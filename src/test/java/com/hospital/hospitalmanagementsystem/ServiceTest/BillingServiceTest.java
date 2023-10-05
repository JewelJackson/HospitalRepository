package com.hospital.hospitalmanagementsystem.ServiceTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hospital.hospitalmanagementsystem.Entity.*;
import com.hospital.hospitalmanagementsystem.Handler.NotValidException;
import com.hospital.hospitalmanagementsystem.Handler.PrescriptionNotFound;
import com.hospital.hospitalmanagementsystem.Repository.*;
import com.hospital.hospitalmanagementsystem.Request.BillingRequest;
import com.hospital.hospitalmanagementsystem.Service.BillingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BillingServiceTest {

    @InjectMocks
    private BillingService billingService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private ReceptionistRepository receptionistRepository;

    @Mock
    private BillingRepository billingRepository;

    private Doctor doctor;
    private Patient patient;
    private Receptionist receptionist;
    private Prescription prescription;

    private BillingRequest billingRequest;

    @BeforeEach
    public void setUp() {
        doctor = new Doctor();
        doctor.setDoctorStatus("Present");

        patient = new Patient();
        receptionist = new Receptionist();

        prescription = new Prescription();
        prescription.setPrescriptionId(1);
        prescription.setMedicine(Collections.emptyList());
        prescription.setPrescriptionDate(java.sql.Date.valueOf("2023-10-04"));

    }

    @Test
    void PrescriptionIdIsInvalid() {
        BillingRequest billingRequest = new BillingRequest();
        billingRequest.setPrescriptionId(1);
        billingRequest.setDoctorId(2);
        billingRequest.setPatientId(3);
        billingRequest.setConsultationFee(100.0);
        billingRequest.setTestingFee(200.0);
        billingRequest.setPaymentStatus("PAID");

        when(prescriptionRepository.findByPrescriptionId(1)).thenReturn(Optional.empty());

        assertThrows(PrescriptionNotFound.class, () -> {
            billingService.payment(billingRequest);
        });
    }

    @Test
    void CalculateMedicineFeeSuccess() {
        List<Medicine> medicines = new ArrayList<>();
        medicines.add(new Medicine(1, "Medicine 1", 50.0));
        medicines.add(new Medicine(2, "Medicine 2", 70.0));

        double medicineFee = billingService.calculateMedicineFee(medicines);

        assertEquals(120.0, medicineFee);
    }
}

