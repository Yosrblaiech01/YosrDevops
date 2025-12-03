package tn.esprit.studentmanagement.services;

import org.springframework.stereotype.Service;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;

import java.util.List;

@Service
public class EnrollmentService implements IEnrollment {

    private final EnrollmentRepository enrollmentRepository;

    // ✔️ Injection par constructeur (recommandée)
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment getEnrollmentById(Long idEnrollment) {
        // ✔️ Correction du .get() dangereux
        return enrollmentRepository.findById(idEnrollment).orElse(null);
    }

    @Override
    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteEnrollment(Long idEnrollment) {
        enrollmentRepository.deleteById(idEnrollment);
    }
}
