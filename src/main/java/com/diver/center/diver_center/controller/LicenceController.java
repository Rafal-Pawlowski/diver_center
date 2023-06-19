package com.diver.center.diver_center.controller;

import com.diver.center.diver_center.model.Licence;
import com.diver.center.diver_center.service.InstructorService;
import com.diver.center.diver_center.service.LicenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/licences")
public class LicenceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicenceController.class);

    private final LicenceService licenceService;
    private final InstructorService instructorService;


    public LicenceController(LicenceService licenceService, InstructorService instructorService) {
        this.licenceService = licenceService;
        this.instructorService = instructorService;
    }

    @PostMapping
    public ResponseEntity<Licence> postLicence(@RequestBody Licence requestLicence) {
        Licence savedLicence = licenceService.save(requestLicence);
        LOGGER.info("{} savedLicence", savedLicence);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedLicence);
    }

    @GetMapping
    public Iterable<Licence> getAllLicences() {
        return licenceService.getAllLicences();
    }

    @GetMapping("/{id}")
    public Optional<Licence> getLicenceById(@PathVariable long id) {
        return licenceService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicenceById(@PathVariable long id) {
        Optional<Licence> optionalLicencetoDetach = licenceService.getById(id);
        if (optionalLicencetoDetach.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (optionalLicencetoDetach.get().getInstructor() != null) {
            instructorService.detachLicenceFromInstructor(optionalLicencetoDetach.get().getInstructor().getId());
            }
            licenceService.removeById(id);
            return ResponseEntity.noContent().build();
        }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLicence(@PathVariable long id, @RequestBody Licence updatedLicence) {
        Optional<Licence> updated = licenceService.completeUpdate(id, updatedLicence);
        if (updated.isPresent()) {
            return ResponseEntity.ok("Licence updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
