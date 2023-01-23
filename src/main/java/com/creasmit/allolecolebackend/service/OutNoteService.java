package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.OutNote;

import java.util.List;

public interface OutNoteService {

    public OutNote create(OutNote outNote) throws Exception;

    public List<OutNote> getAllBySchool(long schoolId) throws Exception;

    public boolean delete(long noteId) throws Exception;
}
