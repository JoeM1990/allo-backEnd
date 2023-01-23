package com.creasmit.allolecolebackend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OptionClass> optionClasses;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private School school;

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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<OptionClass> getOptionClasses() {
        return optionClasses;
    }

    public void setOptionClasses(Set<OptionClass> optionClasses) {
        this.optionClasses = optionClasses;
    }
}
