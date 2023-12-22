package com.hashbus.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataSchedule {
    private Journey journey;
    private Schedule schedule;
}
