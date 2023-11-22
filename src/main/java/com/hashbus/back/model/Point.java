package com.hashbus.back.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    private long id;
    private String pointName;
    private double x;
    private double y;
    private HashSet<Journey> journeys;
}