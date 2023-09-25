package com.hospital.hospitalmanagementsystem.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRequest {
    @NotBlank(message = "Medicine name is a required field")
    @NotNull(message = "Give a medicine name")
    private String medicineName;
    @NotBlank(message = "Price is required field.")
    @NotNull(message = "Enter a price.")
    private double price;
}
