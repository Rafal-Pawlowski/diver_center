package com.diver.center.diver_center.service;

import com.diver.center.diver_center.exception_handler.InstructorServiceException.DeleteEntityException;
import com.diver.center.diver_center.exception_handler.InstructorServiceException.GetEntityException;
import com.diver.center.diver_center.exception_handler.InstructorServiceException.NoExistingEntityException;
import com.diver.center.diver_center.exception_handler.InstructorServiceException.SaveEntityException;
import com.diver.center.diver_center.model.Instructor;
import com.diver.center.diver_center.model.Licence;
import com.diver.center.diver_center.model.Trainee;
import com.diver.center.diver_center.repository.InstructorRepository;
import com.diver.center.diver_center.repository.TraineeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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
    @DisplayName("should Save instructor")
    void shouldSaveInstructor() {
        Instructor instructor = new Instructor("Janek", null, 16, List.of("Boat Instructor"));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = this.instructorService.save(instructor);

        Assertions.assertEquals(savedInstructor, instructor);
    }

    @Test
    @DisplayName("should throw SaveInstructorException when save instructor")
    void save_WhenDataAccessException_ShouldThrowSaveInstructorException() {

        Instructor instructor = new Instructor();
        when(instructorRepository.save(any(Instructor.class))).thenThrow(SaveEntityException.class);
        assertThrows(SaveEntityException.class, () -> instructorService.save(instructor));
    }


    @Test
    @DisplayName("should return all Instructors when instructors exists")
    void shouldReturnAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        instructors.add(new Instructor("Instructor Test 1", null, 20, null));
        instructors.add(new Instructor("Janek Testowy", null, 25, null));

        when(instructorRepository.findAll()).thenReturn(instructors);

        Iterable<Instructor> result = instructorService.getAllInstructors();

        assertEquals(instructors, result);
    }

    @Test
    @DisplayName("should throw GetInstructorException when getting All instructors")
    void shouldThrowGetInstructorExceptionWhileGettingAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        when(instructorRepository.findAll()).thenThrow(GetEntityException.class);

        assertThrows(GetEntityException.class, () -> instructorService.getAllInstructors());
    }


    @Test
    @DisplayName("should return instructor by Id when instructor exists")
    void shouldReturnInstructorById() {
        long instructorId = 1;
        Instructor instructor = new Instructor("Master Instructor", null, 14, null);

        Optional<Instructor> optionalInstructor = Optional.of(instructor);

        when(instructorRepository.findById(instructorId)).thenReturn(optionalInstructor);

        Optional<Instructor> result = instructorService.getInstructorById(instructorId);
        assertEquals(optionalInstructor, result);
    }

    @Test
    @DisplayName("should throw GetInstructorException when getting instructor By Id")
    void shouldThrowGetInstructorExceptionWhenGettingInstructorById() {
        long instructorId = 1;

        when(instructorRepository.findById(anyLong())).thenThrow(GetEntityException.class);

        assertThrows(GetEntityException.class, () -> instructorService.getInstructorById(instructorId));

        verify(instructorRepository, times(1)).findById(instructorId);
    }

    @Test
    @DisplayName("should remove instructor when instructor provided")
    void shouldRemoveInstructorById() {
        long instructorId = 1;
        Instructor instructor = new Instructor("InstructorToDelete", null, 20, null);
        Optional<Instructor> optionalInstructor = Optional.of(instructor);

        when(instructorRepository.findById(instructorId)).thenReturn(optionalInstructor);


        instructorService.removeById(instructorId);
        verify(instructorRepository, times(1)).deleteById(instructorId);
    }

    @Test
    @DisplayName("should throw DeleteInstructorException when removing instructor by id")
    void shouldThrowDeleteInstructorExceptionWhenRemoving() {
        long instructorId = 1;

        doThrow(DeleteEntityException.class).when(instructorRepository).deleteById(instructorId);

        assertThrows(DeleteEntityException.class, () -> instructorService.removeById(instructorId));
    }


    @Test
    @DisplayName("should update instructors name when instructor provided")
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
    @DisplayName("should throw NoExistingInstructorException when updating name by id when there is no existing instructor on this id")
    void shouldThrowNoExistingInstructorExceptionWhenUpdatingNameWhenInstructorDoesNotExist() {
        long instructorId = 1;

        when(instructorRepository.findById(instructorId)).thenReturn(Optional.empty());

        assertThrows(NoExistingEntityException.class, () -> instructorService.update(instructorId, "Andrzej"));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when updating instructor name and no name provided")
    void shouldThrowIllegalArgumentExceptionWhenUpdatingNameAndNoNameProvided() {
        String instructorName = "AD";
        long instructorId = 1;

        var exception = catchThrowable(() -> instructorService.update(instructorId, instructorName));

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no name to update");

        assertThrows(IllegalArgumentException.class, () -> instructorService.update(instructorId, instructorName));

    }

    @Test
    @DisplayName("should update instructor when instructor exists")
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
    @DisplayName("should return Optional.empty when completeUpdate and no instructor provided on selected ID")
    void shouldReturnOptionalEmptyWhenCompleteUpdateAndNoInstructorProvidedOnSelectedId() {
        Instructor instructorToUpdate = new Instructor("Juan Antonio Morales", null, 38, List.of("Cave", "Wrecks"));
        long instructorId = 1;

        when(instructorRepository.findById(instructorId)).thenReturn(Optional.empty());

        Optional<Instructor> result = instructorService.completeUpdate(instructorId, instructorToUpdate);

        verify(instructorRepository, times(1)).findById(instructorId);

        assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when Complete Update and instructor without name is provided")
    void shouldThrowIllegalArgumentExceptionWhenCompleteUpdateAndInstructorWithoutNameIsProvided() {
        Instructor instructorToUpdate = new Instructor(null, null, 38, List.of("Cave", "Wrecks"));
        long instructorId = 1;

        assertThrows(IllegalArgumentException.class, ()-> instructorService.completeUpdate(instructorId, instructorToUpdate));

    }

    @Test
    @DisplayName("should connect licence to instructor when both are provided")
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
    //TODO FAILURE TEST CONNECTING LICENCE

    @Test
    @DisplayName("should detach licence from instructor when both are provided")
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

    //TODO FAILURE TEST DISCONECT LICENCE
    @Test
    @DisplayName("should detach only trainees from instructor when trainees and instructor are provided")
    void detach_Trainees_From_Instructor_when_all_sources_provided() {
        //given
        long instructorId = 1;
        Instructor instructor = new Instructor("John Doe", null, 30, List.of("Wreck"));

        Trainee trainee1 = new Trainee("Alice Smith", 25, "Female", "English");
        Trainee traineeAfterSave1 = new Trainee("Alice Smith", 25, "Female", "English");
        trainee1.setId(1L);
        traineeAfterSave1.setId(1L);
        Trainee trainee2 = new Trainee("Bob Johnson", 28, "Male", "Spanish");
        Trainee traineeAfterSave2 = new Trainee("Bob Johnson", 28, "Male", "Spanish");
        trainee2.setId(2L);
        traineeAfterSave2.setId(2L);
        List<Trainee> traineesList = new ArrayList<>();
        trainee1.setInstructor(instructor);
        trainee2.setInstructor(instructor);
        traineesList.add(trainee1);
        traineesList.add(trainee2);
        instructor.setTrainees(traineesList);
        Optional<Instructor> optionalInstructor = Optional.of(instructor);

        when(instructorRepository.findById(instructorId)).thenReturn(optionalInstructor);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(traineeAfterSave1, traineeAfterSave2);

        Optional<Instructor> result = instructorService.detachTraineesFromInstructor(instructorId);

        verify(instructorRepository, times(1)).findById(instructorId);
        verify(traineeRepository, times(traineesList.size())).save(any(Trainee.class));

        List<Trainee> resultTraineeList = result.get().getTrainees();


        assertTrue(Optional.of(result).isPresent());
        assertEquals(null, resultTraineeList.get(0).getInstructor());
    }

    // TODO DETACH TRAINEES FAILURE TEST

    @Test
    @DisplayName("should return all instructors by filtered name when instructors are provided ")
    void shouldReturnInstructorsByCustomQuerryInstructorFindByName() {

        List<Instructor> instructors = new ArrayList<>();
        Instructor instructor1 = new Instructor("Marian Gmur", null, 33, List.of("Wreck"));
        Instructor instructor3 = new Instructor("Marian Dora", null, 47, List.of("Cannabis Trainer"));
        instructors.add(instructor1);
        instructors.add(instructor3);

        when(instructorRepository.findAllByNameContaining(anyString())).thenReturn(instructors);

        List<Instructor> instructorsQueryList = instructorService.querryInstructorFindByName("marian");

        verify(instructorRepository).findAllByNameContaining(anyString());

        assertEquals(instructorsQueryList.stream().count(), 2);
        assertTrue(instructorsQueryList.get(0).getName().toLowerCase().contains("marian"));
    }

    // TODO FAILURE TEST QYSTOMQUERY
    @Test
    @DisplayName("Should connect trainee to instructor when trainee and instructor are both exists")
    void shouldAddTraineeToInstructorWhenTraineeAndInstructorExists() {
        long instructorId = 1;
        long traineeId = 1;
        Trainee trainee = new Trainee("Alice Smith", 25, "Female", "English");

        Instructor instructor = new Instructor("Marian Smok", null, 33, List.of("Wreck"));

        List<Trainee> traineeList = List.of(trainee);
        instructor.setTrainees(traineeList);

        Optional<Instructor> optionalInstructor = Optional.of(instructor);
        Optional<Trainee> optionalTrainee = Optional.of(trainee);

        when(instructorRepository.existsById(anyLong())).thenReturn(true);
        when(traineeRepository.existsById(anyLong())).thenReturn(true);
        when(instructorRepository.findById(anyLong())).thenReturn(optionalInstructor);
        when(traineeRepository.findById(anyLong())).thenReturn(optionalTrainee);
        when(traineeRepository.save(trainee)).thenReturn(trainee);

        Instructor result = instructorService.addTraineeToInstructor(instructorId, traineeId);

        verify(instructorRepository, times(1)).existsById(anyLong());
        verify(traineeRepository, times(1)).existsById(anyLong());
        verify(instructorRepository, times(1)).findById(anyLong());
        verify(traineeRepository, times(1)).findById(anyLong());
        verify(traineeRepository, times(1)).save(trainee);

        assertEquals(result.getTrainees().get(0).getInstructor(), trainee.getInstructor());
        assertTrue(result.getTrainees().size() == 1);
        assertTrue(trainee.getInstructor() != null);

    }

    //TODO ADD TRAINEES FAILURE TEST
}