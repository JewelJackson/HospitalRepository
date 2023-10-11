package com.hospital.hospitalmanagementsystem.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
public class Receptionist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receptionistId;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    @NotBlank(message = "Email required")
    @NotNull(message = "Email required")
    private String email;
    @NotBlank(message = "Password required")
    @NotNull(message = "Password required")
    private String password;

    public Receptionist(int i) {
    }
}
