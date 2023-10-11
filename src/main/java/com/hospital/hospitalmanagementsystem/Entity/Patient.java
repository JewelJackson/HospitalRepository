package com.hospital.hospitalmanagementsystem.Entity;
import jakarta.persistence.*;
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
@Table(name = "Patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;
    @NotNull(message = "First Name required")
    private String firstName;
    @NotNull(message = "Last Name required")
    private String lastName;
    private String age;
    private String gender;
    @NotNull(message = "Phone Number required")
    @Column(unique = true)
    @Size(min = 10, max = 10, message = "Phone number must contain exactly 10 digits")
    private String phone;
    private String email;
    private String address;
    @NotBlank(message = "Password required")
    @Column(unique = true)
    private String password;

    public Patient(int i) {
    }
}
