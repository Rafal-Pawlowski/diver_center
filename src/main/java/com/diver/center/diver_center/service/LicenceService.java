package com.diver.center.diver_center.service;

import com.diver.center.diver_center.model.Licence;
import com.diver.center.diver_center.repository.LicenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LicenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicenceService.class);

    private final LicenceRepository repository;

    public LicenceService(LicenceRepository repository) {
        this.repository = repository;
    }

    public Licence save(Licence licence) {
        return repository.save(licence);
    }

    public Iterable<Licence> getAllLicences() {
        return repository.findAll();
    }

    public Optional<Licence> getById(long id) {
        return repository.findById(id);
    }


    public void removeById(long id) {
        repository.deleteById(id);
    }

    public Optional<Licence> completeUpdate(long id, Licence updatedLicence) {
        Optional<Licence> optionalLicence = repository.findById(id);
        if (optionalLicence.isPresent()) {
            Licence existingLicence = optionalLicence.get();
            existingLicence.setLicenceNumber(updatedLicence.getLicenceNumber());
            existingLicence.setDateOfRelease(updatedLicence.getDateOfRelease());
            existingLicence.setFederation(updatedLicence.getFederation());
            existingLicence.setLevel(updatedLicence.getLevel());
            return Optional.of(repository.save(existingLicence));
        }
        return Optional.empty();
    }
//    public Optional<Licence> detachInstructorFromLicence(long licenceId) {
//        Optional <Licence> optionalLicence = repository.findById(licenceId);
//        if(repository.existsById(licenceId)){
//            Licence licenceToDetach = optionalLicence.get();
//            licenceToDetach.setInstructor(null);
//            return Optional.of(repository.save(licenceToDetach));
//        }
//        return Optional.empty();
//    }
}
