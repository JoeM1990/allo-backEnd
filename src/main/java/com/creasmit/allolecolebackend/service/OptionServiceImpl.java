package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.OptionClass;
import com.creasmit.allolecolebackend.repository.OptionRepo;
import com.creasmit.allolecolebackend.utils.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionRepo optionRepo;

    @Override
    public OptionClass create(OptionClass optionClass) throws Exception {
        return this.optionRepo.save(optionClass);
    }

    @Override
    public List<OptionClass> getAllByClassRoom(long classRoomId) throws Exception {
        return this.optionRepo.findAllByClassRoom(classRoomId);
    }

    @Override
    public OptionClass update(OptionClass optionClass) throws Exception {
        return this.optionRepo.save(optionClass);
    }

    @Override
    public boolean delete(long optionId) throws Exception {
        OptionClass optionClass = this.optionRepo.findById(optionId);

        if (optionClass == null)
            throw new ValueException("L'optionClass séléctionné n'existe pas");
        this.optionRepo.delete(optionClass);

        OptionClass optionClassChecked = this.optionRepo.findById(optionId);
        if (optionClassChecked == null)
            return true;

        return false;
    }
}
