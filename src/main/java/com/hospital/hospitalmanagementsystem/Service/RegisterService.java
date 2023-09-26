package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.*;
import com.hospital.hospitalmanagementsystem.Repository.AdminRepository;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospital.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospital.hospitalmanagementsystem.Repository.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;


@Service
@PropertySource("classpath:application.properties")
public class RegisterService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${email.username}")
    private String emailUsername;

    @Value("${email.subject}")
    private String emailSubject;

    @Value("${email.message}")
    private String emailMessage;

    private static final String ADMIN = "Admin";
    private static final String DOCTOR = "Doctor";
    private static final String PATIENT = "Patient";
    private static final String RECEPTIONIST = "Receptionist";

    /**
     * To register the details of admin, doctor, patient and receptionist
     * @param registerRequest
     */
    public void save(Register registerRequest) {

        Admin admin = new Admin();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Receptionist receptionist = new Receptionist();

        String role = registerRequest.getRole();
        String hashedPassword = BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt());

        if (role.equalsIgnoreCase(ADMIN)) {
            admin.setFirstName(registerRequest.getFirstName());
            admin.setLastName(registerRequest.getLastName());
            admin.setPhone(registerRequest.getPhone());
            admin.setEmail(registerRequest.getEmail());
            admin.setPassword(hashedPassword);
            adminRepository.save(admin);
        }
        else if (role.equalsIgnoreCase(DOCTOR)) {
            doctor.setFirstName(registerRequest.getFirstName());
            doctor.setLastName(registerRequest.getLastName());
            doctor.setGender(registerRequest.getGender());
            doctor.setPhone(registerRequest.getPhone());
            doctor.setDepartment(registerRequest.getDepartment());
            doctor.setDoctorStatus(registerRequest.getDoctorStatus());
            doctor.setEmail(registerRequest.getEmail());
            doctor.setPassword(hashedPassword);
            doctorRepository.save(doctor);
        }
        else if (role.equalsIgnoreCase(PATIENT)) {
            patient.setFirstName(registerRequest.getFirstName());
            patient.setLastName(registerRequest.getLastName());
            patient.setGender(registerRequest.getGender());
            patient.setPhone(registerRequest.getPhone());
            patient.setEmail(registerRequest.getEmail());
            patient.setAge(registerRequest.getAge());
            patient.setAddress(registerRequest.getAddress());
            patient.setPassword(hashedPassword);
            patientRepository.save(patient);
        }
        else if (role.equalsIgnoreCase(RECEPTIONIST)) {
            receptionist.setFirstName(registerRequest.getFirstName());
            receptionist.setLastName(registerRequest.getLastName());
            receptionist.setGender(registerRequest.getGender());
            receptionist.setPhone(registerRequest.getPhone());
            receptionist.setEmail(registerRequest.getEmail());
            receptionist.setPassword(hashedPassword);
            receptionistRepository.save(receptionist);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailUsername);
        message.setTo(registerRequest.getEmail());
        message.setSubject(emailSubject);
        message.setText(emailMessage);

        emailSender.send(message);
    }
}