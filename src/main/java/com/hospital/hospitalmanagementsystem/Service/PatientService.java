package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.Patient;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Response.PatientResponse;
import com.hospital.hospitalmanagementsystem.Repository.PatientRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public PatientResponse patientLogin(Patient patientRequest) {

        Patient patient = patientRepository.findByPhone(patientRequest.getPhone());
        PatientResponse patientResponse= new PatientResponse();


        if (BCrypt.checkpw(patientRequest.getPassword(), patient.getPassword())) {

            patientResponse.setPatientId(patient.getPatientId());
            patientResponse.setName(patient.getFirstName() +" "+patient.getLastName());
            patientResponse.setEmail(patient.getEmail());
            return patientResponse;
        }
        else {
            throw new InvalidException("Wrong password");
        }
    }
    /*else {
        throw new InvalidException("The User has not registered yet.");
    }*/
}
