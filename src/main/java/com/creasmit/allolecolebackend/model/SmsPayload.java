package com.creasmit.allolecolebackend.model;

import lombok.Data;

@Data
public class SmsPayload {
    private String from;
    private String to;
    private String content;
}
