package com.diver.center.diver_center.service;

import com.diver.center.diver_center.model.Instructor;
import com.diver.center.diver_center.model.Location;
import com.diver.center.diver_center.repository.InstructorRepository;
import com.diver.center.diver_center.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository repository;
    private final InstructorRepository instructorRepository;

    public LocationService(LocationRepository repository, InstructorRepository instructorRepository) {
        this.repository = repository;
        this.instructorRepository = instructorRepository;
    }

    public Location save(Location location) {
        return repository.save(location);

    }

    public Iterable<Location> getAllLocations() {
        return repository.findAll();
    }

    public Optional<Location> getById(long id) {
        return repository.findById(id);
    }

    public Optional<Location> update(long id, Location updatedLocation) {
        Optional<Location> optionalLocation = repository.findById(id);
        if (optionalLocation.isPresent()) {
//            Location existingLocation = optionalLocation.get();
//            existingLocation.setName(updatedLocation.getName());
//            existingLocation.setType(updatedLocation.getType());
//            existingLocation.setDifficultyLevel(updatedLocation.getDifficultyLevel());
//            existingLocation.setMaxDepth(updatedLocation.getMaxDepth());
            updatedLocation.setId(id);
            return Optional.of(repository.save(updatedLocation));
        } else {
            return Optional.empty();
        }
    }
    public Optional<Location> addInstructorToLocation(long instructorId, long locationId){
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        Optional<Location> optionalLocation = repository.findById(locationId);
        if(optionalLocation.isPresent()&& optionalInstructor.isPresent()){
            Instructor instructor = optionalInstructor.get();
            Location location = optionalLocation.get();
            location.addInstructorSet(instructor);
            return Optional.of(repository.save(location));
        }
        return Optional.empty();
    }
    public Optional<Location> deleteInstructorFromLocation(long instructorId, long locationId){
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        Optional<Location> optionalLocation = repository.findById(locationId);
        if(optionalLocation.isPresent()&& optionalInstructor.isPresent()){
            Instructor instructor = optionalInstructor.get();
            Location location = optionalLocation.get();
            location.removeInstructorSet(instructor);
            return Optional.of(repository.save(location));
        }
        return Optional.empty();
    }


    public void deleteById(long id) {
        repository.deleteById(id);
    }


}
