package com.wrapped.controller;

import com.wrapped.entity.Item;
import com.wrapped.entity.Person;
import com.wrapped.entity.User;
import com.wrapped.repository.ItemRepository;
import com.wrapped.repository.PersonRepository;
import com.wrapped.repository.UserRepository;
import com.wrapped.service.ItemService;
import com.wrapped.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class PersonController {
//    Dependency Injection

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Autowired ItemController itemController;

    @Autowired
    UserRepository userRepository;
//    End of Dependency Injection


// GET
//    Start of Get All People
    @RequestMapping(value = "/getAllPeople",
     produces = MediaType.APPLICATION_JSON_VALUE,
     method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Person>> getAllPeople() {
        List<Person> allPeople = personService.listAllPersons();
        return new ResponseEntity<>(allPeople, HttpStatus.OK);
    }
//    End of get all People

// GET
// Start of get People by UserId. This will represent all the people associated with a persons 'shopping list'
    @RequestMapping(value = "/getPeopleByUserId",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Person>> getPeopleByUserId(int userId) {
        List<Person> allPeopleByUser = personService.listPersonByUserId(userId);
        return new ResponseEntity<>(allPeopleByUser, HttpStatus.OK);
    }
//  End of Get people by user Id GET method

// GET
// Start of get Person by Id. This will be just one person, NOT the list of people associated with a user
    @RequestMapping(value = "/getPersonById",
     produces = MediaType.APPLICATION_JSON_VALUE,
     method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Optional<Person>> findPersonById(int id) {
        return new ResponseEntity<>(personRepository.findById(id), HttpStatus.OK);
    }
// end of Get a person by ID

// PUT
// Start of update Person PUT method

    @RequestMapping(value = "/updatePerson",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE,
    method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Optional<Person>> updatePerson(@RequestBody Person person) {
        Person updatePerson = personRepository.findById(person.getId()).get();
        Item updateItemAssociatedWithPerson = itemRepository.getById(person.getItemAttachedId().getId());
        updatePerson.setFirstName(person.getFirstName());
        updatePerson.setLastName(person.getLastName());
        System.out.println(updatePerson.getItemAttachedId().getId());
        System.out.println(updatePerson.getItemAttachedId().getItemName());
        System.out.println(updateItemAssociatedWithPerson);
        updateItemAssociatedWithPerson.setItemName(person.getItemAttachedId().getItemName());
        System.out.println(updateItemAssociatedWithPerson);
//        updatePerson.setItemAttachedId();
        updatePerson.setRelationsip(person.getRelationsip());
        itemRepository.save(updateItemAssociatedWithPerson);
        personRepository.save(updatePerson);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    End of update Person PUT Method.

//    POST
//    Start of savePerson POST method.
    @RequestMapping(value = "/savePerson",
     consumes = MediaType.APPLICATION_JSON_VALUE,
     produces = MediaType.APPLICATION_JSON_VALUE,
     method = RequestMethod.POST)
    public void submitPersonDetails(@RequestBody Person person) {
        Optional<Item> optionalItem = itemRepository.findById(person.getItemAttachedId().getId());
        Item item = null;
        if(!optionalItem.isPresent()) {
            item = itemRepository.save(person.getItemAttachedId());
        }
        else {
            item = optionalItem.get();
        }
        User user = userRepository.findById(person.getUserAttached().getId()).get();
        person.setUserAttached(user);
        person.setItemAttachedId(item);
        personRepository.save(person);
    }
// end of savePerson POST method

// DELETE
// Start of DELETE person Method
    @RequestMapping(value = "/deletePerson",
    method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Optional<Person>> deletePerson(int id) {
        personRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    End of Delete Person DELETE method

}
