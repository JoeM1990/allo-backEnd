package com.creasmit.allolecolebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pupil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String firstname;
    private String lastname;
    private String tutor;
    private String phoneNumber;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClassRoom classRoom;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OptionClass optionClass;


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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public OptionClass getOptionClass() {
        return optionClass;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOptionClass(OptionClass optionClass) {
        this.optionClass = optionClass;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public OptionClass getOption() {
        return optionClass;
    }

    public void setOption(OptionClass optionClass) {
        this.optionClass = optionClass;
    }
}
