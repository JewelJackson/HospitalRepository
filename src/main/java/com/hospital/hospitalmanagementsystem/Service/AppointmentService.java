package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.Appointment;
import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Entity.Patient;
import com.hospital.hospitalmanagementsystem.Handler.DoctorNotFoundException;
import com.hospital.hospitalmanagementsystem.Handler.PatientNotFoundException;
import com.hospital.hospitalmanagementsystem.Repository.AppointmentRepository;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospital.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospital.hospitalmanagementsystem.Response.AppointmentRequest;
import com.hospital.hospitalmanagementsystem.Response.AvailableDoctorRequest;
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

    public void scheduleAppointment(AppointmentRequest appointmentRequest) throws ParseException {
        List<Doctor> doctorList = doctorRepository.findByDoctorStatus("Present");
        String doctorEmail = appointmentRequest.getDoctor().getEmail();

        Doctor doctorSelected = doctorList.stream()
                .filter(doctor -> doctorEmail.equals(doctor.getEmail()))
                .findFirst()
                .orElse(null);

        if (doctorSelected != null) {
            String patientEmail = appointmentRequest.getPatient().getEmail();
            Patient patient = patientRepository.findByEmail(patientEmail);
            if (patient == null) {
                throw new PatientNotFoundException("Patient not found.");
            }


            Appointment appointment = new Appointment();
            appointment.setDoctor(doctorSelected);
            appointment.setPatient(patient);
            appointment.setTreatmentStatus(false);
            appointment.setDoctorAvailabilityStatus(true);

           /* appointment.setDoctor(doctorSelected.getFirstName() + " " + doctorSelected.getLastName());
            appointment.setPatient(patient.getFirstName() + " " + patient.getLastName());*/

            SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
            Date utilDate = d.parse(appointmentRequest.getAppointmentDate());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            appointment.setAppointmentDate(sqlDate);

            appointmentRepository.save(appointment);

        } else {
            throw new DoctorNotFoundException("Doctor not found");
        }
    }
}
