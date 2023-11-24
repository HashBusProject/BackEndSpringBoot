package com.hashbus.back.model;

import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Journey {
    private Integer id;
    private String name;
    private Point sourcePoint;
    private Point destinationPoint;
    private HashSet<Integer> stopPoints;
    private HashSet<Schedule> journeys;
}
