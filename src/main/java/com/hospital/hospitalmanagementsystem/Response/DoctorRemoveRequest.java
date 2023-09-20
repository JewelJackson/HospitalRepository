package com.hospital.hospitalmanagementsystem.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRemoveRequest {
    private String adEmail;
    private int doctorID;
}
