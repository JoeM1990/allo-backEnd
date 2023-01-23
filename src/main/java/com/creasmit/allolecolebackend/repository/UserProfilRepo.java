package com.creasmit.allolecolebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.creasmit.allolecolebackend.model.UserProfil;

@Repository
public interface UserProfilRepo extends JpaRepository<UserProfil, Long> {

    @Query("FROM UserProfil u WHERE u.name=:name")
    UserProfil findByName(@Param("username") String name);

}
