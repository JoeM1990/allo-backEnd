package com.creasmit.allolecolebackend.model;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfil {
    private long id;
    private String name;
    private String lastname;
    private String typeAuthentificationTag;
    private String roleTag;
    private String phoneNumber;
    private String password;

    public UserProfil() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTypeAuthentificationTag() {
        return typeAuthentificationTag;
    }

    public void setTypeAuthentificationTag(String typeAuthentificationTag) {
        this.typeAuthentificationTag = typeAuthentificationTag;
    }

    public String getRoleTag() {
        return roleTag;
    }

    public void setRoleTag(String roleTag) {
        this.roleTag = roleTag;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
