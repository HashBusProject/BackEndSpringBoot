package com.hashbus.back.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.PrimitiveIterator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    private User user ;
    private String newPassword ;
}
