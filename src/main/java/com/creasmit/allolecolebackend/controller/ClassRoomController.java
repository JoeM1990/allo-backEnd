package com.creasmit.allolecolebackend.controller;

import com.creasmit.allolecolebackend.model.ClassRoom;
import com.creasmit.allolecolebackend.service.ClassRoomService;
import com.creasmit.allolecolebackend.utils.StatusResponse;
import com.creasmit.allolecolebackend.utils.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ClassRoomController {

    @Autowired
    private ClassRoomService classRoomService;

    @GetMapping("/schools/{schoolId}/classRooms")
    public ResponseEntity getClassesBySchool(@PathVariable("schoolId") long schoolId) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            List<ClassRoom> classRooms = this.classRoomService.getAllBySchool(schoolId);
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

    @PostMapping("/classRooms")
    public ResponseEntity create(@RequestBody ClassRoom classRoom) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            ClassRoom classRoomCreated = this.classRoomService.create(classRoom);
            if (classRoomCreated != null) {
                return ResponseEntity.status(HttpStatus.OK).body(classRoomCreated);
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

    @PutMapping("/classRooms")
    public ResponseEntity update(@RequestBody ClassRoom classRoom) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            ClassRoom classRoomUpdated = this.classRoomService.update(classRoom);
            if (classRoomUpdated != null) {
                return ResponseEntity.status(HttpStatus.OK).body(classRoomUpdated);
            } else
                statusResponse.setMessage("Echec de modification");

        } catch (ValueException e) {
            statusResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);
    }

    @DeleteMapping("/classRooms/{classRoomId}")
    public ResponseEntity delete(@PathVariable("classRoomId") long classRoomId) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            boolean isDeleted = this.classRoomService.delete(classRoomId);
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
