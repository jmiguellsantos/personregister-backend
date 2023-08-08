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
}
