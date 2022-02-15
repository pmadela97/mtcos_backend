package com.user.domain;

public interface UserRepository {

    void save(User user);

    void update(User user);
}
