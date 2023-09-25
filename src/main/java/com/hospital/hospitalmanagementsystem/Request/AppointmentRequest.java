package com.hospital.hospitalmanagementsystem.Request;

import com.hospital.hospitalmanagementsystem.Entity.Receptionist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

    @NotBlank(message = "Email required")
    @Email(message = "Invalid email address")
    private String patientEmail;

    @NotBlank(message = "Email required")
    @Email(message = "Invalid email address")
    private String doctorEmail;
    private Receptionist receptionist;
    private String appointmentDate;
    private boolean doctorAvailabilityStatus;
}
