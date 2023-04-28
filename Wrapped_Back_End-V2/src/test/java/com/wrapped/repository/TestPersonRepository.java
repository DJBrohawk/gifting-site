package com.wrapped.repository;

import com.wrapped.Wrapped_Back_EndV2.WrappedBackEndV2Application;
import com.wrapped.entity.Item;
import com.wrapped.entity.Person;
import com.wrapped.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {WrappedBackEndV2Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestPersonRepository {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;


    Person person;
    User user;
    Item item;

    private void setupTestData() {
        user = new User(1, "Bob", "Holden", "BobisBob@Gmail.com", "ThisIsBobsPassword");
        userRepository.save(user);

        item = new Item(1, "TestItem");
        itemRepository.save(item);

        person = new Person(1, "Cade", "Skywalker", user, item, "Son");
        personRepository.save(person);
    }


    @BeforeAll
    void setUp() {
      setupTestData();

    }

    @AfterAll
    void tearDown() {
        personRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();


    }

    @Test
    void testfindPersonsByUserId_Found() {
        List<Person> testPersons = personRepository.findPersonsByUserId(user.getId());
        System.out.println("People associated with user: " + testPersons);

        if (testPersons != null) {
            System.out.println("testPerson: " + testPersons.get(0).getFirstName());
        }

        assertThat(testPersons).isNotNull();
        if (testPersons != null) {
            assertThat(testPersons.get(0).getUserAttached().getId()).isEqualTo(1);
        }

    }

    @Test
    void testFindPersonByUserId_NotFound() {




        List<Person> testPersons = personRepository.findPersonsByUserId(123656);

        assertThat(testPersons.isEmpty()).isTrue();
    }
}
