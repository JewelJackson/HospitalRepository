package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.*;
import com.hospital.hospitalmanagementsystem.Handler.AppointmentNotFound;
import com.hospital.hospitalmanagementsystem.Handler.NotValidException;
import com.hospital.hospitalmanagementsystem.Handler.PrescriptionNotFound;
import com.hospital.hospitalmanagementsystem.Repository.AppointmentRepository;
import com.hospital.hospitalmanagementsystem.Repository.BillingRepository;
import com.hospital.hospitalmanagementsystem.Repository.PrescriptionRepository;
import com.hospital.hospitalmanagementsystem.Repository.ReceptionistRepository;
import com.hospital.hospitalmanagementsystem.Request.BillingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public void payment(BillingRequest billingRequest) {

        Optional<Appointment> appointment = appointmentRepository.findByAppointmentId(billingRequest.getAppointmentId());
        if(!appointment.isPresent()){
            throw new AppointmentNotFound("Appointment not found for billing.");
        }
        Appointment app = appointment.get();

        if (!(app.getDoctor().getDoctorId() == (billingRequest.getDoctorId())) && !(app.getPatient().getPatientId() == (billingRequest.getPatientId()))) {
            throw new NotValidException("Doctor ID or Patient ID does not match the appointment for billing");
        }

        Prescription prescription = prescriptionRepository.findByPrescriptionId(billingRequest.getPrescriptionId());
        if (prescription == null) {
            throw new PrescriptionNotFound("Prescription not found for billing.");
        }

        Billing billing = new Billing();
        billing.setReceptionist(receptionistRepository.findByReceptionistId(billingRequest.getReceptionistId()));
        billing.setDoctor(app.getDoctor());
        billing.setPatient(app.getPatient());
        billing.setBillingDate(app.getAppointmentDate());
        billing.setConsultationFee(billingRequest.getConsultationFee());
        billing.setTestingFee(billingRequest.getTestingFee());

        double medicineFee = calculateMedicineFee(prescription.getMedicine());
        billing.setMedicineFee(medicineFee);

        billing.setTotalAmount(billingRequest.getConsultationFee() +
                billingRequest.getTestingFee() +
                medicineFee);

        billingRepository.save(billing);
    }

    private double calculateMedicineFee(List<Medicine> medicines) {
        double medicineFee = 0.0;
        for (Medicine medicine : medicines) {
            medicineFee += medicine.getPrice();
        }
        return medicineFee;
    }

}





 /*   List<Medicine> medicines = prescriptionRepository.findByMedicineName(billingRequest.getMedicineFee());
    double calculateMedicineFee() {
        double medicineFee = 0.0;
        for (Medicine medicine : medicines) {
            medicineFee += medicine.getPrice();
        }
    }*/