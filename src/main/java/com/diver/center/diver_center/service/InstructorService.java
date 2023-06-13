package com.diver.center.diver_center.service;

import com.diver.center.diver_center.model.Instructor;
import com.diver.center.diver_center.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstructorService.class);

    private final InstructorRepository repository;

    public InstructorService(InstructorRepository repository) {
        this.repository = repository;
    }

    public Instructor save(Instructor instructor) {
        return repository.save(instructor);
    }

    public Iterable<Instructor> getAllInstructors() {
        return repository.findAll();
    }

    public Optional<Instructor> getInstructorById(long id) {
        return repository.findById(id);
    }

    public void removeById(long id) {
        repository.deleteById(id);
    }

    public Instructor update(long id, String nameInstructor) {
        Optional<Instructor> optionalInstructor = repository.findById(id);
        if (optionalInstructor.isPresent()) {
            Instructor existingInstructor = optionalInstructor.get();
            existingInstructor.setName(nameInstructor);
            return repository.save(existingInstructor);
        }
        return null;
    }

    public Instructor completeUpdate(long id, Instructor updatedInstructor) {
        Optional<Instructor> optionalInstructor = repository.findById(id);
        if (optionalInstructor.isPresent()) {
            Instructor existingInstructor = optionalInstructor.get();
            existingInstructor.setName(updatedInstructor.getName());
            existingInstructor.setLicenceNumber(updatedInstructor.getLicenceNumber());
            existingInstructor.setAge(updatedInstructor.getAge());
            existingInstructor.setTrainingType(updatedInstructor.getTrainingType());
            return repository.save(existingInstructor);
        }
        return null;
    }
}
