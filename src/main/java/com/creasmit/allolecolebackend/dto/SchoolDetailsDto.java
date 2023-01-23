package com.creasmit.allolecolebackend.dto;

import com.creasmit.allolecolebackend.model.School;
import lombok.Data;

@Data
public class SchoolDetailsDto {
    private School school;
    private long pupilCount;
    private long sentCountMsg;
}
