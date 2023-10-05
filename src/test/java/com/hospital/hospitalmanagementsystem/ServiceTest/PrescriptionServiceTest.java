package com.hospital.hospitalmanagementsystem.ServiceTest;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hospital.hospitalmanagementsystem.Entity.*;
import com.hospital.hospitalmanagementsystem.Handler.*;
import com.hospital.hospitalmanagementsystem.Repository.*;
import com.hospital.hospitalmanagementsystem.Request.AppointmentRequest;
import com.hospital.hospitalmanagementsystem.Request.PrescriptionRequest;
import com.hospital.hospitalmanagementsystem.Service.PrescriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceTest {

    @InjectMocks
    private PrescriptionService prescriptionService;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    private Doctor doctor;
    private Patient patient;
    private Appointment appointment;
    private List<Medicine> medicineList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        doctor = new Doctor();
        doctor.setDoctorStatus("Present");
        doctor.setDoctorId(1);

        patient = new Patient();
        patient.setPatientId(1);

        java.util.Date utilDate = new java.util.Date(); // Create a java.util.Date
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Convert to java.sql.Date

        appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(sqlDate);

        // Use non-null medicine names
        medicineList = Arrays.asList(
                new Medicine("Medicine1", 10.0),
                new Medicine("Medicine2", 20.0)
        );
    }

    @Test
    public void testAddPrescriptionInvalidAppointment() {
        PrescriptionRequest prescriptionRequest = new PrescriptionRequest();
        prescriptionRequest.setAppointmentId(1);
        prescriptionRequest.setPatientId(5);
        prescriptionRequest.setDoctorId(1);
        prescriptionRequest.setMedicine(Arrays.asList("Medicine 1"));

        when(appointmentRepository.findByAppointmentId(1)).thenReturn(Optional.empty());

       assertThrows(AppointmentNotFound.class, () -> {
            prescriptionService.addPrescription(prescriptionRequest);
        });
    }

    @Test
    public void testAddPrescription_DoctorIdOrPatientIdNotMatch() {
        PrescriptionRequest prescriptionRequest = new PrescriptionRequest();
        prescriptionRequest.setAppointmentId(1);
        prescriptionRequest.setPatientId(5);
        prescriptionRequest.setDoctorId(1);
        prescriptionRequest.setMedicine(Arrays.asList("Medicine 1"));

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);
        appointment.setDoctor(new Doctor(1));
        appointment.setPatient(new Patient(5));

        when(appointmentRepository.findByAppointmentId(1)).thenReturn(Optional.of(appointment));

        assertThrows(NotValidException.class, () -> {
            prescriptionService.addPrescription(prescriptionRequest);
        });
    }

    @Test
    public void testAddPrescriptionSuccess() {
        PrescriptionRequest prescriptionRequest = new PrescriptionRequest();
        prescriptionRequest.setAppointmentId(1);
        prescriptionRequest.setPatientId(5);
        prescriptionRequest.setDoctorId(1);
        prescriptionRequest.setMedicine(List.of("Medicine 1"));

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1);
        Doctor doctor1 = new Doctor();
        doctor1.setDoctorId(1);
        appointment.setDoctor(doctor1);
        Patient patient1 = new Patient();
        patient1.setPatientId(5);
        appointment.setPatient(patient1);

        when(appointmentRepository.findByAppointmentId(1)).thenReturn(Optional.of(appointment));

        List<Medicine> medicineList = new ArrayList<>();
        Medicine medicine = new Medicine();
        medicine.setMedicineName("Medicine 1");
        medicineList.add(medicine);

        when(medicineRepository.findAll()).thenReturn(medicineList);

        prescriptionService.addPrescription(prescriptionRequest);

        // Verify that the prescription is saved to the database
        verify(prescriptionRepository).save(any(Prescription.class));
    }

}












