package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Entity.Doctor;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Response.DoctorResponse;
import com.hospital.hospitalmanagementsystem.Service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Operation(summary = "Login for Doctor",
            description = "login doctor by using email and password as inputs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Logged in successfully"),
            @ApiResponse(responseCode = "404",description = "Login failed")})
    @PostMapping(value="/login")
    public DoctorResponse doctorLoginDetails(@Valid @RequestBody @Parameter(
            name = "doctorRequest",
            description = "Doctor object",
            required = true,
            content = @Content(schema = @Schema(implementation = DoctorResponse.class)))
                                                 Doctor doctorRequest) throws InvalidException {
        return doctorService.doctorLogin(doctorRequest);
    }
}
