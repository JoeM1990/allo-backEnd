package com.creasmit.allolecolebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.creasmit.allolecolebackend.model.UserProfil;
import com.creasmit.allolecolebackend.repository.UserProfilRepo;
import com.creasmit.allolecolebackend.utils.ValueException;

@Service
public class ProfilServiceImpl implements ProfilService {

    @Autowired
    private UserProfilRepo userProfilRepo;

    @Autowired
    private UserProfil userProfill;

    @Override
    public UserProfil login(String name, String password) throws Exception {

        UserProfil userProfil = this.userProfilRepo.findByName(name);
        if (userProfil == null)
            throw new ValueException(String.format("le profil portant le username %s n'existe", name));

        boolean isValid = new BCryptPasswordEncoder().matches(password, userProfil.getPassword());
        if (!isValid)
            throw new ValueException("le mot de passe est incorrect");

        userProfil.setPassword(null);
        return userProfil;
    }

    @Override
    public UserProfil create(UserProfil userProfil) throws Exception {

        if (userProfil == null)
            throw new ValueException("Impossible de créer un utilisateur avec comme valeur null");

        if (userProfil.getName() == null)
            throw new ValueException("Veuillez renseigner le username");

        UserProfil userChecked = this.userProfilRepo.findByName(userProfil.getName());
        if (userChecked != null)
            throw new ValueException(String.format("le username %s est déjà utilisé", userProfil.getName()));

        if (userProfil.getPassword() == null)
            throw new ValueException("Veuillez renseigner le mot de passe");

        if (userProfil.getPassword().length() < 8)
            throw new ValueException("le mot de passe doit contenir 8 caractères");

        String pwdEncrypted = new BCryptPasswordEncoder().encode(userProfil.getPassword());
        userProfil.setPassword(pwdEncrypted);

        return this.userProfilRepo.save(userProfil);
    }

}
