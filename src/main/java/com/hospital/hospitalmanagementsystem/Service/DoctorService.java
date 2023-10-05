package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Request.AvailableDoctorRequest;
import com.hospital.hospitalmanagementsystem.Request.DoctorRequest;
import com.hospital.hospitalmanagementsystem.Response.DoctorResponse;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Doctor Login
     * @param doctorRequest
     * @return
     */
    public DoctorResponse doctorLogin(DoctorRequest doctorRequest) {

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

    /**
     * Available Doctor
     * @return
     */
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

    /**
     * To get all doctors
     * @return
     */
    public List<Doctor> getAllDoctors() {
        List<Doctor> allDoctors = doctorRepository.findAll();
        return allDoctors;
    }
}






