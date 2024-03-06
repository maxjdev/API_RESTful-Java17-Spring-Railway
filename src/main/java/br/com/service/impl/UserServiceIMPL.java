package br.com.service.impl;

import br.com.domain.model.User;
import br.com.domain.repository.UserRepository;
import br.com.service.UserService;
import br.com.service.exception.BusinessException;
import br.com.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class UserServiceIMPL implements UserService {
    private static final Long UNCHANGEABLE_USER_ID = 1L;

    private final UserRepository repo;
    public UserServiceIMPL(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return this.repo.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return repo.findById(id)
                .orElseThrow(NotFoundException::new);
    }
    @Transactional
    @Override
    public User create(User u) {
        ofNullable(u).orElseThrow(() -> new BusinessException("\n *** User to create must not be null. *** \n"));
        ofNullable(u.getAccount()).orElseThrow(() -> new BusinessException("\n *** User account must not be null. *** \n"));
        ofNullable(u.getCard()).orElseThrow(() -> new BusinessException("\n *** User card must not be null. *** \n"));

        validateChangeableId(u.getId(), "created");

        if (repo.existsByAccountNumber(u.getAccount().getNumber())) {
            throw new BusinessException("\n *** This account number already exists. *** \n");
        }
        if (repo.existsByCardNumber(u.getCard().getNumber())) {
            throw new BusinessException("\n *** This card number already exists. *** \n");
        }

        return repo.save(u);
    }
    @Transactional
    @Override
    public User update(Long id, User u) {
        validateChangeableId(id, "updated");
        User dbUser = this.findById(id);
        if (!dbUser.getId().equals(id)) {
            throw new BusinessException("\n *** Update IDs must be the same. *** \n");
        }

        dbUser.setName(u.getName());
        dbUser.setAccount(u.getAccount());
        dbUser.setCard(u.getCard());
        dbUser.setFeatures(u.getFeatures());
        dbUser.setNews(u.getNews());

        return repo.save(dbUser);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        validateChangeableId(id, "deleted");
        User dbUser = this.findById(id);
        repo.delete(dbUser);
    }

    // ID protection UNCHANGEABLE_USER_ID
    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("\n *** User with ID %d can not be %s. *** \n".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}
