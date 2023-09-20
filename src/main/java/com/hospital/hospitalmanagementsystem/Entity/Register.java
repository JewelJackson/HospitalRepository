package com.hospital.hospitalmanagementsystem.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class Register {

    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(message = "Invalid email address")
    private String email;
    /*@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\\\d)(?=.*[@#$%^&+=!]).+$",
            message = "Password must contain at least one uppercase letter, one digit, and one special character")*/
    @Size(min = 8, message = "Password must have at least 8 characters.")
    @NotBlank(message = "Password is required")
    private String password;
    private String department;
    private String doctorStatus;
    private String gender;
    @Size(min = 10, max = 10, message = "Phone number must contain exactly 10 digits")
    private String phone;
    private int age;
    private String address;
    @NotBlank(message = "Role cannot be empty or null")
    private String role;



}
