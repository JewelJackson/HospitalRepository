package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.Admin;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Response.AdminResponse;
import com.hospital.hospitalmanagementsystem.Repository.AdminRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public AdminResponse adminLogin(Admin adminRequest) {

        Admin admin = adminRepository.findByEmail(adminRequest.getEmail());
        AdminResponse adminResponse = new AdminResponse();

        if (BCrypt.checkpw(adminRequest.getPassword(), admin.getPassword())) {

            adminResponse.setAdminId(admin.getAdminId());
            adminResponse.setName(admin.getFirstName() + " " + admin.getLastName());
            return adminResponse;
        }
        else {
            throw new InvalidException("Wrong password");
        }
    }
    /*else {
        throw new InvalidException("The User has not registered yet.");
    }*/
}


