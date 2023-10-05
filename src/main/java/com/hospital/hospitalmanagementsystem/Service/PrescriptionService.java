package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.*;
import com.hospital.hospitalmanagementsystem.Handler.AppointmentNotFound;
import com.hospital.hospitalmanagementsystem.Handler.MedicineNotFound;
import com.hospital.hospitalmanagementsystem.Handler.NotValidException;
import com.hospital.hospitalmanagementsystem.Repository.*;
import com.hospital.hospitalmanagementsystem.Request.PrescriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    /**
     * To add prescription
     * @param prescriptionRequest
     */
    public void addPrescription(PrescriptionRequest prescriptionRequest) {

        Optional<Appointment> appointment = appointmentRepository.findByAppointmentId(prescriptionRequest.getAppointmentId());
        if(!appointment.isPresent()){
            throw new AppointmentNotFound("Appointment not found.");
        }
        Appointment app = appointment.get();

        if (!(app.getDoctor().getDoctorId() == (prescriptionRequest.getDoctorId())) && !(app.getPatient().getPatientId() == (prescriptionRequest.getPatientId()))) {
            throw new NotValidException("Doctor ID or Patient ID does not match the appointment");
        }

        List<Medicine> medicineList = medicineRepository.findAll();
        if (!prescriptionRequest.getMedicine()
                .stream()
                .allMatch(medicineName -> medicineList.stream().anyMatch(medicine ->{
                    String medicineNameFromList = medicine.getMedicineName();
                    return medicineNameFromList != null && medicineNameFromList.equals(medicineName);
                }))) {
            throw new MedicineNotFound("This medicine is out of stock.");
        }

        Prescription prescription = new Prescription();
        prescription.setAppointment(app);
        prescription.setDoctor(app.getDoctor());
        prescription.setPatient(app.getPatient());
        prescription.setPrescriptionDate(app.getAppointmentDate());
        prescription.setCheckupNote(prescriptionRequest.getCheckupNote());
        prescription.setMedicine(medicineRepository.findAllByNameIn(prescriptionRequest.getMedicine()));
        prescription.setPrescriptionData(prescriptionRequest.getPrescriptionData());
        prescriptionRepository.save(prescription);
    }
}