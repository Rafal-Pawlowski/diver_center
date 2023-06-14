package com.diver.center.diver_center.controller;

import com.diver.center.diver_center.model.Location;
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

    public LocationController(LocationService service) {
        this.service = service;
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
