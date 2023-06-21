package com.diver.center.diver_center.service;

import com.diver.center.diver_center.model.Instructor;
import com.diver.center.diver_center.model.Licence;
import com.diver.center.diver_center.model.Trainee;
import com.diver.center.diver_center.repository.InstructorRepository;
import com.diver.center.diver_center.repository.TraineeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstructorService.class);

    private final InstructorRepository repository;
    private final LicenceService licenceService;
    private final TraineeRepository traineeRepository;

    public InstructorService(InstructorRepository repository, LicenceService licenceService, TraineeRepository traineeRepository) {
        this.repository = repository;
        this.licenceService = licenceService;
        this.traineeRepository = traineeRepository;
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
        if (optionalInstructor.isPresent() && optionalLicence.isPresent()) {
            Instructor updatedInstructor = optionalInstructor.get();
            updatedInstructor.setLicence(optionalLicence.get());

            return Optional.of(repository.save(updatedInstructor));
        } else {
            return Optional.empty();
        }
    }

    //tu zapisać licencje zamiast zapisywać instruktora
    public Optional<Instructor> detachLicenceFromInstructor(long instructorId) {
        Optional<Instructor> optionalInstructor = getInstructorById(instructorId);
        if (repository.existsById(instructorId)) {
            Instructor instructorWithLicence = optionalInstructor.get();
            instructorWithLicence.setLicence(null);
            return Optional.of(repository.save(instructorWithLicence));
        }
        return Optional.empty();
    }

    public Optional<Instructor> detachTraineesFromInstructor(long instructorId) {
        Optional<Instructor> optionalInstructor = getInstructorById(instructorId);
        if (optionalInstructor.isPresent() && !optionalInstructor.get().getTrainees().isEmpty()) {
            Instructor instructor = optionalInstructor.get();
            for (Trainee trainee : instructor.getTrainees()) {
                trainee.setInstructor(null);
                traineeRepository.save(trainee);
            }

            return Optional.of(instructor);
        }
        return Optional.empty();
    }



    public List<Instructor> querryInstructorFindByName(String name) {
        return repository.findAllByNameContaining(name);
    }

    public Instructor addTraineeToInstructor(long instructorId, long traineeId) {
        if (repository.existsById(instructorId) && traineeRepository.existsById(traineeId)) {
            Optional<Instructor> optionalInstructor = repository.findById(instructorId);
            Optional<Trainee> optionalTrainee = traineeRepository.findById(traineeId);
            Instructor instructor = optionalInstructor.get();
            Trainee trainee = optionalTrainee.get();
            trainee.setInstructor(instructor);
            traineeRepository.save(trainee);

            return instructor;
        }
        return null;
    }
}
