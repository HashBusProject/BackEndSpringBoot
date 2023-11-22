package com.hashbus.back.model;

import lombok.Data;

@Data
public class Ticket {
    private long id;
    private double price;
    private Journey journey;
}
