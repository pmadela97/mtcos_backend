package com.user.application;

import com.user.domain.User;
import com.user.domain.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserQueries {

    List<User> getAll();

    Optional<User> findEntityById(long id);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmailAddress(String emailAddress);

    List<UserDto> getAllDto();

    Optional<UserDto> findUserDtoById(long id);

    Optional<UserDto> findUserDtoByUsername(String username);

    List<UserDto> getAllDtoByUserRole(UserRole userRole);

    public UserDto createDto(User user);
}
