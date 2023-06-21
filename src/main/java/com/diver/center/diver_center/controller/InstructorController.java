package com.diver.center.diver_center.controller;

import com.diver.center.diver_center.exception_handler.AttachedException;
import com.diver.center.diver_center.model.Instructor;
import com.diver.center.diver_center.service.InstructorService;
import com.diver.center.diver_center.service.LicenceService;
import com.diver.center.diver_center.service.TraineeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/instructors")
public class InstructorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstructorController.class);

    private final InstructorService instructorService;
    private final LicenceService licenceService;
    private final TraineeService traineeService;

    public InstructorController(InstructorService instructorService, LicenceService licenceService, TraineeService traineeService) {
        this.instructorService = instructorService;
        this.licenceService = licenceService;
        this.traineeService = traineeService;
    }

    @PostMapping
    public ResponseEntity<Instructor> postInstructor(@RequestBody Instructor requestInstructor) {
        Instructor savedInstructor = instructorService.save(requestInstructor);
        LOGGER.info("{} saved", savedInstructor);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedInstructor);
    }

    @GetMapping
    public Iterable<Instructor> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/{id}")
    public Optional<Instructor> getInstructorById(@PathVariable Long id) {
        return instructorService.getInstructorById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Instructor> putInstructor(@PathVariable long id, @RequestBody String name) {
        Instructor updatedInstructor = instructorService.update(id, name);
        LOGGER.info("{} updated", updatedInstructor);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updatedInstructor);
    }

    @PatchMapping("/{instructorId}/setLicence/{licenceId}")
    public ResponseEntity<Optional<Instructor>> setLicenceToInstructor(@PathVariable long instructorId, @PathVariable long licenceId) {
        Optional<Instructor> optionalInstructor = instructorService.setLicence(instructorId, licenceId);
        if (optionalInstructor.isPresent()) {
            LOGGER.info("{} updated", optionalInstructor);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(optionalInstructor);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/instructorChoice/{instructorId}/{traineeId}")
    public ResponseEntity<Instructor> addTraineeToInstructorTraineeList(@PathVariable long instructorId, @PathVariable long traineeId) {
        if (instructorService.getInstructorById(instructorId).isPresent() && traineeService.getById(traineeId).isPresent()) {
            Instructor instructor = instructorService.addTraineeToInstructor(instructorId, traineeId);
            LOGGER.info("{} updated", instructor);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(instructor);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateInstructor(@PathVariable Long id, @RequestBody Instructor updatedInstructor) {
        Optional<Instructor> updated = instructorService.completeUpdate(id, updatedInstructor);
        if (updated.isPresent()) {
            return ResponseEntity.ok("Instructor updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable long id) {
        Optional<Instructor> instructorById = instructorService.getInstructorById(id);
        if (instructorById.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (!instructorById.get().getTrainees().isEmpty()) {
          throw new AttachedException("Cannot Delete Instructor that has trainees, please first detach all trainees");

        } else if (instructorById.get().getLicence() != null) {
            licenceService.detachInstructorFromLicence(instructorById.get().getLicence().getId());
        }
        instructorService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/querry/{name}")
    public List<Instructor> querryFindInstructorByContainingName(@PathVariable String name) {
        return instructorService.querryInstructorFindByName(name);
    }
}


