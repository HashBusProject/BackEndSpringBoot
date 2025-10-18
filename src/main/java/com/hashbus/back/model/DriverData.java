package com.hashbus.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverData {
    private User driver;
    private Bus bus;
}
