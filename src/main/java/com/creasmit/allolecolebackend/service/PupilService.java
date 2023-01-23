package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.Pupil;

import java.util.List;

public interface PupilService {

    public Pupil create(Pupil pupil) throws Exception;

    public List<Pupil> getAllBySchool(long schoolId) throws Exception;

    public List<Pupil> getAllByClassRoom(long classRoomId) throws Exception;

    public List<Pupil> getAllByOption(long optionId) throws Exception;

    public Pupil update(Pupil pupil) throws Exception;

    public boolean delete(long pupilId) throws Exception;
}
