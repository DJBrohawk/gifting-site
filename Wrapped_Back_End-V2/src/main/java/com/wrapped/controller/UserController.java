package com.wrapped.controller;

import com.wrapped.entity.User;
import com.wrapped.repository.UserRepository;
import com.wrapped.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
public class UserController {
//    Dependency Injection
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    End of dependecy injection

// GET
// Start of GetAllUsers Method
    @RequestMapping(value = "/getAllUsers",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.listallUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
//    End of getAllUsers Get method.

// GET
// Start of get User by Id
    @RequestMapping(value = "/getUserById",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity <Optional<User>> findUserById(int id) {
        return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);
    }
//    End of get User by ID

// POST
// Start of saveUser POST Method

    @RequestMapping(value = "/saveUser",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        method = RequestMethod.POST)
    public void saveUser(@RequestBody User user) {
        userRepository.save(user);
    }
//    end of save User POST method

// PUT
// Start of updateUser method
    @RequestMapping(value = "/updateUser",
     consumes = MediaType.APPLICATION_JSON_VALUE,
     produces = MediaType.APPLICATION_JSON_VALUE,
    method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Optional<User>> updateUser(@RequestBody User user) {

        User updateUser = userRepository.findById(user.getId()).get();

        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setLastName(user.getLastName());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setPassword(user.getPassword());

        userRepository.save(updateUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    End of update User method

// DELETE
// Start of Delete User DELETE method
    @RequestMapping(value = "/deleteUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Optional<User>> deleteUser(int id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
// End of deleteUser DELETE Method

// POST
// Start of Login functionality

    @RequestMapping(value = "/loginUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User loginUser = userService.loginUser(user);

        if (loginUser != null ) {
            return new ResponseEntity<>(loginUser, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }










}
