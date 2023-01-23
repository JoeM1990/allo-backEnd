package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.ClassRoom;
import com.creasmit.allolecolebackend.model.OptionClass;
import com.creasmit.allolecolebackend.model.School;
import com.creasmit.allolecolebackend.repository.ClassRoomRepo;
import com.creasmit.allolecolebackend.utils.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    @Autowired
    private ClassRoomRepo classRoomRepo;

    @Override
    public ClassRoom create(ClassRoom classRoom) throws Exception {
        classRoom.setSchool(new School(classRoom.getSchool().getId()));
        try{
            for (OptionClass oc : classRoom.getOptionClasses())
            oc.setClassRoom(classRoom);
        }catch(Exception e){
        }

        return this.classRoomRepo.save(classRoom);
    }

    @Override
    public List<ClassRoom> getAllBySchool(long schoolId) throws Exception {
        return this.classRoomRepo.findAllBySchool(schoolId);
    }

    @Override
    public ClassRoom update(ClassRoom classRoom) throws Exception {
        return this.classRoomRepo.save(classRoom);
    }

    @Override
    public boolean delete(long classRoomId) throws Exception {
        ClassRoom classRoom = this.classRoomRepo.findById(classRoomId);
        if (classRoom == null)
            throw new ValueException("La classe séléctionné n'existe pas");

        this.classRoomRepo.delete(classRoom);

        ClassRoom classRoomChecked = this.classRoomRepo.findById(classRoomId);
        if (classRoomChecked == null)
            return true;
        return false;
    }
}
