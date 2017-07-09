package com.smetnertest.controller;

import com.smetnertest.dto.DtoUser;
import com.smetnertest.model.ListWrapper;
import com.smetnertest.service.ContactService;
import com.smetnertest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * A REST controller to handle all {@link com.smetnertest.model.User} related
 * requests: get all users, edit user profile
 */

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private ContactService contactService;


    @Autowired
    public UserController(UserService userService, ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<DtoUser>> getAllUser() {
        List<DtoUser> result = userService.getAllUsers();
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoUser> updateUserContact(@PathVariable("id") long id,
                                                     @Valid @RequestBody DtoUser dtoUser) {
        DtoUser user = userService.findOne(id);
        if (user != null && id == user.getId()) {
            contactService.updateContact(dtoUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @PutMapping("/all")
    public ResponseEntity<List<DtoUser>> updateAllUsers(@Valid @RequestBody ListWrapper dtoUsers) {
        List<DtoUser> currentUsers = userService.getAllUsers();
        if (currentUsers.size() == dtoUsers.getUsers().size()) {
            dtoUsers.getUsers().forEach(contactService::updateContact);
            return new ResponseEntity<List<DtoUser>>(userService.getAllUsers(), HttpStatus.OK);
        }
        return new ResponseEntity<List<DtoUser>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoUser> getUserById(@PathVariable("id") long id) {
        DtoUser user = userService.findOne(id);
        if (user != null && user.getId() == id) {
            return new ResponseEntity<DtoUser>(user, HttpStatus.OK);
        }
        return new ResponseEntity<DtoUser>(HttpStatus.NOT_FOUND);
    }


}
