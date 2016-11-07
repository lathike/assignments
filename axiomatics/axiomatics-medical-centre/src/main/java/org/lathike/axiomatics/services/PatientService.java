package org.lathike.axiomatics.services;

import org.lathike.axiomatics.model.Patient;
import org.lathike.axiomatics.model.QPatient;
import org.lathike.axiomatics.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<Patient> findPatientBySocialSecurityNumber(String socialSecurityNumber) {
        Assert.notNull(socialSecurityNumber, "Social security number should not be null");
        Assert.hasLength("yyyyMMddxxxx", "Social security number must be of type 19xxmmddxxxx");
        return patientRepository.findBySocialSecurityNumber(socialSecurityNumber);
    }

    public Patient registerPatient(Patient patient) {
        Assert.notNull(patient, "Patient must not be null");

        boolean patientAlreadyRegistered = patientRepository.exists(QPatient.patient.socialSecurityNumber.eq(patient.getSocialSecurityNumber()));
        Assert.isTrue(patientAlreadyRegistered == false, "Patient is already registered:" + patient.getSocialSecurityNumber());

        Patient registered = patientRepository.save(patient);
        return registered;
    }
}
