package com.creasmit.allolecolebackend.controller;

import com.creasmit.allolecolebackend.dto.OptionClassDto;
import com.creasmit.allolecolebackend.model.OptionClass;
import com.creasmit.allolecolebackend.service.OptionService;
import com.creasmit.allolecolebackend.utils.StatusResponse;
import com.creasmit.allolecolebackend.utils.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class OptionController {

    @Autowired
    private OptionService optionService;


    @GetMapping("/classRooms/{classRoomId}/options")
    public ResponseEntity getOptionsByClass(@PathVariable("classRoomId") long classRoomId) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            List<OptionClass> classRooms = this.optionService.getAllByClassRoom(classRoomId);
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

    @PostMapping("/options")
    public ResponseEntity create(@RequestBody OptionClassDto optionClassDto) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            OptionClass optionClass = new OptionClass();
            optionClass.setName(optionClassDto.getName());
            optionClass.setClassRoom(optionClassDto.getClassRoom());

            OptionClass optionClassCreated = this.optionService.create(optionClass);
            if (optionClassCreated != null) {
                return ResponseEntity.status(HttpStatus.OK).body(optionClassCreated);
            } else
                statusResponse.setMessage("Echec d'enregistrement");

        } catch (ValueException e) {
            statusResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);
    }

    @PutMapping("/options")
    public ResponseEntity update(@RequestBody OptionClass optionClass) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            OptionClass optionClassUpdated = this.optionService.update(optionClass);
            if (optionClassUpdated != null) {
                return ResponseEntity.status(HttpStatus.OK).body(optionClassUpdated);
            } else
                statusResponse.setMessage("Echec de modification");

        } catch (ValueException e) {
            statusResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);
    }

    @DeleteMapping("/options/{optionId}")
    public ResponseEntity delete(@PathVariable("optionId") long optionId) {

        StatusResponse statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            boolean isDeleted = this.optionService.delete(optionId);
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
