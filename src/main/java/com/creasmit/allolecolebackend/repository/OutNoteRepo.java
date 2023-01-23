package com.creasmit.allolecolebackend.repository;

import com.creasmit.allolecolebackend.model.OutNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutNoteRepo extends JpaRepository<OutNote, Long> {

    @Query("FROM OutNote o WHERE o.id=:id")
    public OutNote findById(@Param("id") long id);

    @Query("FROM OutNote n WHERE n.school.id=:schoolId")
    public List<OutNote> findAllBySchool(@Param("schoolId") long schoolId);

}
