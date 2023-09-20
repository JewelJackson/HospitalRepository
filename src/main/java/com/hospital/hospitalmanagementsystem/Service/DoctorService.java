package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Response.AvailableDoctorRequest;
import com.hospital.hospitalmanagementsystem.Response.DoctorResponse;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public DoctorResponse doctorLogin(Doctor doctorRequest) {

        Doctor doctor = doctorRepository.findByEmail(doctorRequest.getEmail());
        if(doctor==null){
            throw new InvalidException("The User has not registered yet.");
        }
        DoctorResponse doctorResponse = new DoctorResponse();

        if (BCrypt.checkpw(doctorRequest.getPassword(), doctor.getPassword())) {

            doctorResponse.setDoctorId(doctor.getDoctorId());
            doctorResponse.setName(doctor.getFirstName() + " " + doctor.getLastName());
            doctorResponse.setEmail(doctor.getEmail());
            return doctorResponse;
        } else {
            throw new InvalidException("Wrong password");
        }

    }



    public List<AvailableDoctorRequest> getAvailableDoctors() {
        List<AvailableDoctorRequest> availableDoctorList = new ArrayList<>();
        List<Doctor> doctorList = doctorRepository.findAll();

        List<Doctor> availableDoctors = doctorList.stream()
                .filter(doctor -> "Present".equalsIgnoreCase(doctor.getDoctorStatus()))
                .toList();

        availableDoctors.forEach(doctor -> {
            AvailableDoctorRequest doc = new AvailableDoctorRequest();
            doc.setName(doctor.getFirstName() + " " + doctor.getLastName());
            doc.setDepartment(doctor.getDepartment());
            availableDoctorList.add(doc);
        });
        return availableDoctorList;
    }

    public void getAllDoctors(){
        doctorRepository.findAll();
    }
}






