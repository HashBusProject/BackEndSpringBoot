package com.hashbus.back.exceptions;

import com.hashbus.back.service.OrganizerService;

public class OrganizerExeption extends RuntimeException {
    public OrganizerExeption(String s) {
         super(s);
    }

}
