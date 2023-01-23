package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.Status;
import com.creasmit.allolecolebackend.repository.StatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepo statusRepo;

    @Override
    public Status create(Status status) throws Exception {
        return this.statusRepo.save(status);
    }

    @Override
    public List<Status> getAll() throws Exception {
        return this.statusRepo.findAll();
    }

    @EventListener(ApplicationReadyEvent.class)
    private void init() {
        Status statusActive = this.statusRepo.findByDescription("Activé");
        if (statusActive == null)
            this.statusRepo.save(new Status("Activé"));

        Status statusDisable = this.statusRepo.findByDescription("Désactivé");
        if (statusDisable == null)
            this.statusRepo.save(new Status("Désactivé"));
    }
}
