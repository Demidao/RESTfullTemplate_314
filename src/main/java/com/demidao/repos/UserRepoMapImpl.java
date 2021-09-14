package com.demidao.repos;

import com.demidao.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepoMapImpl implements UserRepo {

    private Map<Long, User> dataBase = new HashMap<>();

    public UserRepoMapImpl() {}

    @Override
    public User save(User user) {
        dataBase.put(user.getId(), user);
        return dataBase.get(user.getId());
    }

    @Override
    public void save(User[] users) {
        for(User u : users) {
            dataBase.put(u.getId(), u);
        }
    }

    @Override
    public User update(User user) {
        return save(user);
    }

    @Override
    public User delete(long id) {
        return dataBase.remove(id);
    }
}
