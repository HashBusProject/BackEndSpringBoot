package com.hashbus.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchDataSchedule {
    private Journey journey;
    private Schedule schedule;
    private Bus bus;
}
