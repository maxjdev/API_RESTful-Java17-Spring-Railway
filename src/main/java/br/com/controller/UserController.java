package br.com.controller;

import br.com.domain.model.User;
import br.com.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    // IoC & DI
    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    // EndPoints
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        var u = service.findById(id);
        return ResponseEntity.ok(u);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        var uCreated = service.create(u);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(uCreated);
    }
}
