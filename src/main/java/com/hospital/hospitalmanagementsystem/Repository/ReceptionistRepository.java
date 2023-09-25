package com.hospital.hospitalmanagementsystem.Repository;

import com.hospital.hospitalmanagementsystem.Entity.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface ReceptionistRepository extends JpaRepository<Receptionist,Integer> {
    Receptionist findByEmail(String email);
    Receptionist findByReceptionistId(int receptionistId);
}
