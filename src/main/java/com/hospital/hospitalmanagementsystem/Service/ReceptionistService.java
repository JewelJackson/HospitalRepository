package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Entity.Billing;
import com.hospital.hospitalmanagementsystem.Entity.Patient;
import com.hospital.hospitalmanagementsystem.Entity.Receptionist;
import com.hospital.hospitalmanagementsystem.Handler.BillNotFound;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Handler.PatientNotFoundException;
import com.hospital.hospitalmanagementsystem.Repository.BillingRepository;
import com.hospital.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospital.hospitalmanagementsystem.Request.ReceptionistRequest;
import com.hospital.hospitalmanagementsystem.Response.ReceptionistResponse;
import com.hospital.hospitalmanagementsystem.Repository.ReceptionistRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptionistService {

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Receptionist Login
     * @param receptionistRequest
     * @return
     */
    public ReceptionistResponse receptionistLogin(ReceptionistRequest receptionistRequest) {

        Receptionist receptionist = receptionistRepository.findByEmail(receptionistRequest.getEmail());
        if(receptionist==null){
            throw new InvalidException("The User has not registered yet.");
        }
        ReceptionistResponse receptionistResponse= new ReceptionistResponse();

        if (BCrypt.checkpw(receptionistRequest.getPassword(), receptionist.getPassword())) {

            receptionistResponse.setReceptionistId(receptionist.getReceptionistId());
            receptionistResponse.setName(receptionist.getFirstName() +" "+receptionist.getLastName());
            receptionistResponse.setEmail(receptionist.getEmail());
            return receptionistResponse;
        }
        else {
            throw new InvalidException("Wrong password");
        }
    }

    /**
     * To get all receptionist
     * @return
     */
    public List<Receptionist> getAllReceptionist(){
        List<Receptionist> allReceptionists = receptionistRepository.findAll();
        return allReceptionists;
    }

    /**
     * To see the dues
     * @param patientId
     * @return
     */
    public Double seeDues(int patientId){
        List<Billing> billingList = billingRepository.findByPatientId(patientId);
        if (billingList.isEmpty()){
            throw new BillNotFound("No such bill exist");
        }
        for (Billing bill : billingList){
            if (bill.getPaymentStatus().equalsIgnoreCase("The bill is due.")){
                return billingRepository.getTotalAmountByPatientId(patientId);
            }
        }return 0.00;
    }

    /**
     * To set the payment status as "Cleared"
     * @param patientId
     */
    public void clearDues(int patientId){
        List<Billing> billingList = billingRepository.findByPatientId(patientId);
        if (billingList.isEmpty()){
            throw new BillNotFound("No such bill exist");
        }
        for (Billing bill : billingList){
            if (bill.getPaymentStatus().equalsIgnoreCase("The bill is due.")){
                bill.setPaymentStatus("Cleared");
                billingRepository.save(bill);
            }
        }
    }
}
