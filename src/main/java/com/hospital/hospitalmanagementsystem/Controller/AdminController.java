package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Entity.Admin;
import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Entity.Patient;
import com.hospital.hospitalmanagementsystem.Entity.Receptionist;
import com.hospital.hospitalmanagementsystem.Handler.PatientNotFoundException;
import com.hospital.hospitalmanagementsystem.Handler.DoctorNotFoundException;
import com.hospital.hospitalmanagementsystem.Request.AdminRequest;
import com.hospital.hospitalmanagementsystem.Request.AvailableDoctorRequest;
import com.hospital.hospitalmanagementsystem.Request.DoctorRemoveRequest;
import com.hospital.hospitalmanagementsystem.Response.AdminResponse;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ReceptionistService receptionistService;

    @Operation(summary = "Login for Admin",
            description = "login admin by using email and password as inputs.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "Logged in successfully"),
    @ApiResponse(responseCode = "404",description = "Login failed")})
    @PostMapping(value="/login")
    @ResponseBody
    public AdminResponse adminLoginDetails(@Valid @RequestBody @Parameter(
            name = "adminRequest",
            description = "Admin object",
            required = true,
            content = @Content(schema = @Schema(implementation = AdminResponse.class)))
                                           AdminRequest adminRequest) throws InvalidException {
        return adminService.adminLogin(adminRequest);
    }

    @Operation(summary = "Doctor removal by Admin",
            description = "Doctor is removed by the admin using DoctorID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Doctor removed successfully"),
            @ApiResponse(responseCode = "404",description = "Failed")})
    @PostMapping(value="/remove/doctor")
    public void removeDoctor(@Valid @RequestBody @Parameter(
            name = "doctorRemoveRequest",
            description = "DoctorRemoveRequest object",
            required = true,
            content = @Content(schema = @Schema(implementation = DoctorRemoveRequest.class))) DoctorRemoveRequest doctorRemoveRequest) throws PatientNotFoundException, DoctorNotFoundException {
        adminService.removeDoctorByAdmin(doctorRemoveRequest);
    }

    @Operation(summary = "Doctor available or present",
            description = "Available doctor is fetched by checking the doctor status is present.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Available doctor fetched."),
            @ApiResponse(responseCode = "404",description = "Failed")})
    @GetMapping(value = "/available-doctors")
    public List<AvailableDoctorRequest> doctorsAvailable(){
        return doctorService.getAvailableDoctors();
    }

    @Operation(summary = "List of all doctor",
            description = "Admin can get the list of all doctors in the hospital")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "All doctors fetched."),
            @ApiResponse(responseCode = "404",description = "Failed")})
    @GetMapping(value = "/get/all/doctors")
    private List<Doctor> getDoctors(){
        return doctorService.getAllDoctors();
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

    @Operation(summary = "List of all receptionist",
            description = "Admin can get all the receptionist in the hospital")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "All receptionist fetched."),
            @ApiResponse(responseCode = "404",description = "Failed")})
    @GetMapping(value = "/get/all/receptionist")
    private List<Receptionist> getReceptionist(){
        return receptionistService.getAllReceptionist();
    }


}

