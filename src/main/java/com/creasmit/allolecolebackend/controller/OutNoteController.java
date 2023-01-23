package com.creasmit.allolecolebackend.controller;

import com.creasmit.allolecolebackend.dto.MessagePayload;
import com.creasmit.allolecolebackend.model.*;
import com.creasmit.allolecolebackend.service.*;
import com.creasmit.allolecolebackend.utils.StatusResponse;
import com.creasmit.allolecolebackend.utils.ValueException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class OutNoteController {

    @Autowired
    private OutNoteService outNoteService;

    @Autowired
    private PupilService pupilService;

    @Autowired
    private SmsServiceImpl smsService;

    @GetMapping("/schools/{schoolId}/notes")
    public ResponseEntity getAllBySchool(@PathVariable("schoolId") long schoolId) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            List<OutNote> classRooms = this.outNoteService.getAllBySchool(schoolId);
            return ResponseEntity.status(HttpStatus.OK).body(classRooms);
        } catch (ValueException e) {
            statusResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);
    }

    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity delete(@PathVariable("noteId") long noteId) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            boolean isDeleted = this.outNoteService.delete(noteId);
            if (isDeleted) {
                statusResponse.setStatus(HttpStatus.OK.name());
                return ResponseEntity.status(HttpStatus.OK).body(statusResponse);
            } else
                statusResponse.setMessage("Echec de suppression");

        } catch (ValueException e) {
            statusResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);
    }

    @PostMapping("/messages")
    public ResponseEntity send(@RequestBody MessagePayload messagePayload) {
        StatusResponse statusResponse = new StatusResponse();
        try {
            this.prepareNote(messagePayload);
            statusResponse.setStatus(HttpStatus.OK.name());
            return ResponseEntity.status(HttpStatus.OK).body(statusResponse);
        } catch (Exception e) {
            e.printStackTrace();
            statusResponse.setMessage("Erreur interne");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statusResponse);
    }

    private List<Pupil> pupils = new ArrayList<>();

    private void prepareNote(MessagePayload messagePayload) throws Exception {
        System.out.println(new ObjectMapper().writeValueAsString(messagePayload));
        switch (messagePayload.getType()) {
            case "toSchool":
            case "toPupils": {
                if (messagePayload.getType().equals("toSchool"))
                    pupils = this.pupilService.getAllBySchool(messagePayload.getSchool().getId());
                else if (messagePayload.getType().equals("toPupils"))
                    pupils = messagePayload.getPupils();
                this.sendNote(pupils, messagePayload);
                break;
            }
            case "toClassRoom":
                for (ClassRoom classRoom : messagePayload.getClassRooms()) {
                    pupils = this.pupilService.getAllByClassRoom(classRoom.getId());
                    this.sendNote(pupils, messagePayload);
                }
                break;
            case "toOption":
                for (OptionClass optionClass : messagePayload.getOptionClasses()) {
                    pupils = this.pupilService.getAllByOption(optionClass.getId());
                    this.sendNote(pupils, messagePayload);
                }
                break;
        }
    }

    public void sendNote(List<Pupil> pupils, MessagePayload messagePayload) {
        new Thread(() -> {
            try {
                for (Pupil pupil : pupils) {
                    smsService.sendSms(pupil.getPhoneNumber(), messagePayload.getContent() + " " + messagePayload.getSchool().getName().toUpperCase());
                }
                OutNote outNote = new OutNote();
                outNote.setNoteContent(messagePayload.getContent());
                outNote.setCount(pupils.size());
                outNote.setSchool(messagePayload.getSchool());
                outNoteService.create(outNote);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
