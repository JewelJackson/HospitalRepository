package com.hospital.hospitalmanagementsystem.ServiceTest;

import com.hospital.hospitalmanagementsystem.Entity.Admin;
import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Entity.Patient;
import com.hospital.hospitalmanagementsystem.Entity.Receptionist;
import com.hospital.hospitalmanagementsystem.Repository.AdminRepository;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospital.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospital.hospitalmanagementsystem.Repository.ReceptionistRepository;
import com.hospital.hospitalmanagementsystem.Request.RegisterRequest;
import com.hospital.hospitalmanagementsystem.Service.RegisterService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.*;

public class RegisterServiceTest {

    @InjectMocks
    private RegisterService registerService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ReceptionistRepository receptionistRepository;

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterAdmin() {
        // Create a RegisterRequest object for an admin
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setRole("admin");

        when(adminRepository.save(any(Admin.class))).thenReturn(new Admin()); // Return a new Admin object

        registerService.register(registerRequest);

        // Verify that adminRepository.save() was called with the expected Admin object
        verify(adminRepository).save(any(Admin.class));

        // Verify that mailSender.send() was called with the expected SimpleMailMessage
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testRegisterDoctor() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setRole("doctor");
        when(doctorRepository.save(any(Doctor.class))).thenReturn(new Doctor());
        registerService.register(registerRequest);
        verify(doctorRepository).save(any(Doctor.class));
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testRegisterPatient() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setRole("patient");
        when(patientRepository.save(any(Patient.class))).thenReturn(new Patient());
        registerService.register(registerRequest);
        verify(patientRepository).save(any(Patient.class));
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testRegisterReceptionist() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setRole("receptionist");
        when(receptionistRepository.save(any(Receptionist.class))).thenReturn(new Receptionist());
        registerService.register(registerRequest);
        verify(receptionistRepository).save(any(Receptionist.class));
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testExtractFromCSV() throws IOException {
        // Mock the MultipartFile
        byte[] csvBytes = "firstName,lastName,age,gender,phone,email,address\nSachin,Tendulkar,45,Male,1234567890,sachin@example.com,HB123".getBytes();
        MockMultipartFile mockFile = new MockMultipartFile("file", "patients.csv", "text/csv", csvBytes);

        // Mock the InputStream
        try (InputStream inputStream = mockFile.getInputStream();
             InputStreamReader reader = new InputStreamReader(inputStream);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            // Mock the CSVRecord
            List<CSVRecord> csvRecords = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                csvRecords.add(record);
            }

            when(patientRepository.save(any(Patient.class))).thenReturn(new Patient());

            registerService.extractFromCSV(mockFile);

            verify(patientRepository, times(csvRecords.size())).save(any(Patient.class));
        }
    }



}