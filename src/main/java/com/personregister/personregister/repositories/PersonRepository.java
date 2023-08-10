package com.personregister.personregister.repositories;

import com.personregister.personregister.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByNameContainsIgnoreCase(String name);
    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.contacts WHERE p.id = :id")
    Optional<Person> findByIdWithContacts(Long id);

}
