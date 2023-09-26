package com.hospital.hospitalmanagementsystem.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillingRequest {
    //@NotBlank(message = "Enter doctor Id")
    private int doctorId;
    //@NotBlank(message = "Enter patient Id")
    private int patientId;
    private int appointmentId;
    //@NotBlank(message = "Enter prescription Id")
    private int prescriptionId;
    //@NotBlank(message = "Enter receptionist Id")
    private int receptionistId;
    private String billingDate;

    @NotNull(message = "Consultation Fee is necessary")
    @DecimalMin(value = "0.0", message = "Consultation Fee cannot be negative.")
    private double consultationFee;
    @DecimalMin(value = "0.0", message = "Testing Fee cannot be negative.")
    private double testingFee;
    private double medicineFee;
    @NotBlank(message = "Balance should be mentioned.")
    private String paymentStatus;
    private double totalAmount;
}
