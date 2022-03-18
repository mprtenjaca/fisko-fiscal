package com.fisco.fiscal.fiskofiscal.controller;

import com.fisco.fiscal.fiskofiscal.dto.UserDTO;
import com.fisco.fiscal.fiskofiscal.model.User;
import com.fisco.fiscal.fiskofiscal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public List<User> getUsers(){
        return userService.getAll();
    }

    // Save User
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update (@RequestParam("id") Long id, @RequestBody User user){
        Optional<UserDTO> optionalUpdatedUser = userService.update(id, user);
        if(optionalUpdatedUser.isPresent()){
            return new ResponseEntity<>(optionalUpdatedUser.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
