package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.OptionClass;

import java.util.List;

public interface OptionService {

    public OptionClass create(OptionClass optionClass) throws Exception;

    public List<OptionClass> getAllByClassRoom(long classRoomId) throws Exception;

    public OptionClass update(OptionClass optionClass) throws Exception;

    public boolean delete(long optionId) throws Exception;
}
