package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.Receptionist;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Response.ReceptionistResponse;
import com.hospital.hospitalmanagementsystem.Repository.ReceptionistRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceptionistService {

    @Autowired
    private ReceptionistRepository receptionistRepository;

    public ReceptionistResponse receptionistLogin(Receptionist receptionistRequest) {

        Receptionist receptionist = receptionistRepository.findByEmail(receptionistRequest.getEmail());
        ReceptionistResponse receptionistResponse= new ReceptionistResponse();

        if (BCrypt.checkpw(receptionistRequest.getPassword(), receptionist.getPassword())) {

            receptionistResponse.setReceptionistId(receptionist.getReceptionistId());
            receptionistResponse.setName(receptionist.getFirstName() +" "+receptionist.getLastName());
            return receptionistResponse;
        }
        else {
            throw new InvalidException("Wrong password");
        }
    }
    /*else {
        throw new InvalidException("The User has not registered yet.");
    }*/
}
