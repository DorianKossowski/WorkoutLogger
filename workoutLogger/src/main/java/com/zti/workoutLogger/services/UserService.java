package com.zti.workoutLogger.services;

import com.zti.workoutLogger.models.dto.UserDto;

public interface UserService {

    void createUser(UserDto newUserDto);
}