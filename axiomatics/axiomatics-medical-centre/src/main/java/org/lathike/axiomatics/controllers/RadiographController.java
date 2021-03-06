package org.lathike.axiomatics.controllers;

import org.lathike.axiomatics.model.Doctor;
import org.lathike.axiomatics.model.MedicalStaff;
import org.lathike.axiomatics.model.Radiograph;
import org.lathike.axiomatics.services.AuthenticationService;
import org.lathike.axiomatics.services.RadiographService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
@RequestMapping("radiographs")
public class RadiographController {

    @Autowired private AuthenticationService authenticationService;
    @Autowired private RadiographService radiographService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Radiograph> xrays() {
        Optional<MedicalStaff> staff = authenticationService.getLoggedInStaff();
        return staff.map(medicalStaff -> radiographService.getFor(medicalStaff)).orElse(new ArrayList<>());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("requestedby")
    public Iterable<Radiograph> xrayRequestedByDoctor(@RequestParam("doctor") String socialSecurityNumber) {
        return radiographService.findByRequestingPhysician(socialSecurityNumber);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("performedby")
    public Iterable<Radiograph> xrayPerformedByRadiologist(@RequestParam("radiologist") String socialSecurityNumber) {
        return radiographService.findByPerformingRadiologist(socialSecurityNumber);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("ofpatient")
    public Iterable<Radiograph> xraysPerformedOnPatient(@RequestParam("patient") String socialSecurityNumber) {
        return radiographService.findByPatient(socialSecurityNumber);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("create")
    public Radiograph create(@RequestBody Radiograph radiograph) {
        Optional<Doctor> doctor = authenticationService.getLoggedInPhysician();
        doctor.ifPresent(doc -> radiograph.setRequestedBy(doc));
        return radiographService.create(radiograph);
    }
}
