package com.student.domain;

import com.user.domain.User;

import java.util.Optional;

public interface StudentRepository {

    Optional<Long> findUserIdByUserName(String userName);
    Optional<Long> findUserNameByUserId(long id);

    Optional<User> findUserByStudentId(long id);

}
