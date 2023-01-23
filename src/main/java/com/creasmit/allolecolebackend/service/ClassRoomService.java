package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.ClassRoom;

import java.util.List;

public interface ClassRoomService {

    public ClassRoom create(ClassRoom classRoom) throws Exception;

    public List<ClassRoom> getAllBySchool(long schoolId) throws Exception;

    public ClassRoom update(ClassRoom classRoom) throws Exception;

    public boolean delete(long classRoomId) throws Exception;
}
