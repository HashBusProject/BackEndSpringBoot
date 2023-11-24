package com.hashbus.back.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    private Long id;
    private String pointName;
    private double x;
    private double y;
    private HashSet<Journey> journeys;
}