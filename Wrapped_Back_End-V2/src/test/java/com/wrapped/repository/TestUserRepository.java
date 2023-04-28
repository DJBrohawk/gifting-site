package com.wrapped.repository;

import com.wrapped.Wrapped_Back_EndV2.WrappedBackEndV2Application;
import com.wrapped.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {WrappedBackEndV2Application.class})
public class TestUserRepository {

    @Autowired
    private UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {

        user = new User(1, "Bob", "Holden", "BobisBob@Gmail.com", "ThisIsBobsPassword");
        userRepository.save(user);

    }

    @AfterEach
    void tearDown() {
        user = null;
        userRepository.deleteAll();
    }
    @Test
    void testFindByEmailAndPassword_Found() {
        User testUser = userRepository.findUserByEmailAndPassword("BobisBob@Gmail.com", "ThisIsBobsPassword");
        System.out.println("testUser: " + testUser);
        if (testUser != null) {
            System.out.println("testUser email: " + testUser.getEmail());
        }
        assertThat(testUser).isNotNull();
        if (testUser != null) {
            assertThat(testUser.getEmail()).isEqualTo("BobisBob@Gmail.com");
        }
    }

    @Test
    void testFindByEmailAndPassword_NotFound() {
        User testUser = userRepository.findUserByEmailAndPassword("BobisBobWrongEmail@Gmail.com", "ThisIsBobsPassword");
        assertThat(testUser).isNull();
    }

}
