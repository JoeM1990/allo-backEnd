package com.creasmit.allolecolebackend.repository;

import com.creasmit.allolecolebackend.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepo extends JpaRepository<Status, Long> {

    @Query("FROM Status s WHERE s.description=:desc")
    public Status findByDescription(@Param("desc") String desc);
}
