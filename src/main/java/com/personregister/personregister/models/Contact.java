package com.personregister.personregister.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
