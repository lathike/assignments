package org.lathike.axiomatics.controllers;

import org.lathike.axiomatics.model.Doctor;
import org.lathike.axiomatics.model.MedicalStaff;
import org.lathike.axiomatics.model.Patient;
import org.lathike.axiomatics.services.AuthenticationService;
import org.lathike.axiomatics.services.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
@RequestMapping("patients")
public class PatientsController {

    private final Logger logger = LoggerFactory.getLogger(PatientsController.class);

    @Autowired private PatientService patientService;
    @Autowired private AuthenticationService authenticationService;

    @GET
    @RequestMapping("{socialSecurityNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Patient> getPatient(@PathVariable("socialSecurityNumber") String socialSecurityNumber) {
        Optional<Patient> patient = patientService.findPatientBySocialSecurityNumber(socialSecurityNumber);
        return patient
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Patient registerPatient(Patient patient) {
        Optional<MedicalStaff> staff = authenticationService.getLoggedInStaff();

        Patient registered = staff
                .filter(medicalStaff -> medicalStaff instanceof Doctor)
                .map(Doctor.class::cast)
                .filter(doctor -> doctor.getSocialSecurityNumber().equals(patient.getDoctor().getSocialSecurityNumber()))
                .map(doctor -> patientService.registerPatient(patient))
                .orElseThrow(() -> new IllegalArgumentException("You are not authorized to register patients on behalf of other staff"));
        return registered;
    }
}
