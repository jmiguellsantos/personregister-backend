package com.personregister.personregister.services;

import com.personregister.personregister.dtos.PersonDTO;
import com.personregister.personregister.models.Person;
import com.personregister.personregister.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public Person save(Person personModel) {
        return personRepository.save(personModel);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findByName(String name) {
        return personRepository.findByNameContainsIgnoreCase(name);
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }
}
