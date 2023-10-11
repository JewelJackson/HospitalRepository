package com.hospital.hospitalmanagementsystem.ServiceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hospital.hospitalmanagementsystem.Entity.Medicine;
import com.hospital.hospitalmanagementsystem.Repository.MedicineRepository;
import com.hospital.hospitalmanagementsystem.Request.MedicineRequest;
import com.hospital.hospitalmanagementsystem.Service.MedicineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MedicineServiceTest {

    @InjectMocks
    private MedicineService medicineService;

    @Mock
    private MedicineRepository medicineRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testAddMedicineSuccess() {
        MedicineRequest medicineRequest = new MedicineRequest();
        medicineRequest.setMedicineName("Medicine A");
        medicineRequest.setPrice(10.0);

        when(medicineRepository.save(any(Medicine.class))).thenAnswer(invocation -> {
            Medicine savedMedicine = invocation.getArgument(0);
            savedMedicine.setMedicineId(1);
            return savedMedicine;
        });

        assertDoesNotThrow(() -> medicineService.add(medicineRequest));

        verify(medicineRepository, times(1)).save(any(Medicine.class));
    }
}
