package com.diver.center.diver_center.repository;

import com.diver.center.diver_center.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Long> {


}
