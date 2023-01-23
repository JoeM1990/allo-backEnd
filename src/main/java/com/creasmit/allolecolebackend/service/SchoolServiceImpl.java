package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.dto.SchoolDetailsDto;
import com.creasmit.allolecolebackend.model.OutNote;
import com.creasmit.allolecolebackend.model.School;
import com.creasmit.allolecolebackend.model.Status;
import com.creasmit.allolecolebackend.repository.OutNoteRepo;
import com.creasmit.allolecolebackend.repository.PupilRepo;
import com.creasmit.allolecolebackend.repository.SchoolRepo;
import com.creasmit.allolecolebackend.repository.StatusRepo;
import com.creasmit.allolecolebackend.utils.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepo schoolRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private PupilRepo pupilRepo;

    @Autowired
    private OutNoteRepo outNoteRepo;


    @Override
    public List<SchoolDetailsDto> getAll() throws Exception {
        List<School> schools = this.schoolRepo.findAll();
        List<SchoolDetailsDto> schoolDetailsDtos = new ArrayList<>();
        for (School school : schools) {
            long pupilCount = this.pupilRepo.findBySchool(school.getId()).size();
            SchoolDetailsDto schoolDetailsDto = new SchoolDetailsDto();
            schoolDetailsDto.setSchool(school);
            schoolDetailsDto.setPupilCount(pupilCount);

            List<OutNote> outNotes = this.outNoteRepo.findAllBySchool(school.getId());
            long sentMsg = 0;
            for (OutNote outNote : outNotes)
                sentMsg += outNote.getCount();
            schoolDetailsDto.setSentCountMsg(sentMsg);
            schoolDetailsDtos.add(schoolDetailsDto);
        }
        return schoolDetailsDtos;
    }

    @Override
    public School get(long schoolId) throws Exception {


        return this.schoolRepo.getSchoolById(schoolId);
    }

    @Override
    public School getByUser(long userId) throws Exception {
        return this.schoolRepo.getSchoolByUserId(userId);
    }

    @Override
    public School create(School school) throws Exception {
        Status status = this.statusRepo.findByDescription("Désactivé");
        school.setStatus(status);
        return this.schoolRepo.save(school);
    }

    @Override
    public School update(School school) throws Exception {
        return this.schoolRepo.save(school);
    }

    @Override
    public School activeSchool(School school) throws Exception {
        School schoolFind = this.schoolRepo.getSchoolById(school.getId());
        Status status = this.statusRepo.findByDescription("Activé");
        schoolFind.setStatus(status);
        return this.schoolRepo.save(schoolFind);
    }

    @Override
    public School disableSchool(School school) throws Exception {
        School schoolFind = this.schoolRepo.getSchoolById(school.getId());
        Status status = this.statusRepo.findByDescription("Désactivé");
        schoolFind.setStatus(status);
        return this.schoolRepo.save(schoolFind);
    }

    @Override
    public boolean delete(long schoolId) throws Exception {

        School school = this.schoolRepo.getSchoolById(schoolId);
        if (school == null)
            throw new ValueException("Aucune école n'est trouvé avec l'id " + schoolId);

        this.schoolRepo.delete(school);
        School schoolCheckOut = this.schoolRepo.getSchoolById(schoolId);
        if (schoolCheckOut != null)
            return true;
        return false;
    }
}
