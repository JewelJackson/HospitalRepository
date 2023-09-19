package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Entity.Receptionist;
import com.hospital.hospitalmanagementsystem.Handler.InvalidException;
import com.hospital.hospitalmanagementsystem.Response.ReceptionistResponse;
import com.hospital.hospitalmanagementsystem.Service.ReceptionistService;
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
@RequestMapping(value = "/receptionist")
public class ReceptionistController {

    @Autowired
    private ReceptionistService receptionistService;

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
                                                             Receptionist receptionistRequest) throws InvalidException {
        return receptionistService.receptionistLogin(receptionistRequest);
    }
}
