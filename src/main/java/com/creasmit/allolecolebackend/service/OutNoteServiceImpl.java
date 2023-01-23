package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.OutNote;
import com.creasmit.allolecolebackend.repository.OutNoteRepo;
import com.creasmit.allolecolebackend.utils.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutNoteServiceImpl implements OutNoteService {

    @Autowired
    private OutNoteRepo outNoteRepo;

    @Override
    public OutNote create(OutNote outNote) throws Exception {
        return this.outNoteRepo.save(outNote);
    }

    @Override
    public List<OutNote> getAllBySchool(long schoolId) throws Exception {
        return this.outNoteRepo.findAllBySchool(schoolId);
    }

    @Override
    public boolean delete(long noteId) throws Exception {
        OutNote outNote = this.outNoteRepo.findById(noteId);
        if (outNote == null)
            throw new ValueException("La note n'existe pas");

        this.outNoteRepo.delete(outNote);

        OutNote outNoteChecked = this.outNoteRepo.findById(noteId);
        if (outNoteChecked == null)
            return true;
        return false;
    }
}
