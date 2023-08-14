package com.personregister.personregister.service;

import com.personregister.personregister.models.Person;
import com.personregister.personregister.repositories.PersonRepository;
import com.personregister.personregister.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceTests {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void savePersonServiceTest() {
        Person person = new Person();
        person.setId(1L);

        when(personRepository.save(any(Person.class))).thenReturn(person);
        Person savedPerson = personService.save(person);

        assertNotNull(savedPerson);
        assertEquals(person.getId(), savedPerson.getId());
    }

    @Test
    public void findAllPersonsServiceTest() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        persons.add(new Person());

        when(personRepository.findAll()).thenReturn(persons);
        List<Person> foundPersons = personService.findAll();

        assertNotNull(foundPersons);
        assertEquals(2, foundPersons.size());
    }

    @Test
    public void findPersonByNameServiceTest() {
        String name = "Batman";
        Person person = new Person();
        person.setId(1L);
        person.setName(name);

        when(personRepository.findByNameContainsIgnoreCase(name)).thenReturn(Optional.of(person));
        Optional<Person> foundPerson = personService.findByName(name);

        assertTrue(foundPerson.isPresent());
        assertEquals(name, foundPerson.get().getName());
    }
}
