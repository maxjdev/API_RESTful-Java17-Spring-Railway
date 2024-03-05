package br.com.service.impl;

import br.com.domain.model.User;
import br.com.domain.repository.UserRepository;
import br.com.service.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceIMPL implements UserService {
    // IoC & DI
    private final UserRepository repo;
    public UserServiceIMPL(UserRepository repo) {
        this.repo = repo;
    }
    // Methods
    @Override
    public User findById(Long id) {
        return repo.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(User u) {
        if (repo.existsByAccountNumber(u.getAccount().getNumber())) {
            throw new IllegalArgumentException("\n ***This User ID already exists.*** \n");
        }
        return repo.save(u);
    }
}
