package com.wrapped.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrapped.entity.Item;
import com.wrapped.entity.Person;
import com.wrapped.entity.User;
import com.wrapped.repository.PersonRepository;
import com.wrapped.repository.UserRepository;
import com.wrapped.service.PersonService;
import com.wrapped.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {UserService.class, UserController.class, UserRepository.class})
@WebMvcTest(TestUserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc

public class TestUserController {

    @Autowired
    private MockMvc mockMvc;

//    @Autowired
//    private PersonService personService;

    @Autowired
    private UserService userService;

//    @Autowired
//    PersonController personController;

    @Autowired
    UserController userController;

//    @MockBean
//    PersonRepository personRepository;

    @MockBean
    private UserRepository userRepository;

    User testUserOne;
    User testUserTwo;

    List<User> userList = new ArrayList<>();
    private DeserializationContext objectMapper;

    private void setUpTestData() {
        testUserOne = new User(101, "TestFirstName", "TestLastName", "testEmail@test.com", "FakePassword");
        testUserTwo = new User(102, "TestFirstNameTwo", "TestLastNameTwo", "TestEmail2@Mail.com", "FakePassword");

        userList.add(testUserOne);
        userList.add(testUserTwo);
    }

    @BeforeEach
    void setUp() {
        setUpTestData();
    }

    @AfterAll
    void tearDown() {

        userRepository.deleteAll();
    }


    @Test
    void testGetAllUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(userList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getAllUsers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        String responseContent = result.getResponse().getContentAsString();
        List<User> users = new ObjectMapper().readValue(responseContent, new TypeReference<List<User>>() {
        });
        assertEquals(2, users.size());
        assertEquals(users.get(0).getFirstName(), "TestFirstName");
    }

    @Test
    void testFindUserById() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUserOne));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getUserById?id=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        User responseUser = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

        assertEquals(testUserOne.getFirstName(), responseUser.getFirstName());
        assertEquals(testUserOne.getLastName(), responseUser.getLastName());
        assertEquals(testUserOne.getEmail(), responseUser.getEmail());
    }

    @Test
    void testUpdateUser() throws Exception {
      User user = new User(103, "TestFirstNameInUpdateTest", "TestLastNameInUpdateTest", "testEmailInUpdateTest@test.com", "FakePasswordInUpdateTest");
      User updatedUser = new User(103, "TestFirstNameInUpdateTestThisPartShouldUpdate", "TestLastNameInUpdateTest", "testEmailInUpdateTest@test.com", "FakePasswordInUpdateTest");

      when(userRepository.findById(103)).thenReturn(Optional.of(user));
      when(userRepository.save(any(User.class))).thenReturn(updatedUser);
      ObjectMapper objectMapper = new ObjectMapper();
      String json = objectMapper.writeValueAsString(updatedUser);

      MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/updateUser")
              .content(json)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andReturn();

      verify(userRepository, times(1)).findById(103);
      verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = new User(106, "DeleteThisUserFirstName", "DeleteThisUserLastName", "DeleteThisUserEmail@gmail.com", "DeleteThisPassword");
        userRepository.save(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/deleteUser?id=" + 106)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(userRepository, times(1)).deleteById(user.getId());
        assertThat(userRepository.findById(106)).isEmpty();
    }







}



















