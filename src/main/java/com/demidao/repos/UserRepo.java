package com.demidao.repos;

import com.demidao.models.User;

import java.util.List;

public interface UserRepo {

    User save(User user);

    void save(User[] users);

    User update(User user);

    User delete(long id);
}
