package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.*;
import com.hospital.hospitalmanagementsystem.Repository.AdminRepository;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospital.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospital.hospitalmanagementsystem.Repository.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ReceptionistRepository receptionistRepository;

    /*@Autowired
    private Admin admin;
    @Autowired
    private Doctor doctor;
    @Autowired
    private Patient patient;
    @Autowired
    private Receptionist receptionist;
*/

    /**
     * To register details
     * @param registerRequest
     */
    public void save(Register registerRequest) {
        Admin admin = new Admin();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Receptionist receptionist = new Receptionist();

        String role = registerRequest.getRole();
        String firstName = registerRequest.getFirstName();
        String lastName = registerRequest.getLastName();
        String email = registerRequest.getEmail();
        String phone = registerRequest.getPhone();
        String password = registerRequest.getPassword();


        /**
         * Check if role is empty or null
         */
        if (role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Role cannot be empty or null");
        }

        /**
         * Check if first name and last name are empty or null
         */
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("First name and last name are required fields.");
        }

        /**
         * Check if the phone number is null or  contains exactly 10 digits
         */
        if (phone == null || phone.length()!=10) {
            throw new IllegalArgumentException("Phone number must contain 10 digits.");
        }

        /**
         * Check if email is null or contains the "@" symbol
         */
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }

        /**
         * Check password conditions
         * Must have 8 characters
         * must contain at least one uppercase letter, one digit, and one special character
         */
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must have at least 8 characters.");
        }

        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }

            if (hasUppercase && hasDigit && hasSpecialChar) {
                break;
            }
        }
        if (!hasUppercase || !hasDigit || !hasSpecialChar) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one digit, and one special character.");
        }

        /**
         * Check if role is Admin, then set the field
         */
        if (role.equals("Admin")) {
            admin.setFirstName(registerRequest.getFirstName());
            admin.setLastName(registerRequest.getLastName());
            admin.setPhone(registerRequest.getPhone());
            admin.setEmail(registerRequest.getEmail());
            admin.setPassword(registerRequest.getPassword());
            adminRepository.save(admin);
        }

        else if (role.equals("Doctor")) {
            doctor.setFirstName(registerRequest.getFirstName());
            doctor.setLastName(registerRequest.getLastName());
            doctor.setPhone(registerRequest.getPhone());
            doctor.setEmail(registerRequest.getEmail());
            doctor.setGender(registerRequest.getGender());
            doctor.setDepartment(registerRequest.getDepartment());
            doctorRepository.save(doctor);
        }

        else if (role.equals("Patient")) {
            patient.setFirstName(registerRequest.getFirstName());
            patient.setLastName(registerRequest.getLastName());
            patient.setGender(registerRequest.getGender());
            patient.setPhone(registerRequest.getPhone());
            patient.setEmail(registerRequest.getEmail());
            patient.setDateOfBirth(registerRequest.getDateOfBirth());
            patient.setAddress(registerRequest.getAddress());
            patientRepository.save(patient);
        }

        else if (role.equals("Receptionist")){
            receptionist.setFirstName(registerRequest.getFirstName());
            receptionist.setLastName(registerRequest.getLastName());
            receptionist.setGender(registerRequest.getGender());
            receptionist.setPhone(registerRequest.getPhone());
            receptionist.setEmail(registerRequest.getEmail());
            receptionistRepository.save(receptionist);
        }
    }
}