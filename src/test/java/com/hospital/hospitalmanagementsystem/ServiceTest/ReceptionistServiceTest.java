package com.hospital.hospitalmanagementsystem.ServiceTest;

import com.hospital.hospitalmanagementsystem.Entity.Billing;
import com.hospital.hospitalmanagementsystem.Entity.Receptionist;
import com.hospital.hospitalmanagementsystem.Handler.BillNotFound;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Repository.BillingRepository;
import com.hospital.hospitalmanagementsystem.Repository.ReceptionistRepository;
import com.hospital.hospitalmanagementsystem.Request.ReceptionistRequest;
import com.hospital.hospitalmanagementsystem.Response.ReceptionistResponse;
import com.hospital.hospitalmanagementsystem.Service.ReceptionistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReceptionistServiceTest {

    @InjectMocks
    private ReceptionistService receptionistService;

    @Mock
    private ReceptionistRepository receptionistRepository;

    @Mock
    private BillingRepository billingRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReceptionistLoginSuccess() {
        ReceptionistRequest request = new ReceptionistRequest();
        request.setEmail("receptionist@example.com");
        request.setPassword("password");

        Receptionist receptionist = new Receptionist();
        receptionist.setEmail("receptionist@example.com");
        receptionist.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));

        when(receptionistRepository.findByEmail(request.getEmail())).thenReturn(receptionist);

        ReceptionistResponse receptionistResponse = receptionistService.receptionistLogin(request);

        assertNotNull(receptionistResponse);
        assertEquals("receptionist@example.com", receptionistResponse.getEmail());
    }

    @Test
    public void testReceptionistLoginInvalid() {
        ReceptionistRequest receptionistRequest = new ReceptionistRequest();
        receptionistRequest.setEmail("receptionist@example.com");
        receptionistRequest.setPassword("wrong_password");

        Receptionist receptionist = new Receptionist();
        receptionist.setEmail("receptionist@example.com");
        receptionist.setPassword(BCrypt.hashpw("Password Correct", BCrypt.gensalt()));

        when(receptionistRepository.findByEmail(receptionistRequest.getEmail())).thenReturn(receptionist);

        assertThrows(InvalidException.class, () -> receptionistService.receptionistLogin(receptionistRequest));
    }

    @Test
    public void testReceptionistNotRegistered() {
        ReceptionistRequest receptionistRequest = new ReceptionistRequest();
        receptionistRequest.setEmail("unknown@example.com");
        receptionistRequest.setPassword("password");

        when(receptionistRepository.findByEmail(receptionistRequest.getEmail())).thenReturn(null);

        assertThrows(InvalidException.class, () -> receptionistService.receptionistLogin(receptionistRequest));
    }

    @Test
    public void testGetAllReceptionists() {
        Receptionist receptionist = new Receptionist();
        receptionist.setEmail("receptionist@example.com");
        List<Receptionist> receptionists = new ArrayList<>();
        receptionists.add(receptionist);

        when(receptionistRepository.findAll()).thenReturn(receptionists);

        List<Receptionist> response = receptionistService.getAllReceptionist();

        assertEquals(1, response.size());
        assertEquals("receptionist@example.com", response.get(0).getEmail());
    }

    @Test
    public void testSeeDues_BillExists() {
        Billing billing = new Billing();
        billing.setPaymentStatus("The bill is due.");
        List<Billing> billingList = new ArrayList<>();
        billingList.add(billing);
        when(billingRepository.findByPatientId(1)).thenReturn(billingList);

        double totalAmount = receptionistService.seeDues(1);

        assertEquals(0.00, totalAmount); // Assuming all bills have been paid (paymentStatus=Cleared)
        assertEquals("The bill is due.",billing.getPaymentStatus());
    }

    @Test
    public void testSeeDues_NoBillExists() {
        when(billingRepository.findByPatientId(2)).thenReturn(new ArrayList<>());

        assertThrows(BillNotFound.class, () -> receptionistService.seeDues(2));
    }

    @Test
    public void testDues_BillingExists() {
        Billing billing = new Billing();
        billing.setPaymentStatus("The bill is due.");
        List<Billing> billingList = new ArrayList<>();
        billingList.add(billing);

        when(billingRepository.findByPatientId(2)).thenReturn(billingList);

        receptionistService.dues(2);

        assertEquals("Cleared", billing.getPaymentStatus());
        verify(billingRepository, times(1)).save(billing);
    }

    @Test
    public void testDues_NoBillingExists() {
        when(billingRepository.findByPatientId(2)).thenReturn(new ArrayList<>());

        assertThrows(BillNotFound.class, () -> receptionistService.dues(2));
    }
}