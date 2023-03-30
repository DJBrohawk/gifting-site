package com.wrapped.service;

import com.wrapped.entity.Person;
import com.wrapped.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;


    public List<Person> listAllPersons() {
        return personRepository.findAll();
    }

    public List<Person> listPersonByUserId(int userId) {
        return personRepository.findPersonsByUserId(userId);
    }

}
