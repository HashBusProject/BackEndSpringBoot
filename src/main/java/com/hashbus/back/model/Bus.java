package com.hashbus.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {
    private User driver;
    private Integer id;
    private Integer cap;
    private HashSet<Schedule> schedules;
//    private int
    private boolean isWorking;
}
