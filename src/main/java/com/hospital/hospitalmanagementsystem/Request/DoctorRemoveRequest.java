package com.hospital.hospitalmanagementsystem.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRemoveRequest {
    private String adEmail;
    private int doctorID;
}
