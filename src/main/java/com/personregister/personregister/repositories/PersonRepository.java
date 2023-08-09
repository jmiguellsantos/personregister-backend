package com.personregister.personregister.repositories;

import com.personregister.personregister.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByNameContainsIgnoreCase(String name);
}
