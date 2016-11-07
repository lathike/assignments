package org.lathike.axiomatics.services;

import org.lathike.axiomatics.model.Doctor;
import org.lathike.axiomatics.model.MedicalStaff;
import org.lathike.axiomatics.repositories.MedicalStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final MedicalStaffRepository medicalStaffRepository;

    @Autowired
    public AuthenticationService(MedicalStaffRepository medicalStaffRepository) {
        this.medicalStaffRepository = medicalStaffRepository;
    }

    public Optional<MedicalStaff> getLoggedInStaff() {
        String socialSecurityNumber = getPrincipalsSocialSecurityNumber();
        return medicalStaffRepository.findBySocialSecurityNumber(socialSecurityNumber);
    }

    public Optional<Doctor> getLoggedInPhysician() {
        String socialSecurityNumber = getPrincipalsSocialSecurityNumber();
        Optional<MedicalStaff> staff = medicalStaffRepository.findBySocialSecurityNumber(socialSecurityNumber);

        return staff.filter(medicalStaff -> medicalStaff instanceof Doctor)
                .map(Doctor.class::cast);
    }

    private String getPrincipalsSocialSecurityNumber() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String socialSecurityNumber = (String) auth.getPrincipal();
        return socialSecurityNumber;
    }


}
