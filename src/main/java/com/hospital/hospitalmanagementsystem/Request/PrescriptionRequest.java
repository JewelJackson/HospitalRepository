package com.hospital.hospitalmanagementsystem.Request;

import com.hospital.hospitalmanagementsystem.Entity.Medicine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionRequest {
    @NotBlank
    @NotNull(message = "Enter doctor Id")
    private int doctorId;
    @NotBlank
    @NotNull(message = "Enter patient Id")
    private int patientId;
    @NotBlank
    @NotNull(message = "Enter appointment Id")
    private int appointmentId;
    private String checkupNote;
    private List<String> medicine;
    private String prescriptionData;
}
