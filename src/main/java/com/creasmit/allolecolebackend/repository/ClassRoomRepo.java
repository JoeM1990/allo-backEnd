package com.creasmit.allolecolebackend.repository;

import com.creasmit.allolecolebackend.model.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepo extends JpaRepository<ClassRoom, Long> {

    @Query("FROM ClassRoom c WHERE c.id=:id")
    public ClassRoom findById(@Param("id") long id);

    @Query("FROM ClassRoom c WHERE c.school.id=:schoolId")
    public List<ClassRoom> findAllBySchool(@Param("schoolId") long schoolId);
}
