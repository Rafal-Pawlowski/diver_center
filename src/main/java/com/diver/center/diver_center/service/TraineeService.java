package com.diver.center.diver_center.service;

import com.diver.center.diver_center.model.Trainee;
import com.diver.center.diver_center.repository.TraineeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TraineeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    private final TraineeRepository repository;

    public TraineeService(TraineeRepository traineeRepository) {
        this.repository = traineeRepository;
    }

    public Trainee save(Trainee trainee) {
        return repository.save(trainee);
    }

    public Iterable<Trainee> getAll() {
        return repository.findAll();
    }

    public Optional<Trainee> getById(long id) {
        return repository.findById(id);
    }

    public Optional<Trainee> update(long id, Trainee updadedTrainee){
        Optional<Trainee> traineeById = repository.findById(id);
        if(traineeById.isPresent()){
            Trainee existingTrainee = traineeById.get();
            existingTrainee.setName(updadedTrainee.getName());
            existingTrainee.setAge(updadedTrainee.getAge());
            existingTrainee.setGender(updadedTrainee.getGender());
            existingTrainee.setLanguage(updadedTrainee.getLanguage());
            return Optional.of(repository.save(existingTrainee));
        }
        return Optional.empty();
    }

    public void deleteById(long id){
        repository.deleteById(id);
    }

}



