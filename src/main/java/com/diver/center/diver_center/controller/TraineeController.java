package com.diver.center.diver_center.controller;

import com.diver.center.diver_center.model.Trainee;
import com.diver.center.diver_center.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/trainees")
public class TraineeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeController.class);

    private final TraineeService service;


    public TraineeController(TraineeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Trainee> saveTrainee(@RequestBody Trainee newTrainee) {
        Trainee savedTrainee = service.save(newTrainee);
        LOGGER.info("{}", savedTrainee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedTrainee);
    }

    @GetMapping
    public Iterable<Trainee> getAllTrainees() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Trainee> getTraineeById(@PathVariable long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTrainee(@PathVariable long id, @RequestBody Trainee updatedTrainee) {
        Optional<Trainee> updated = service.update(id, updatedTrainee);
        if (updated.isPresent()) {
            return ResponseEntity.ok("Trainee updated Successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/detachInstructor/{traineeId}")
    public ResponseEntity<String> detachInstructor(@PathVariable long traineeId) {
        Optional<Trainee> optionalTrainee = service.detachInstructor(traineeId);
        if (optionalTrainee.isPresent()) {
            return ResponseEntity.ok("Instructor detached from Trainee");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraineeById(@PathVariable long id) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}
