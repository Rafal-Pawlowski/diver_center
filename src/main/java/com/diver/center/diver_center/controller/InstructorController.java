package com.diver.center.diver_center.controller;

import com.diver.center.diver_center.model.Instructor;
import com.diver.center.diver_center.service.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstructorController.class);

    private InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
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

    @PatchMapping("/{id}")
    public ResponseEntity<Instructor> putInstructor(@PathVariable long id, @RequestBody String name) {
        Instructor updatedInstructor = instructorService.update(id, name);
        LOGGER.info("{} updated", updatedInstructor);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updatedInstructor);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateInstructor(@PathVariable Long id, @RequestBody Instructor updatedInstructor) {
        Instructor updated = instructorService.completeUpdate(id, updatedInstructor);
        if (updated != null) {
            return ResponseEntity.ok("Instructor updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable long id) {
        if (instructorService.getInstructorById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            instructorService.removeById(id);
            return ResponseEntity.noContent().build();
        }
    }
}


