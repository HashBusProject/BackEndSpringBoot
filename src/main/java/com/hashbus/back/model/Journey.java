package com.hashbus.back.model;

import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Journey {
    private Integer id;
    private String name;
    private Integer sourcePoint;
    private Integer destinationPoint;
    private List<Integer> stopPoints;
    private Double price;
    private List<Schedule> journeys;
}
