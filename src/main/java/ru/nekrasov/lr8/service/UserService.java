package ru.nekrasov.lr8.service;

import ru.nekrasov.lr8.dto.UserDto;
import ru.nekrasov.lr8.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();

    void setNewRoles(Long userId, String roleName);
}