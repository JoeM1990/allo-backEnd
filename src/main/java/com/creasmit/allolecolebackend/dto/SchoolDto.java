package com.creasmit.allolecolebackend.dto;

import com.creasmit.allolecolebackend.model.School;
import com.creasmit.allolecolebackend.model.UserProfil;
import lombok.Data;

@Data
public class SchoolDto {
    private School school;
    private UserProfil userProfil;
}
