package br.com.service;

import br.com.domain.model.User;

public interface UserService {
    User findById(Long id);
    User create(User userToCreate);
}
