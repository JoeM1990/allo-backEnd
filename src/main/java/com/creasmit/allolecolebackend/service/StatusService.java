package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.Status;

import java.util.List;

public interface StatusService {

    public Status create(Status status) throws Exception;

    public List<Status> getAll() throws Exception;
}
