package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Response.DoctorResponse;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public DoctorResponse doctorLogin(Doctor doctorRequest) {

        Doctor doctor = doctorRepository.findByEmail(doctorRequest.getEmail());
        DoctorResponse doctorResponse= new DoctorResponse();

        if (BCrypt.checkpw(doctorRequest.getPassword(), doctor.getPassword())) {

            doctorResponse.setDoctorId(doctor.getDoctorId());
            doctorResponse.setName(doctor.getFirstName() +" "+doctor.getLastName());
            doctorResponse.setEmail(doctor.getEmail());
            return doctorResponse;
        }
        else {
            throw new InvalidException("Wrong password");
        }
    }
    /*else {
        throw new InvalidException("The User has not registered yet.");
    }*/
}
