package com.creasmit.allolecolebackend.repository;

import com.creasmit.allolecolebackend.model.Pupil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PupilRepo extends JpaRepository<Pupil, Long> {

    @Query("FROM Pupil p WHERE p.id=:id")
    public Pupil findById(@Param("id") long pupilId);

    @Query("FROM Pupil  p WHERE p.classRoom.id=:classId")
    public List<Pupil> findAllByClassRoom(@Param("classId") long classId);

    @Query("FROM Pupil  p WHERE p.classRoom.school.id=:schoolId")
    public List<Pupil> findBySchool(@Param("schoolId") long schoolId);

    @Query("FROM Pupil  p WHERE p.optionClass.id=:optionId")
    public List<Pupil> findAllByOption(@Param("optionId") long optionId);
}
