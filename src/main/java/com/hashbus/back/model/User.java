package com.hashbus.back.model;

import lombok.Data;

import java.util.HashSet;

@Data
public class User {
    private long id;
    private String username;
    private String email;
    private String name;
    private String password;
    private int role;
}
