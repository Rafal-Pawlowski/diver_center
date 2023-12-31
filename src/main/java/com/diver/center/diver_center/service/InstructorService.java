package com.diver.center.diver_center.service;

import com.diver.center.diver_center.exception_handler.InstructorServiceException.DeleteEntityException;
import com.diver.center.diver_center.exception_handler.InstructorServiceException.GetEntityException;
import com.diver.center.diver_center.exception_handler.InstructorServiceException.NoExistingEntityException;
import com.diver.center.diver_center.exception_handler.InstructorServiceException.SaveEntityException;
import com.diver.center.diver_center.model.Instructor;
import com.diver.center.diver_center.model.Licence;
import com.diver.center.diver_center.model.Trainee;
import com.diver.center.diver_center.repository.InstructorRepository;
import com.diver.center.diver_center.repository.TraineeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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
        try {
            return repository.save(instructor);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to save instructor: " + e.getMessage());
            throw new SaveEntityException("Failed to save instructor: " + e.getMessage());
        }
    }

    public Iterable<Instructor> getAllInstructors() {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to get all instructors: " + e.getMessage());
            throw new GetEntityException("Failed to get all instructors: " + e.getMessage());
        }
    }

    public Optional<Instructor> getInstructorById(long id) {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to get instructor by id: " + e.getMessage());
            throw new GetEntityException("Failed to get instructor by id: " + e.getMessage());
        }
    }

    public void removeById(long id) {
        try {
            repository.deleteById(id);
        } catch (DataAccessException e) {
            LOGGER.error("failed to delete instructor: " + e.getMessage());
            throw new DeleteEntityException("Failed to remove instructor: " + e.getMessage());
        }
    }

    public Instructor update(long id, String nameInstructor) {
        if (nameInstructor.length() >= 3) {
            Optional<Instructor> optionalInstructor = repository.findById(id);
            if (optionalInstructor.isPresent()) {
                Instructor existingInstructor = optionalInstructor.get();
                existingInstructor.setName(nameInstructor);
                return repository.save(existingInstructor);
            } else {
                LOGGER.error("Failed to update because there is no existing instructor with ID: " + id);
                throw new NoExistingEntityException("Failed to update because there is no existing instructor with ID: " + id);
            }
        } else {
            LOGGER.error("Failed to update instructor name because there is no name to update. Name supposed to be min 3 letters: " + nameInstructor);
            throw new IllegalArgumentException("Failed to update instructor because there is no name to update. Name should be min 3 letters: " + nameInstructor);
        }
    }


    public Optional<Instructor> completeUpdate(long id, Instructor updatedInstructor) {
        if (updatedInstructor.getName()!=null) {
            Optional<Instructor> optionalInstructor = repository.findById(id);
            if (optionalInstructor.isPresent()) {
                Instructor existingInstructor = optionalInstructor.get();
                existingInstructor.setName(updatedInstructor.getName());
                existingInstructor.setLicence(updatedInstructor.getLicence());
                existingInstructor.setAge(updatedInstructor.getAge());
                existingInstructor.setTrainingType(updatedInstructor.getTrainingType());
                return Optional.of(repository.save(existingInstructor));
            }
            return Optional.empty();
        }
        LOGGER.error("No instructor to update provided. Instructor supposed to have name");
        throw new IllegalArgumentException("No instructor to update provided. Instructor supposed to have name");
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
