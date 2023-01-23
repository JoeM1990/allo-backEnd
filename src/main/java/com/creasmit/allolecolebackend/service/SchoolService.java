package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.dto.SchoolDetailsDto;
import com.creasmit.allolecolebackend.model.School;

import java.util.List;

public interface SchoolService {

    public List<SchoolDetailsDto> getAll() throws Exception;

    public School get(long schoolId) throws Exception;

    public School getByUser(long userId) throws Exception;

    public School create(School school) throws Exception;

    public School update(School school) throws Exception;

    public School activeSchool(School school) throws Exception;

    public School disableSchool(School school) throws Exception;

    public boolean delete(long schoolId) throws Exception;

}
