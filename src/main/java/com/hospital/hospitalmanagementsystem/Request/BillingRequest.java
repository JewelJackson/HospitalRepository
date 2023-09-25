package com.hospital.hospitalmanagementsystem.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillingRequest {
    @NotBlank
    @NotNull(message = "Enter doctor Id")
    private int doctorId;
    @NotBlank
    @NotNull(message = "Enter patient Id")
    private int patientId;
    @NotBlank
    @NotNull(message = "Enter appointment Id")
    private int appointmentId;
    @NotBlank
    @NotNull(message = "Enter prescription Id")
    private int prescriptionId;
    @NotBlank
    @NotNull(message = "Enter receptionist Id")
    private int receptionistId;
    private String billingDate;

    @NotBlank
    @NotNull(message = "Consultation Fee is necessary")
    private double consultationFee;
    private double testingFee;
    private double medicineFee;
    @NotBlank
    @NotNull(message = "Calculate the total amount")
    private double totalAmount;
}
