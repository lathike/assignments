package org.lathike.axiomatics.services;

import com.googlecode.catchexception.apis.BDDCatchException;
import com.querydsl.core.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lathike.axiomatics.model.Patient;
import org.lathike.axiomatics.repositories.PatientRepository;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceUnitTest {

    private PatientService SUT;

    @Mock private PatientRepository patientRepository;

    @Before
    public void setup() {
        SUT = new PatientService(patientRepository);
    }

    @Test
    public void givenPatientDoesNotExistFindPatientBySocialSecurityNumberReturnsAnEmpty() {
        String nonExistingSsn = "198001010000";
        when(patientRepository.findBySocialSecurityNumber(nonExistingSsn)).thenReturn(Optional.empty());

        Optional<Patient> patient = SUT.findPatientBySocialSecurityNumber(nonExistingSsn);
        assertThat(patient).isEmpty();
    }

    @Test
    public void givenPatientAlreadyExistsRegisterPatientThrowsAssertionError() {
        Patient alreadyRegisteredPatient = new Patient();
        alreadyRegisteredPatient.setSocialSecurityNumber("198001010000");
        when(patientRepository.exists((Predicate) any())).thenReturn(true);

        BDDCatchException.when(SUT).registerPatient(alreadyRegisteredPatient);
        then(caughtException()).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Patient is already registered:" + alreadyRegisteredPatient.getSocialSecurityNumber())
                .hasNoCause();
    }

    @Test
    public void givenPatientIsNotAlreadyRegisteredRegisterPatientCreatesANewPatient() {
        Patient patient = new Patient();
        patient.setSocialSecurityNumber("198001010000");
        when(patientRepository.exists((Predicate) any())).thenReturn(false);
        when(patientRepository.save((Patient) any())).thenReturn(patient);

        Patient registered = SUT.registerPatient(patient);
        assertThat(registered).isNotNull().isEqualTo(patient);
    }

}
