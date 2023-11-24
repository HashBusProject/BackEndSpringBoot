package com.hashbus.back.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    private Integer id;
    private String pointName;
    private Double x;
    private Double y;
    private HashSet<Journey> journeys;
}