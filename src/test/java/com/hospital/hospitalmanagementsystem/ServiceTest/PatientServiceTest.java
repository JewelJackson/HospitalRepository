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
        patient = new Patient();
        patient.setEmail("patient@example.com");
        patient.setFirstName("Gokul");
        patient.setLastName("Nair");
        patient.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
    }

    @Test
    public void testPatientLoginSuccess() {
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setPhone("8975369433");
        patientRequest.setPassword("password");

        when(patientRepository.findByPhone("8975369433")).thenReturn(patient);

        PatientResponse patientResponse = assertDoesNotThrow(() -> patientService.patientLogin(patientRequest));

        assertEquals("Gokul Nair", patientResponse.getName());
        assertEquals("patient@example.com", patientResponse.getEmail());
    }

    @Test
    public void testPatientLoginInvalid() {

        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setPhone("8975369433");
        patientRequest.setPassword("Wrong Password"); // Wrong password

        when(patientRepository.findByPhone("8975369433")).thenReturn(patient);

        assertThrows(InvalidException.class, () -> patientService.patientLogin(patientRequest));
    }

    @Test
    public void testPatientNotRegistered() {
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setPhone("1234567890");
        patientRequest.setPassword("password");

        when(patientRepository.findByPhone("1234567890")).thenReturn(null);

        assertThrows(InvalidException.class, () -> patientService.patientLogin(patientRequest));
    }

    @Test
    public void testGetAllPatients() {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);

        when(patientRepository.findAll()).thenReturn(patientList);

        List<Patient> patients = patientService.getAllPatients();

        assertEquals(1, patients.size());
        assertEquals("patient@example.com", patients.get(0).getEmail());
    }
}