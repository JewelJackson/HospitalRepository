package com.hospital.hospitalmanagementsystem.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthFinderResponse {
    private int age;
    private String sex;
    private String pregnant;
    private String sexuallyActive;
    private String tobaccoUse;
}
