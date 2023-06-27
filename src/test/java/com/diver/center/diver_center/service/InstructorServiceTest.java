package com.diver.center.diver_center.service;

import com.diver.center.diver_center.model.Instructor;
import com.diver.center.diver_center.model.Licence;
import com.diver.center.diver_center.model.Trainee;
import com.diver.center.diver_center.repository.InstructorRepository;
import com.diver.center.diver_center.repository.TraineeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class InstructorServiceTest {

    //    @InjectMocks
    private InstructorService instructorService;

    @Mock
    private LicenceService licenceService;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private TraineeRepository traineeRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instructorService = new InstructorService(instructorRepository, licenceService, traineeRepository);
    }

    @Test
    void shouldSaveInstructor() {
        Instructor instructor = new Instructor("Janek", null, 16, List.of("Boat Instructor"));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = this.instructorService.save(instructor);

        Assertions.assertEquals(savedInstructor, instructor);
    }

    @Test
    void shouldReturnAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        instructors.add(new Instructor("Instructor Test 1", null, 20, null));
        instructors.add(new Instructor("Janek Testowy", null, 25, null));

        when(instructorRepository.findAll()).thenReturn(instructors);

        Iterable<Instructor> result = instructorService.getAllInstructors();

        assertEquals(instructors, result);
    }

    @Test
    void shouldReturnInstructorById() {
        long instructorId = 1;
        Instructor instructor = new Instructor("Master Instructor", null, 14, null);

        Optional<Instructor> optionalInstructor = Optional.of(instructor);

        when(instructorRepository.findById(instructorId)).thenReturn(optionalInstructor);

        Optional<Instructor> result = instructorService.getInstructorById(instructorId);
        assertEquals(optionalInstructor, result);
    }

    @Test
    void shouldRemoveInstructorById() {
        long instructorId = 1;
        Instructor instructor = new Instructor("InstructorToDelete", null, 20, null);
        Optional<Instructor> optionalInstructor = Optional.of(instructor);

        when(instructorRepository.findById(instructorId)).thenReturn(optionalInstructor);


        instructorService.removeById(instructorId);
        verify(instructorRepository, times(1)).deleteById(instructorId);
    }


    @Test
    void shouldUpdateInstructorName() {
        long instructorId = 15;
        Instructor instructor = new Instructor("Ronaldo", null, 20, null);
        Optional<Instructor> optionalInstructor = Optional.of(instructor);

        when(instructorRepository.findById(instructorId)).thenReturn(optionalInstructor);
        instructorService.update(instructorId, "Andrzej Zmienny");
        verify(instructorRepository, times(1)).save(instructor);

        Optional<Instructor> updatedOptionalInstructor = instructorRepository.findById(instructorId);
        String updatedName = updatedOptionalInstructor.map(Instructor::getName).orElse(null);

        Assertions.assertEquals("Andrzej Zmienny", updatedName);
    }

    @Test
    void shouldCompleteUpdateInstructor() {
        long instructorId = 3;
        Instructor existingInstructor = new Instructor("Juan Antonio Morales", null, 38, List.of("Cave", "Wrecks"));
        Instructor updatedInstructor = new Instructor("Updated Instructor", null, 25, List.of("Wrecks", "Fish", "Sharks"));

        Optional<Instructor> optionalInstructor = Optional.of(existingInstructor);

        when(instructorRepository.findById(instructorId)).thenReturn(optionalInstructor);
        when(instructorRepository.save(existingInstructor)).thenReturn(updatedInstructor);

        Optional<Instructor> result = instructorService.completeUpdate(instructorId, updatedInstructor);

        verify(instructorRepository, times(1)).findById(instructorId);
        verify(instructorRepository, times(1)).save(existingInstructor);

        assertTrue(result.isPresent());
        assertEquals(updatedInstructor, result.get());
        assertEquals(updatedInstructor.getId(), result.get().getId());
    }

    @Test
    void shouldSetInstructorLicenceWhenObjectsProvided() {

        long instructorId = 1;
        long licenceId = 2;
        Instructor instructorBeforeUpdate = new Instructor("Marjusz", null, 29, List.of("Rebreather instructorBeforeUpdate", "Wrecks"));
        Licence licence = new Licence(29290, LocalDate.of(2020, 04, 20), "PADI", "Wrecks instructorBeforeUpdate", null);
        Instructor instructorAfterUpdate = new Instructor("Marjusz", licence, 29, List.of("Rebreather instructorBeforeUpdate", "Wrecks"));

        Optional<Instructor> optionalInstructor = Optional.of(instructorBeforeUpdate);
        Optional<Licence> optionalLicence = Optional.of(licence);

        when(instructorRepository.findById(instructorId)).thenReturn(optionalInstructor);
        when(licenceService.getById(licenceId)).thenReturn(optionalLicence);
        when(instructorRepository.save(instructorBeforeUpdate)).thenReturn(instructorAfterUpdate);

        Optional<Instructor> result = instructorService.setLicence(instructorId, licenceId);

        verify(instructorRepository, times(1)).findById(instructorId);
        verify(licenceService, times(1)).getById(licenceId);
        verify(instructorRepository, times(1)).save(instructorBeforeUpdate);


        assertEquals(result.get().getLicence(), licence);

    }

    @Test
    void shouldDetachLicenceFromInstructorWhenObjectsProvided() {

        long instructorId = 1;
        Instructor instructorBeforeSave = new Instructor("Marian", null, 40, List.of("Boat Instructor"));
        Licence licence = new Licence(012324, LocalDate.of(2019, 1, 1), "CMAS", "Advanced Diver", instructorBeforeSave);
        instructorBeforeSave.setLicence(licence);

        Instructor instructorAfterSave = new Instructor("Marian", null, 40, List.of("Boat Instructor"));

        Optional<Instructor> optionalInstructorBeforeSave = Optional.of(instructorBeforeSave);

        when(instructorRepository.findById(instructorId)).thenReturn(optionalInstructorBeforeSave);
        when(instructorRepository.existsById(instructorId)).thenReturn(true);
        when(instructorRepository.save(instructorBeforeSave)).thenReturn(instructorAfterSave);

        Optional<Instructor> result = instructorService.detachLicenceFromInstructor(instructorId);

        verify(instructorRepository, times(1)).findById(instructorId);
        verify(instructorRepository, times(1)).existsById(instructorId);
        verify(instructorRepository, times(1)).save(instructorBeforeSave);

        assertNull(result.get().getLicence());
    }

    @Test
    void detachTraineesFromInstructor() {
        //find
        //traineeRepository.save

        Trainee trainee = new Trainee("Trainee1", 20, "Female", "English");
        Trainee trainee2 = new Trainee("Trainee2", 30, "Male", "Polish");
        List<Trainee> traineesList = List.of(trainee, trainee2);
        Instructor instructor = new Instructor("Jurek", null, 30, null);
        instructor.setTrainees(traineesList);





    }

    @Test
    void querryInstructorFindByName() {
    }

    @Test
    void addTraineeToInstructor() {
    }
}