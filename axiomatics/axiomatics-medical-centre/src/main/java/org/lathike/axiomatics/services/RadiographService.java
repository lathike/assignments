package org.lathike.axiomatics.services;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.lathike.axiomatics.model.Doctor;
import org.lathike.axiomatics.model.MedicalStaff;
import org.lathike.axiomatics.model.QRadiograph;
import org.lathike.axiomatics.model.Radiograph;
import org.lathike.axiomatics.model.Radiologist;
import org.lathike.axiomatics.repositories.RadiographRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RadiographService {

    private final RadiographRepository radiographRepository;

    @Autowired
    public RadiographService(RadiographRepository radiographRepository) {
        this.radiographRepository = radiographRepository;
    }

    public Radiograph create(Radiograph radiograph) {
        Assert.notNull(radiograph, "Radiograph must not be null");
        Assert.notNull(radiograph.getPatient(), "Radiograph patient must not be null");
        Assert.notNull(radiograph.getRequestedBy(), "Radiographs requesting medical staff member must not be null");
        Assert.notNull(radiograph.getReason(), "Reason for performing the radiograph must not be null");
        Assert.isTrue(radiograph.getReason().trim().length() > 0, "Reason for performing the radiograph must not be empty");

        return radiographRepository.save(radiograph);
    }

    public List<Radiograph> getFor(MedicalStaff medicalStaff) {
        Assert.notNull(medicalStaff, "Staff member cannot be null");
        assertIsValidSocialSecurityNumber(medicalStaff.getSocialSecurityNumber());

        List<Radiograph> xrays;
        if (medicalStaff instanceof Doctor) {
            xrays = findByRequestingPhysician(medicalStaff.getSocialSecurityNumber());
        } else if (medicalStaff instanceof Radiologist) {
            xrays = findByPerformingRadiologist(medicalStaff.getSocialSecurityNumber());
        } else {
            throw new IllegalArgumentException("Unknown Medical Staff type:" + medicalStaff.getClass());
        }
        return xrays;

    }

    public List<Radiograph> findByRequestingPhysician(String socialSecurityNumber) {
        assertIsValidSocialSecurityNumber(socialSecurityNumber);
        return findBy(QRadiograph.radiograph.requestedBy.socialSecurityNumber.eq(socialSecurityNumber));
    }

    public List<Radiograph> findByPerformingRadiologist(String socialSecurityNumber) {
        assertIsValidSocialSecurityNumber(socialSecurityNumber);
        return findBy(QRadiograph.radiograph.performedBy.socialSecurityNumber.eq(socialSecurityNumber));
    }

    public List<Radiograph> findByPatient(String socialSecurityNumber) {
        assertIsValidSocialSecurityNumber(socialSecurityNumber);
        return findBy(QRadiograph.radiograph.patient.socialSecurityNumber.eq(socialSecurityNumber), new OrderSpecifier(Order.DESC, QRadiograph.radiograph.requestedOn));
    }

    private List<Radiograph> findBy(Predicate predicate, OrderSpecifier... orderSpecifiers) {
        Assert.notNull(predicate, "Cannot find for null predicates of radiographs");
        Iterable<Radiograph> xrays = radiographRepository.findAll(predicate, orderSpecifiers);
        return StreamSupport.stream(xrays.spliterator(), false).collect(Collectors.toList());
    }

    private void assertIsValidSocialSecurityNumber(String socialSecurityNumber) {
        Assert.notNull(socialSecurityNumber, "Social security number should not be null");
        Assert.hasLength("yyyyMMddxxxx", "Social security number must be of type 19xxmmddxxxx");
    }
}
