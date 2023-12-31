package com.hashbus.back.model;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;

@Data
public class User implements Serializable {
    private Integer userID;
    private String username;
    private String email;
    private String name;
    private String password;
    private Integer role;
}
