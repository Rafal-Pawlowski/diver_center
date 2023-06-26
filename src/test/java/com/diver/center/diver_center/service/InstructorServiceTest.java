package com.diver.center.diver_center.service;

import com.diver.center.diver_center.model.Instructor;
import com.diver.center.diver_center.repository.InstructorRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class InstructorServiceTest {

    @InjectMocks
    private InstructorService instructorService;

    @Mock
    private InstructorRepository instructorRepository;

    @BeforeEach
    void beforeTest() {
        MockitoAnnotations.openMocks(this);
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
    void update() {
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
    void completeUpdate() {
    }

    @Test
    void setLicence() {
    }

    @Test
    void detachLicenceFromInstructor() {
    }

    @Test
    void detachTraineesFromInstructor() {
    }

    @Test
    void querryInstructorFindByName() {
    }

    @Test
    void addTraineeToInstructor() {
    }
}