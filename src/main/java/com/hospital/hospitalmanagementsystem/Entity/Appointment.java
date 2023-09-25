package com.hospital.hospitalmanagementsystem.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private Date appointmentDate;
    private boolean doctorAvailabilityStatus;
    private boolean treatmentStatus;
}
