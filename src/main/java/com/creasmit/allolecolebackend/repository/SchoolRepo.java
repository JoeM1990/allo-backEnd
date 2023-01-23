package com.creasmit.allolecolebackend.repository;

import com.creasmit.allolecolebackend.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepo extends JpaRepository<School, Long> {

    @Query("FROM School s WHERE s.id=:schoolId")
    public School getSchoolById(@Param("schoolId") long schoolId);

    @Query("FROM School s WHERE s.userId=:userId")
    public School getSchoolByUserId(@Param("userId") long userId);
}
