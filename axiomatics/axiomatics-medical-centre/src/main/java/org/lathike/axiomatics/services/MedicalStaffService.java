package org.lathike.axiomatics.services;

import org.lathike.axiomatics.model.Doctor;
import org.lathike.axiomatics.model.Radiograph;
import org.lathike.axiomatics.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalStaffService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public MedicalStaffService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

}
