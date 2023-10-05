package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.Admin;
import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Handler.DoctorNotFoundException;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospital.hospitalmanagementsystem.Request.AdminRequest;
import com.hospital.hospitalmanagementsystem.Request.DoctorRemoveRequest;
import com.hospital.hospitalmanagementsystem.Response.AdminResponse;
import com.hospital.hospitalmanagementsystem.Repository.AdminRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Admin Login
     * @param adminRequest
     * @return
     */
    public AdminResponse adminLogin(AdminRequest adminRequest) {

        Admin admin = adminRepository.findByEmail(adminRequest.getEmail());
        if(admin==null){
            throw new InvalidException("The User has not registered yet.");
        }
        AdminResponse adminResponse = new AdminResponse();

        if (BCrypt.checkpw(adminRequest.getPassword(), admin.getPassword())) {

            adminResponse.setAdminId(admin.getAdminId());
            adminResponse.setName(admin.getFirstName() + " " + admin.getLastName());
            adminResponse.setEmail(admin.getEmail());
            return adminResponse;
        }
        else {
            throw new InvalidException("Wrong password");
        }

    }

    /**
     * To remove doctor, that is to change the doctor status to "Not Present"
     * @param doctorRemoveRequest
     */
    public void removeDoctorByAdmin(DoctorRemoveRequest doctorRemoveRequest) {

        Admin admin = adminRepository.findByEmail(doctorRemoveRequest.getAdEmail());

        Optional<Doctor> doctorOptional = doctorRepository.findByDoctorId(doctorRemoveRequest.getDoctorID());
        if (!doctorOptional.isPresent()) {
            throw new DoctorNotFoundException("Doctor not found");
        }

        Doctor doctor = doctorOptional.get();
        doctor.setDoctorStatus("Not present");

        doctorRepository.save(doctor);
    }

}



