package com.hashbus.back.model;

import jnr.ffi.annotations.In;
import lombok.Data;

import java.sql.Time;

@Data
public class Schedule {
    private Integer journey; // 1 - 9
    private Integer bus;   // 1  2  3  4  5
    private Time time; // 10 11 12 13 18
    private Integer passengersNumber ;
}