package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.*;
import com.hospital.hospitalmanagementsystem.Repository.AdminRepository;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospital.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospital.hospitalmanagementsystem.Repository.ReceptionistRepository;
import com.hospital.hospitalmanagementsystem.Request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


@Service
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
    public void register(RegisterRequest registerRequest) {

        Admin admin = new Admin();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Receptionist receptionist = new Receptionist();

        String role = registerRequest.getRole();
        String hashedPassword = BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt());

        if (role.equalsIgnoreCase(RegisterService.ADMIN)) {
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
        message.setText("Hello"+" " +registerRequest.getFirstName()+" "+registerRequest.getLastName()+","+"\n" +emailMessage);

        emailSender.send(message);
    }

    /**
     * To upload the patient into the database which is obtained from the offline hospital camps
     * @param file
     * @throws IOException
     */
    public void extractFromCSV(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             InputStreamReader reader = new InputStreamReader(inputStream);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord record : csvParser) {
                Patient patient = new Patient();
                patient.setFirstName(record.get("firstName"));
                patient.setLastName(record.get("lastName"));
                patient.setAge(record.get("age"));
                patient.setGender(record.get("gender"));
                patient.setPhone(record.get("phone"));
                patient.setEmail(record.get("email"));
                patient.setAddress(record.get("address"));
                patient.setPassword(BCrypt.hashpw("12345678",BCrypt.gensalt()));

                patientRepository.save(patient);
            }
        }
    }
}
