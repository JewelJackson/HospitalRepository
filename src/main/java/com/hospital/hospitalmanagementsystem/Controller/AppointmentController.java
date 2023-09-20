package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Response.AppointmentRequest;
import com.hospital.hospitalmanagementsystem.Service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/schedule")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Operation(summary = "Schedule appointments", description = "Allows patient to book appointment with a doctor")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Appointment scheduled",content = @Content),
            @ApiResponse(responseCode = "400", description = "Appointment not scheduled", content = @Content)})
    @PostMapping(value = "/appointment")
    public ResponseEntity<String> bookAppointment(@RequestBody @Parameter(
            name = "appointmentRequest",
            description = "AppointmentRequest object",
            required = true,
            content = @Content(schema = @Schema(implementation = AppointmentRequest.class)))AppointmentRequest appointmentRequest) throws ParseException {
        appointmentService.scheduleAppointment(appointmentRequest);
        return ResponseEntity.ok("Appointment scheduled");
    }
}