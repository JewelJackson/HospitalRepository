package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.*;
import com.hospital.hospitalmanagementsystem.Handler.*;
import com.hospital.hospitalmanagementsystem.Repository.AppointmentRepository;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospital.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospital.hospitalmanagementsystem.Repository.ReceptionistRepository;
import com.hospital.hospitalmanagementsystem.Request.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ReceptionistRepository receptionistRepository;

    public void scheduleAppointmentByPatient(AppointmentRequest appointmentRequest) throws ParseException,NotValidException {
        List<Doctor> doctorList = doctorRepository.findByDoctorStatus("Present");

        Doctor doctorSelected = doctorList.stream()
                .filter(doctor -> appointmentRequest.getDoctorEmail().equals(doctor.getEmail()))
                .findFirst()
                .orElse(null);

        if (doctorSelected != null) {
            Patient patient = patientRepository.findByEmail(appointmentRequest.getPatientEmail());
            if (patient == null) {
                throw new PatientNotFoundException("Patient not found.");
            }

            //List<Appointment> appointmentList = appointmentRepository.findAll();

            if (appointmentRepository.findAll().stream()
                    .anyMatch(app -> app.getDoctor().getDoctorId() == doctorSelected.getDoctorId() && app.getPatient().getPatientId() == patient.getPatientId())) {
                throw new NotValidException("The appointment has already been scheduled.");
            }

            appointmentRepository.save(getAppointment(appointmentRequest, doctorSelected, patient));

        } else {
            throw new DoctorNotFoundException("Doctor not found");
        }
    }
    private static Appointment getAppointment(AppointmentRequest appointmentRequest, Doctor doctorSelected, Patient patient) throws ParseException {
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctorSelected);
        appointment.setPatient(patient);
        appointment.setTreatmentStatus(false);
        appointment.setDoctorAvailabilityStatus(true);

        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        Date utilDate = d.parse(appointmentRequest.getAppointmentDate());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        appointment.setAppointmentDate(sqlDate);
        return appointment;
    }

}
