package com.creasmit.allolecolebackend.controller;

import com.creasmit.allolecolebackend.model.Pupil;
import com.creasmit.allolecolebackend.service.PupilService;
import com.creasmit.allolecolebackend.utils.StatusResponse;
import com.creasmit.allolecolebackend.utils.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PupilController {
    @Autowired
    private PupilService pupilService;

    @GetMapping("/schools/{schoolId}/pupils")
    public ResponseEntity getClassesBySchool(@PathVariable("schoolId") long schoolId) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            List<Pupil> classRooms = this.pupilService.getAllBySchool(schoolId);
            return ResponseEntity.status(HttpStatus.OK).body(classRooms);
        } catch (ValueException e) {
            statusResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);

    }

    @GetMapping("/classRooms/{classRoomId}/pupils")
    public ResponseEntity getClassesByClassRoom(@PathVariable("classRoomId") long classRoomId) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            List<Pupil> classRooms = this.pupilService.getAllByClassRoom(classRoomId);
            return ResponseEntity.status(HttpStatus.OK).body(classRooms);
        } catch (ValueException e) {
            statusResponse.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
            e.printStackTrace();
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);

    }

    @PostMapping("/pupils")
    public ResponseEntity create(@RequestBody Pupil pupil) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            Pupil pupilCreated = this.pupilService.create(pupil);
            if (pupilCreated != null) {
                return ResponseEntity.status(HttpStatus.OK).body(pupilCreated);
            } else
                statusResponse.setMessage("Echec d'enregistrement");

        } catch (ValueException e) {
            statusResponse.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
            e.printStackTrace();
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);
    }

    @PutMapping("/pupils")
    public ResponseEntity update(@RequestBody Pupil pupil) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            Pupil pupilCreated = this.pupilService.update(pupil);
            if (pupilCreated != null) {
                return ResponseEntity.status(HttpStatus.OK).body(pupilCreated);
            } else
                statusResponse.setMessage("Echec de modification");

        } catch (ValueException e) {
            statusResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);
    }

    @DeleteMapping("/pupils/{pupilId}")
    public ResponseEntity delete(@PathVariable("pupilId") long pupilId) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            boolean isDeleted = this.pupilService.delete(pupilId);
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
}
