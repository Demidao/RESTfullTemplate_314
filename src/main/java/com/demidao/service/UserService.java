package com.demidao.service;

import com.demidao.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    void save(User[] users);

    User save(User user);

    User update(User user);

    User delete(long id);

}
