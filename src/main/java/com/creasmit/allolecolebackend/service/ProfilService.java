package com.creasmit.allolecolebackend.service;

import org.springframework.http.ResponseEntity;

import com.creasmit.allolecolebackend.model.UserProfil;

public interface ProfilService {

    // public List<SchoolDetailsDto> getAll() throws Exception;

    // public UserProfil get(long schoolId) throws Exception;

    // public UserProfil getByUser(long userId) throws Exception;

    public UserProfil create(UserProfil userProfil) throws Exception;

    public UserProfil login(String name, String password) throws Exception;

    // public UserProfil update(UserProfil userProfil) throws Exception;

    // public UserProfil activeSchool(UserProfil userProfil) throws Exception;

    // public UserProfil disableSchool(UserProfil userProfil) throws Exception;

    // public boolean delete(long schoolId) throws Exception;

}
