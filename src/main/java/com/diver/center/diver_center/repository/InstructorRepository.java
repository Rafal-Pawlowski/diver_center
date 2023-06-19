package com.diver.center.diver_center.repository;

import com.diver.center.diver_center.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Long> {

    List<Instructor> findAllByNameContaining(String contains);

}
