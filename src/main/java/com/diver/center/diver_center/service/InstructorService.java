package com.diver.center.diver_center.service;

import com.diver.center.diver_center.model.Instructor;
import com.diver.center.diver_center.model.Licence;
import com.diver.center.diver_center.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstructorService.class);

    private final InstructorRepository repository;
    private final LicenceService licenceService;

    public InstructorService(InstructorRepository repository, LicenceService licenceService) {
        this.repository = repository;
        this.licenceService = licenceService;
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

    public Optional<Instructor> completeUpdate(long id, Instructor updatedInstructor) {
        Optional<Instructor> optionalInstructor = repository.findById(id);
        if (optionalInstructor.isPresent()) {
            Instructor existingInstructor = optionalInstructor.get();
            existingInstructor.setName(updatedInstructor.getName());
            existingInstructor.setLicence(updatedInstructor.getLicence());
            existingInstructor.setAge(updatedInstructor.getAge());
            existingInstructor.setTrainingType(updatedInstructor.getTrainingType());
            return Optional.of(repository.save(existingInstructor));
        }
        return Optional.empty(); //najlepiej żeby nie zwracać null, drugie rozwiązanie wyjątek
    }

    public Optional<Instructor> setLicence(long instructorId, long licenceId) {
        Optional<Instructor> optionalInstructor = getInstructorById(instructorId);
        Optional<Licence> optionalLicence = licenceService.getById(licenceId);
        if (optionalInstructor.isPresent() && optionalLicence.isPresent()){
            Instructor updatedInstructor = optionalInstructor.get();
            updatedInstructor.setLicence(optionalLicence.get());

            return Optional.of(repository.save(updatedInstructor));
        } else {
            return Optional.empty();
        }


    }


}
