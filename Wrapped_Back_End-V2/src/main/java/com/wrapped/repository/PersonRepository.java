package com.wrapped.repository;

import com.wrapped.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("Select P from Person P where P.userAttached.id=?1")
    List<Person> findPersonsByUserId(int userAttached);
}
