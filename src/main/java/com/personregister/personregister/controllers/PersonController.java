package com.personregister.personregister.controllers;

import com.personregister.personregister.dtos.PersonDTO;
import com.personregister.personregister.models.Person;
import com.personregister.personregister.services.PersonService;
import org.hibernate.type.LocalDateType;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/person-register")
public class PersonController {
    final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Object> savePerson(@RequestBody @Valid PersonDTO personDTO) {
        Person personModel = new Person();
        BeanUtils.copyProperties(personDTO, personModel);
        personModel.setBirthDate(LocalDate.parse(LocalDateType.FORMATTER.format(personDTO.getBirthDate())));
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.save(personModel));
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findAll());
    }

    @GetMapping("/name={name}")
    public ResponseEntity<?> getNameSearched(@PathVariable(value = "name") String name) {
        Optional<Person> personOptional = personService.findByName(name);
        if (!personOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with name '" + name + "' not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personOptional.get());
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<Object> deletePerson(@PathVariable(value = "id") Long id) {
        Optional<Person> personOptional = personService.findById(id);
        if (!personOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        personService.delete(personOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Person deleted successfully");
    }
}
