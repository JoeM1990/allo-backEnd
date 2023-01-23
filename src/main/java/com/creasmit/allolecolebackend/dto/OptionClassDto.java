package com.creasmit.allolecolebackend.dto;

import com.creasmit.allolecolebackend.model.ClassRoom;
import lombok.Data;

@Data
public class OptionClassDto {
    private long id;
    private String name;
    private ClassRoom classRoom;
}
