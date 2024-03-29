package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Entity.Patient;
import com.hospital.hospitalmanagementsystem.Entity.Receptionist;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Request.AvailableDoctorRequest;
import com.hospital.hospitalmanagementsystem.Request.ReceptionistRequest;
import com.hospital.hospitalmanagementsystem.Response.ReceptionistResponse;
import com.hospital.hospitalmanagementsystem.Service.BillingService;
import com.hospital.hospitalmanagementsystem.Service.DoctorService;
import com.hospital.hospitalmanagementsystem.Service.PatientService;
import com.hospital.hospitalmanagementsystem.Service.ReceptionistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/receptionist")
public class ReceptionistController {

    @Autowired
    private ReceptionistService receptionistService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Operation(summary = "Login for Receptionist",
            description = "login receptionist by using email and password as inputs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Logged in successfully"),
            @ApiResponse(responseCode = "404",description = "Login failed")})
    @PostMapping(value="/login")
    public ReceptionistResponse receptionistLoginDetails(@Valid @RequestBody @Parameter(
            name = "receptionistRequest",
            description = "Receptionist object",
            required = true,
            content = @Content(schema = @Schema(implementation = ReceptionistResponse.class)))
                                                         ReceptionistRequest receptionistRequest) throws InvalidException {
        return receptionistService.receptionistLogin(receptionistRequest);
    }

    @Operation(summary = "Doctor available or present",
            description = "Available doctor is fetched by checking the doctor status is present.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Available doctor fetched."),
            @ApiResponse(responseCode = "404",description = "Failed")})
    @GetMapping(value = "get/all/available/doctors")
    public List<AvailableDoctorRequest> doctorsAvailable(){
        return doctorService.getAvailableDoctors();
    }

    @Operation(summary = "List of all patients",
            description = "Admin can get the list of all patients in the hospital")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "All patients fetched."),
            @ApiResponse(responseCode = "404",description = "Failed")})
    @GetMapping(value = "/get/all/patients")
    private List<Patient> getPatients(){
        return patientService.getAllPatients();
    }

    @Operation(summary = "Bill Amount",
            description = "To see the dues or amount to be paid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Due amount displayed"),
            @ApiResponse(responseCode = "404",description = "Failed to display the amount")})
    @GetMapping(value = "/see/dues")
    public ResponseEntity<String> getBillAmount(@RequestParam("patientId") @Parameter(
            name = "patientId",
            description = "PatientId is passed as the parameter",
            required = true)int patientId){
        Double amount = receptionistService.seeDues(patientId);
        return ResponseEntity.ok("Due Amount : Rs "+amount);
    }

    @Operation(summary = "Change payment status",
            description = "To set the payment status as \"Cleared\"")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Payment status changed"),
            @ApiResponse(responseCode = "404",description = "Failed to change the payment status")})
    @PutMapping(value = "/due/clear")
    public ResponseEntity<String> clearDues(@RequestParam("patientId") @Parameter(
            name = "patientId",
            description = "PatientId is passed as the parameter",
            required = true) int patientId){
        receptionistService.clearDues(patientId);
        return ResponseEntity.ok("Dues cleared");
    }
}
