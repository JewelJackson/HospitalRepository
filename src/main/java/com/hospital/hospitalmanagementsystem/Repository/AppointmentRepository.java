package com.hospital.hospitalmanagementsystem.Repository;

import com.hospital.hospitalmanagementsystem.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Component
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    Optional<Appointment> findByAppointmentId(int appointmentId);
}
