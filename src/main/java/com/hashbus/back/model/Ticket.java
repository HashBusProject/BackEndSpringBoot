package com.hashbus.back.model;

import jnr.ffi.annotations.In;
import lombok.Data;

@Data
public class Ticket {
    private Integer id;
    private Journey journey;
    private User user;
}
