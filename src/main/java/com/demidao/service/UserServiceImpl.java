package com.demidao.service;

import com.demidao.models.User;
import com.demidao.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;


    public UserServiceImpl() {}

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;

    }

    @Override
    public void save(User[] users) {
        userRepo.save(users);
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public User update(User user) {
        return userRepo.update(user);
    }

    @Override
    public User delete(long id) {
        return userRepo.delete(id);
    }

}
