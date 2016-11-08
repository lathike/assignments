package org.lathike.axiomatics.services;

import com.googlecode.catchexception.apis.BDDCatchException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lathike.axiomatics.model.Doctor;
import org.lathike.axiomatics.model.MedicalStaff;
import org.lathike.axiomatics.model.Patient;
import org.lathike.axiomatics.model.Radiograph;
import org.lathike.axiomatics.model.Radiologist;
import org.lathike.axiomatics.repositories.RadiographRepository;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RadiographServiceUnitTest {

    private RadiographService SUT;

    @Mock private RadiographRepository radiographRepository;

    @Before
    public void setup() {
        SUT = new RadiographService(radiographRepository);
    }

    @Test
    public void givenPhysicianHasRequestedXRayWhenRequestedForXRayHeGetsIt() {
        String doctorsSocialSecurityNumber = "198001010000";
        MedicalStaff doctor = new Doctor();
        doctor.setSocialSecurityNumber(doctorsSocialSecurityNumber);

        Radiograph xray = new Radiograph();
        List<Radiograph> xrays = new ArrayList<>();
        xrays.add(xray);

        when(radiographRepository.findAll((Predicate) any())).thenReturn(xrays);
        when(radiographRepository.findAll((Predicate) any(), (OrderSpecifier<?>) any())).thenReturn(xrays);

        List<Radiograph> requestedXRays = SUT.getFor(doctor);
        assertThat(requestedXRays)
                .isNotEmpty()
                .hasSize(1)
                .contains(xray);
    }

    @Test
    public void givenRadiologistHasPerformedARadiographWhenRequestedForXRayHeGetsIt() {
        String radiologistSocialSecurityNumber = "198001010000";
        MedicalStaff radiologist = new Radiologist();
        radiologist.setSocialSecurityNumber(radiologistSocialSecurityNumber);

        Radiograph xray = new Radiograph();
        List<Radiograph> xrays = new ArrayList<>();
        xrays.add(xray);

        when(radiographRepository.findAll((Predicate) any(), (OrderSpecifier<?>) any())).thenReturn(xrays);

        List<Radiograph> requestedXRays = SUT.getFor(radiologist);
        assertThat(requestedXRays)
                .isNotEmpty()
                .hasSize(1)
                .contains(xray);
    }

    @Test
    public void givenAPatientHasAnXRayWeCanFetchIt() {
        String patientsSocialSecurityNumber = "198001010000";

        Radiograph xray = new Radiograph();
        List<Radiograph> xrays = new ArrayList<>();
        xrays.add(xray);

        when(radiographRepository.findAll((Predicate) any(), (OrderSpecifier<?>) any())).thenReturn(xrays);

        List<Radiograph> requestedXRays = SUT.findByPatient(patientsSocialSecurityNumber);
        assertThat(requestedXRays)
                .isNotEmpty()
                .hasSize(1)
                .contains(xray);
    }

    @Test
    public void givenNewMedicalStaffTypeWhenRequestingXRaysThrowsError() {
        MedicalStaff orderly = new MedicalStaff() {
            @Override
            public String getSocialSecurityNumber() {
                return "198001010000";
            }
        };

        BDDCatchException.when(SUT).getFor(orderly);
        then(caughtException()).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown Medical Staff type")
                .hasNoCause();
    }

    @Test
    public void givenAValidRadiographItWillSaved() {
        Radiograph validRadiograph = new Radiograph();
        validRadiograph.setPatient(new Patient());
        validRadiograph.getPatient().setSocialSecurityNumber("198001010000");
        validRadiograph.setRequestedBy(new Doctor());
        validRadiograph.getRequestedBy().setSocialSecurityNumber("198002020000");
        validRadiograph.setReason("cardiovascular endurance followup");

        when(radiographRepository.save((Radiograph) any())).thenReturn(validRadiograph);

        Radiograph saved = SUT.create(validRadiograph);
        assertThat(saved).isNotNull().isEqualTo(validRadiograph);
    }

}
