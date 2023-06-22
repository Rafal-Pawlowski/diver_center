package com.diver.center.diver_center.controller;

import com.diver.center.diver_center.model.Location;
import com.diver.center.diver_center.repository.InstructorRepository;
import com.diver.center.diver_center.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    private final LocationService service;
    private final InstructorRepository instructorRepository;


    public LocationController(LocationService service, InstructorRepository instructorRepository) {
        this.service = service;
        this.instructorRepository = instructorRepository;
    }

    @PostMapping
    public ResponseEntity<Location> saveLocation(@RequestBody Location location) {
        Location savedLocation = service.save(location);
        LOGGER.info("{} saved", savedLocation);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedLocation);
    }

    @GetMapping
    public Iterable<Location> getAllLocations() {
        return service.getAllLocations();
    }

    @GetMapping("/{id}")
    public Optional<Location> getLocationsById(@PathVariable long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLocation(@PathVariable long id, @RequestBody Location updatedLocation) {
        Optional<Location> toUpdate = service.update(id, updatedLocation);
        if (toUpdate.isPresent()) {
            return ResponseEntity.ok("Location updated succesfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/instructorToLocation")
    public ResponseEntity<String> setInstructorToLocationMethod
            (@RequestParam("instructorId") long instructorId, @RequestParam("locationId") long locationId) {

        if (instructorRepository.existsById(instructorId) && getLocationsById(locationId).isPresent()) {
            service.addInstructorToLocation(instructorId, locationId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Instructor set to Location");
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @PatchMapping("/outInstructorOfLocation")
    public ResponseEntity<String> InstructorOutOfLocationMethod
            (@RequestParam("instructorId") long instructorId, @RequestParam("locationId") long locationId) {

        if (instructorRepository.existsById(instructorId) && getLocationsById(locationId).isPresent()) {
            service.deleteInstructorFromLocation(instructorId, locationId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Instructor took out of Location");
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

}
