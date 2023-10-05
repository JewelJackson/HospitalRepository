package com.hospital.hospitalmanagementsystem.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;
    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    @NotNull(message = "Phone Number required")
    @Size(min = 10, max = 10, message = "Phone number must contain exactly 10 digits")
    private String phone;
    private String email;
    private String address;
    @NotBlank(message = "Password required")
    private String password;

    public Patient(int i) {
    }
}
