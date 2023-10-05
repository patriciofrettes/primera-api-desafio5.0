package com.folcademy.primeraapi.controllers;

import com.folcademy.primeraapi.models.Dtos.UserAddDTO;
import com.folcademy.primeraapi.models.Dtos.UserEditDTO;
import com.folcademy.primeraapi.models.Dtos.UserReadDTO;
import com.folcademy.primeraapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<List<UserReadDTO>> findAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping(value = "/users")
    public ResponseEntity<UserReadDTO> findById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping
    public ResponseEntity<UserReadDTO> add(@RequestBody UserAddDTO userAddDTO){
        return ResponseEntity.ok(userService.add(userAddDTO));
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserReadDTO> deleteById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.deleteById(userId));
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserReadDTO> edit(
            @PathVariable Integer userId,
            @RequestBody UserEditDTO user
    ) {
        return ResponseEntity.ok(userService.edit(userId, user));
    }
    }




