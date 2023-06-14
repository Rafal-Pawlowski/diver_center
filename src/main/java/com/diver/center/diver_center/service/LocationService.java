package com.diver.center.diver_center.service;

import com.diver.center.diver_center.model.Location;
import com.diver.center.diver_center.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
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

    public void deleteById(long id) {
        repository.deleteById(id);
    }


}
