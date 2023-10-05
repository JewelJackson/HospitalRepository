package com.hospital.hospitalmanagementsystem.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hospital.hospitalmanagementsystem.Entity.Admin;
import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Entity.Patient;
import com.hospital.hospitalmanagementsystem.Entity.Receptionist;
import com.hospital.hospitalmanagementsystem.Handler.DoctorNotFoundException;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Repository.AdminRepository;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospital.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospital.hospitalmanagementsystem.Repository.ReceptionistRepository;
import com.hospital.hospitalmanagementsystem.Request.AdminRequest;
import com.hospital.hospitalmanagementsystem.Request.DoctorRemoveRequest;
import com.hospital.hospitalmanagementsystem.Response.AdminResponse;
import com.hospital.hospitalmanagementsystem.Service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ReceptionistRepository receptionistRepository;

    private Admin admin;
    private Doctor doctor;
    private Patient patient;
    private Receptionist receptionist;

    @BeforeEach
    public void setUp() {
        admin = new Admin();
        admin.setEmail("admin@example.com");
        admin.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));

        doctor = new Doctor();
        doctor.setEmail("doctor@example.com");

        patient = new Patient();
        patient.setEmail("patient@example.com");

        receptionist = new Receptionist();
        receptionist.setEmail("receptionist@example.com");

    }

    @Test
    public void testAdminLoginSuccess() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setEmail("admin@example.com");
        adminRequest.setPassword("password");

        when(adminRepository.findByEmail(any())).thenReturn(admin);

        AdminResponse adminResponse = adminService.adminLogin(adminRequest);

        assertNotNull(adminResponse);
        assertEquals("admin@example.com", adminResponse.getEmail());
    }

    @Test
    public void testAdminLoginInvalid() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setEmail("admin@example.com");
        adminRequest.setPassword("invalidPassword");

        when(adminRepository.findByEmail(any())).thenReturn(admin);

        assertThrows(InvalidException.class, () -> adminService.adminLogin(adminRequest));
    }

    @Test
    public void testAdminNotRegistered() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setEmail("nonexistent@example.com");
        adminRequest.setPassword("password");

        when(adminRepository.findByEmail(any())).thenReturn(null);

        assertThrows(InvalidException.class, () -> adminService.adminLogin(adminRequest));
    }

    @Test
    public void testRemoveDoctorByAdminSuccess() {
        // Create a DoctorRemoveRequest with valid data
        DoctorRemoveRequest doctorRemoveRequest = new DoctorRemoveRequest();
        doctorRemoveRequest.setAdEmail("admin@example.com");
        doctorRemoveRequest.setDoctorID(1); // Replace with a valid doctor ID

        // Mock the behavior of adminRepository.findByEmail
        when(adminRepository.findByEmail("admin@example.com")).thenReturn(admin);

        // Mock the behavior of doctorRepository.findByDoctorId
        when(doctorRepository.findByDoctorId(1)).thenReturn(Optional.of((doctor)));

        // Call the removeDoctorByAdmin method
        assertDoesNotThrow(() -> adminService.removeDoctorByAdmin(doctorRemoveRequest));

        // Verify that the doctor's status was updated
        assertEquals("Not present", doctor.getDoctorStatus());
        // Verify that the doctorRepository.save method was called once
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    public void testRemoveDoctorByAdmin_DoctorNotFound() {
        // Create a DoctorRemoveRequest with a doctor ID that doesn't exist
        DoctorRemoveRequest doctorRemoveRequest = new DoctorRemoveRequest();
        doctorRemoveRequest.setAdEmail("admin@example.com");
        doctorRemoveRequest.setDoctorID(1); // Replace with a doctor ID that doesn't exist

        // Mock the behavior of adminRepository.findByEmail
        //Admin admin = new Admin();
        when(adminRepository.findByEmail("admin@example.com")).thenReturn(admin);

        // Mock the behavior of doctorRepository.findByDoctorId to return an empty Optional
        when(doctorRepository.findByDoctorId(1)).thenReturn(Optional.empty());

        // Call the removeDoctorByAdmin method and expect a DoctorNotFoundException
        assertThrows(DoctorNotFoundException.class, () -> adminService.removeDoctorByAdmin(doctorRemoveRequest));

        // Verify that the doctorRepository.save method was not called
        //verify(doctorRepository, never()).save(any(Doctor.class));
    }

}
