package com.creasmit.allolecolebackend.controller;

import com.creasmit.allolecolebackend.dto.SchoolDetailsDto;
import com.creasmit.allolecolebackend.model.School;
import com.creasmit.allolecolebackend.service.SchoolService;
import com.creasmit.allolecolebackend.service.SmsServiceImpl;
import com.creasmit.allolecolebackend.utils.ResourceName;
import com.creasmit.allolecolebackend.utils.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SmsServiceImpl smsService;

    @GetMapping
    public ResponseEntity getAll() {
        StatusResponse statusResponse = new StatusResponse();
        try {
            List<SchoolDetailsDto> schools = this.schoolService.getAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(schools);
        } catch (Exception e) {
            statusResponse.setStatus("Erreur interne");
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statusResponse);
    }

    @PutMapping("/status/{status}")
    public ResponseEntity updateStatus(@PathVariable("status") int status, @RequestBody School school) {
        StatusResponse statusResponse = new StatusResponse();
        try {
            if (status == 1) {
                School schoolUpdated = this.schoolService.activeSchool(school);
                if (schoolUpdated != null) {
                    this.smsService.sendSms(schoolUpdated.getPhoneNumber(),"Votre compte de l'cole "+schoolUpdated.getName()+" est maintenant operationnel. Plus d'infos contacter "+ ResourceName.supportNumber);
                    return ResponseEntity.status(HttpStatus.OK).body(schoolUpdated);
                }
            } else if (status == 2) {
                School schoolUpdated = this.schoolService.disableSchool(school);
                if (schoolUpdated != null) {
                    this.smsService.sendSms(schoolUpdated.getPhoneNumber(),"Votre compte de l'cole "+schoolUpdated.getName()+" est desactive. Plus d'infos contacter"+ ResourceName.supportNumber);
                    return ResponseEntity.status(HttpStatus.OK).body(schoolUpdated);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statusResponse);
    }
}
