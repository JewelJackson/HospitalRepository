package com.hospital.hospitalmanagementsystem.ServiceTest;

import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospital.hospitalmanagementsystem.Request.AvailableDoctorRequest;
import com.hospital.hospitalmanagementsystem.Request.DoctorRequest;
import com.hospital.hospitalmanagementsystem.Response.DoctorResponse;
import com.hospital.hospitalmanagementsystem.Service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        doctor = new Doctor();
        doctor.setEmail("doctor@example.com");
        doctor.setPassword(BCrypt.hashpw("password123", BCrypt.gensalt()));
        doctor.setFirstName("Sam");
        doctor.setLastName("Mathew");
        doctor.setDoctorStatus("Present");
    }

    @Test
    public void testDoctorLoginSuccess() {
        DoctorRequest doctorRequest = new DoctorRequest();
        doctorRequest.setEmail("doctor@example.com");
        doctorRequest.setPassword("password123");

        when(doctorRepository.findByEmail("doctor@example.com")).thenReturn(doctor);

        DoctorResponse doctorResponse = doctorService.doctorLogin(doctorRequest);

        assertEquals("Sam Mathew", doctorResponse.getName());
        assertEquals("doctor@example.com", doctorResponse.getEmail());
    }

    @Test
    public void testLoginDoctorInvalid() {
        DoctorRequest doctorRequest = new DoctorRequest();
        doctorRequest.setEmail("doctor@example.com");
        doctorRequest.setPassword("Wrong Password");

        when(doctorRepository.findByEmail("doctor@example.com")).thenReturn(doctor);

        assertThrows(InvalidException.class, () -> doctorService.doctorLogin(doctorRequest));
    }

    @Test
    public void testDoctorNotRegistered() {
        DoctorRequest doctorRequest = new DoctorRequest();
        doctorRequest.setEmail("nonexistent@example.com");
        doctorRequest.setPassword("password123");

        when(doctorRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        assertThrows(InvalidException.class, () -> doctorService.doctorLogin(doctorRequest));
    }

    @Test
    public void testGetAvailableDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctor);

        when(doctorRepository.findByDoctorStatus("Present")).thenReturn(doctorList);

        List<Doctor> availableDoctors = doctorRepository.findByDoctorStatus("Present");

        assertEquals(1, availableDoctors.size());
        //assertEquals("Sam Mathew", availableDoctors.get(0).getName());
    }

    @Test
    public void testGetAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctor);

        when(doctorRepository.findAll()).thenReturn(doctorList);

        List<Doctor> doctors = doctorService.getAllDoctors();

        assertEquals(1, doctors.size());
        assertEquals("doctor@example.com", doctors.get(0).getEmail());
    }
}
