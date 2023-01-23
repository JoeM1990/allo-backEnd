package com.creasmit.allolecolebackend.dto;

import com.creasmit.allolecolebackend.model.ClassRoom;
import com.creasmit.allolecolebackend.model.OptionClass;
import com.creasmit.allolecolebackend.model.Pupil;
import com.creasmit.allolecolebackend.model.School;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagePayload {
    private String type;
    private String content;
    private School school;
    private List<Pupil> pupils = new ArrayList<>();
    private List<ClassRoom> classRooms = new ArrayList<>();
    private List<OptionClass> optionClasses = new ArrayList<>();
}
