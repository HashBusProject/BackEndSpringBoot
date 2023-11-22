package com.hashbus.back.model;

import lombok.Data;

import java.sql.Time;

@Data
public class Schedule {
    private Journey journey; // 1 - 9
    private Bus bus;   // 1  2  3  4  5
    private Time time; // 10 11 12 13 18
}
