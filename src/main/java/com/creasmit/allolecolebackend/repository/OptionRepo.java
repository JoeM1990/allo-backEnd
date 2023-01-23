package com.creasmit.allolecolebackend.repository;

import com.creasmit.allolecolebackend.model.OptionClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepo extends JpaRepository<OptionClass, Long> {

    @Query("FROM OptionClass o WHERE o.id=:id")
    public OptionClass findById(@Param("id") long id);

    @Query("FROM OptionClass o WHERE o.classRoom.id=:classId")
    public List<OptionClass> findAllByClassRoom(@Param("classId") long classId);
}
