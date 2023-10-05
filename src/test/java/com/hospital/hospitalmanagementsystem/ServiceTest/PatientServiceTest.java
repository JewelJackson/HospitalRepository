package com.hospital.hospitalmanagementsystem.ServiceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hospital.hospitalmanagementsystem.Entity.Patient;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospital.hospitalmanagementsystem.Request.PatientRequest;
import com.hospital.hospitalmanagementsystem.Response.PatientResponse;
import com.hospital.hospitalmanagementsystem.Service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    Patient patient = null;

    @BeforeEach
    public void setUp() {
        // You can initialize any common setup here if needed.
        patient = new Patient();
        patient.setEmail("patient@example.com");
        patient.setFirstName("Gokul");
        patient.setLastName("Nair");
        patient.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
    }

    @Test
    public void testPatientLoginSuccess() {
        // Create a PatientRequest
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setPhone("8975369433");
        patientRequest.setPassword("password"); // Password should be hashed, but for testing, we use plain text here

        // Create a mocked Patient object
      /*  Patient patient = new Patient();
        patient.setEmail("patient@example.com");
        patient.setFirstName("Gokul");
        patient.setLastName("Nair");
        patient.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));*/// Hashed password

        // Mock the behavior of the patientRepository.findByEmail method
        when(patientRepository.findByPhone("8975369433")).thenReturn(patient);

        // Call the service method
        PatientResponse patientResponse = assertDoesNotThrow(() -> patientService.patientLogin(patientRequest));

        // Verify that the response contains the correct patient information
        assertEquals("Gokul Nair", patientResponse.getName());
        assertEquals("patient@example.com", patientResponse.getEmail());
    }

    @Test
    public void testPatientLoginInvalid() {
        // Create a PatientRequest
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setPhone("8975369433");
        patientRequest.setPassword("Wrong Password"); // Wrong password

        // Create a mocked Patient object with the correct email but a different password
        Patient patient = new Patient();
        patient.setEmail("patient@example.com");
        patient.setPassword(BCrypt.hashpw("Password Correct", BCrypt.gensalt())); // Hashed password

        // Mock the behavior of the patientRepository.findByEmail method
        when(patientRepository.findByPhone("8975369433")).thenReturn(patient);

        // Call the service method and expect a ValidationException with "Wrong password"
        assertThrows(InvalidException.class, () -> patientService.patientLogin(patientRequest));
    }

    @Test
    public void testPatientNotRegistered() {
        // Create a PatientRequest with an email for a non-existent patient
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setPhone("8975369433");
        patientRequest.setPassword("password");

        // Mock the behavior of the patientRepository.findByEmail method to return null
        when(patientRepository.findByPhone("8975369433")).thenReturn(null);

        // Call the service method and expect a ValidationException with "Patient not registered"
        assertThrows(InvalidException.class, () -> patientService.patientLogin(patientRequest));
    }

    @Test
    public void testGetAllPatients() {
        Patient patient = new Patient();
        patient.setEmail("patient@example.com");
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);

        when(patientRepository.findAll()).thenReturn(patientList);

        List<Patient> patients = patientService.getAllPatients();

        assertEquals(1, patients.size());
        assertEquals("patient@example.com", patients.get(0).getEmail());
    }
}