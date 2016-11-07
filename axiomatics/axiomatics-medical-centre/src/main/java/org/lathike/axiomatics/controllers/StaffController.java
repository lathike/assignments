package org.lathike.axiomatics.controllers;

import org.lathike.axiomatics.model.Doctor;
import org.lathike.axiomatics.model.Radiograph;
import org.lathike.axiomatics.services.MedicalStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("staff")
public class StaffController {

    @Autowired private MedicalStaffService staffService;

    @GET
    @RequestMapping("doctors/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Doctor> findAll() {
        return staffService.findAllDoctors();
    }
}
