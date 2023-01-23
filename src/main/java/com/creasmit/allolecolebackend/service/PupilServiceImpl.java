package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.Pupil;
import com.creasmit.allolecolebackend.repository.PupilRepo;
import com.creasmit.allolecolebackend.utils.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PupilServiceImpl implements PupilService {

    @Autowired
    private PupilRepo pupilRepo;

    @Override
    public Pupil create(Pupil pupil) throws Exception {
        return this.pupilRepo.save(pupil);
    }

    @Override
    public List<Pupil> getAllBySchool(long schoolId) throws Exception {
        return this.pupilRepo.findBySchool(schoolId);
    }

    @Override
    public List<Pupil> getAllByClassRoom(long classRoomId) throws Exception {
        return this.pupilRepo.findAllByClassRoom(classRoomId);
    }

    @Override
    public List<Pupil> getAllByOption(long optionId) throws Exception {
        return this.getAllByOption(optionId);
    }

    @Override
    public Pupil update(Pupil pupil) throws Exception {
        return this.pupilRepo.save(pupil);
    }

    @Override
    public boolean delete(long pupilId) throws Exception {
        Pupil pupil = this.pupilRepo.findById(pupilId);
        if (pupil == null)
            throw new ValueException("L'élève n'existe pas");

        this.pupilRepo.delete(pupil);

        Pupil pupilChecked = this.pupilRepo.findById(pupilId);
        if (pupilChecked == null)
            return true;
        return false;
    }
}
