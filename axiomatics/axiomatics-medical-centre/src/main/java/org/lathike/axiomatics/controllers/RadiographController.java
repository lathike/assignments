package org.lathike.axiomatics.controllers;

import org.lathike.axiomatics.model.MedicalStaff;
import org.lathike.axiomatics.model.Radiograph;
import org.lathike.axiomatics.services.AuthenticationService;
import org.lathike.axiomatics.services.RadiographService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Radiograph create(Radiograph radiograph) {
        return radiographService.create(radiograph);
    }
}
