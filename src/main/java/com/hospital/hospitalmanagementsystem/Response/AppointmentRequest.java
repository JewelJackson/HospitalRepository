package com.hospital.hospitalmanagementsystem.Response;

import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Entity.Patient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class AppointmentRequest {

    private Patient patient;
    private Doctor doctor;
    private String appointmentDate;
    private boolean doctorAvailabilityStatus;
    private LocalTime appointmentTime;
}
