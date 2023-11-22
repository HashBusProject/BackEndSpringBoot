package com.hashbus.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Journey {
    private long id;
    private String name;
    private Point sourcePoint;
    private Point destinationPoint;
    private HashSet<Point> stopPoints;
    private HashSet<Schedule> journeys;
}
