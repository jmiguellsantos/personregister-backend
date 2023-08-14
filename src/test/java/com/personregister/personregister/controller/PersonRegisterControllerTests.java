package com.personregister.personregister.controller;


import com.personregister.personregister.controllers.PersonController;
import com.personregister.personregister.dtos.PersonDTO;
import com.personregister.personregister.models.Person;
import com.personregister.personregister.services.PersonService;
import org.hibernate.type.LocalDateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class PersonRegisterControllerTests {

	@Mock
	private PersonService personService;

	@InjectMocks
	private PersonController personController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void savePersonTest() {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setName("Clark Kent");
		personDTO.setBirthDate(LocalDate.from(LocalDateType.FORMATTER.parse("1992-05-03")));
		personDTO.setEmail("clark@dc.com.br");
		personDTO.setPhoneNumber("4002-8922");

		Person savedPerson = new Person();
		savedPerson.setId(1L);

		when(personService.save(any(Person.class))).thenReturn(savedPerson);
		ResponseEntity<Object> responseEntity = personController.savePerson(personDTO);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	public void getAllPersonsTest() {
		Person person = new Person();
		person.setId(1L);

		when(personService.findAll()).thenReturn(Collections.singletonList(person));

		ResponseEntity<List<Person>> responseEntity = personController.getAllPersons();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(1, responseEntity.getBody().size());
	}


	@Test
	public void getNameSearched_PersonFoundTest() {
		String name = "Batman";
		Person person = new Person();
		person.setId(1L);
		person.setName(name);

		when(personService.findByName(name)).thenReturn(Optional.of(person));

		ResponseEntity<?> responseEntity = personController.getNameSearched(name);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody() instanceof Person);
		Person returnedPerson = (Person) responseEntity.getBody();
		assertEquals(person.getId(), returnedPerson.getId());
	}

	@Test
	public void getNameSearched_PersonNotFoundTest() {
		String name = "name-nonexistent";

		when(personService.findByName(name)).thenReturn(Optional.empty());
		ResponseEntity<?> responseEntity = personController.getNameSearched(name);
		String responseBody = (String) responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody() instanceof String);
		assertEquals("Person with name '" + name + "' not found", responseBody);
	}


	@Test
	public void testDeletePerson_PersonFound() {
		Long id = 1L;
		Person person = new Person();
		person.setId(id);

		when(personService.findById(id)).thenReturn(Optional.of(person));
		ResponseEntity<Object> responseEntity = personController.deletePerson(id);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody() instanceof String);
		assertEquals("Person deleted successfully", responseEntity.getBody());
		verify(personService, times(1)).delete(person);
	}

	@Test
	public void testDeletePerson_PersonNotFound() {
		Long id = 1L;

		when(personService.findById(id)).thenReturn(Optional.empty());
		ResponseEntity<Object> responseEntity = personController.deletePerson(id);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody() instanceof String);
		assertEquals("Person not found", responseEntity.getBody());
		verify(personService, never()).delete(any());
	}

	@Test
	public void testGetPersonDetails_PersonFound() {
		Long id = 1L;
		Person person = new Person();
		person.setId(id);

		when(personService.findByIdWithContacts(id)).thenReturn(Optional.of(person));
		ResponseEntity<?> responseEntity = personController.getPersonDetails(id);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody() instanceof Person);
		Person returnedPerson = (Person) responseEntity.getBody();
		assertEquals(person.getId(), returnedPerson.getId());
	}

	@Test
	public void testGetPersonDetails_PersonNotFound() {
		Long id = 1L;

		when(personService.findByIdWithContacts(id)).thenReturn(Optional.empty());
		ResponseEntity<?> responseEntity = personController.getPersonDetails(id);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody() instanceof String);
		assertEquals("Person not found", responseEntity.getBody());
	}
}
