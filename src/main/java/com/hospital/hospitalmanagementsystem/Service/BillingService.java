package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.*;
import com.hospital.hospitalmanagementsystem.Handler.AppointmentNotFound;
import com.hospital.hospitalmanagementsystem.Handler.BillNotFound;
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
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    /**
     * To generate bill and calculate total bill amount
     * @param billingRequest
     * @return
     */
    public Double payment(BillingRequest billingRequest) {

        Optional<Prescription> prescription = prescriptionRepository.findByPrescriptionId(billingRequest.getPrescriptionId());
        if(!prescription.isPresent()){
            throw new PrescriptionNotFound("Prescription not found for billing.");
        }
        Prescription pres = prescription.get();

        if (!(pres.getDoctor().getDoctorId() == (billingRequest.getDoctorId())) && !(pres.getPatient().getPatientId() == (billingRequest.getPatientId()))) {
            throw new NotValidException("Doctor ID or Patient ID does not match the appointment for billing");
        }

        Billing billing = new Billing();
        billing.setReceptionist(receptionistRepository.findByReceptionistId(billingRequest.getReceptionistId()));
        billing.setDoctor(pres.getDoctor());
        billing.setPatient(pres.getPatient());
        billing.setBillingDate(pres.getPrescriptionDate());
        billing.setConsultationFee(billingRequest.getConsultationFee());
        billing.setTestingFee(billingRequest.getTestingFee());

        double medicineFee = calculateMedicineFee(pres.getMedicine());
        billing.setMedicineFee(medicineFee);

        double amt = (billingRequest.getConsultationFee() +
                billingRequest.getTestingFee() +
                medicineFee);
        billing.setTotalAmount(amt);

        billing.setPaymentStatus(billingRequest.getPaymentStatus());
        billingRepository.save(billing);
        return amt;
    }

    /**
     * To calculate medicine fee
     * @param medicines
     * @return
     */
    private double calculateMedicineFee(List<Medicine> medicines) {
        double medicineFee = 0.0;
        for (Medicine medicine : medicines) {
            medicineFee += medicine.getPrice();
        }
        return medicineFee;
    }

}
