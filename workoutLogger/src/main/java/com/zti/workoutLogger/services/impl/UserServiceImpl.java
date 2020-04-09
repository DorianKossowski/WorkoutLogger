package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.User;
import com.zti.workoutLogger.models.dto.UserDto;
import com.zti.workoutLogger.repositories.UserRepository;
import com.zti.workoutLogger.services.UserService;
import com.zti.workoutLogger.utils.exceptions.AlreadyExistsException;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public void createUser(UserDto newUserDto) {
        if (!newUserDto.getPassword().equals(newUserDto.getRepPassword())) {
            throw new InvalidArgumentExceptions("Provided passwords are different");
        }
        if (userRepository.existsByMail(newUserDto.getMail())) {
            throw new AlreadyExistsException(newUserDto.getMail());
        }
        if (userRepository.existsByUsername(newUserDto.getUsername())) {
            throw new AlreadyExistsException(newUserDto.getUsername());
        }
        userRepository.save(getNewUser(newUserDto));
        logger.debug(newUserDto.getUsername() + " signed up successfully");
    }

    private User getNewUser(UserDto userDto) {
        User user = new User();
        user.setMail(userDto.getMail());
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        return user;
    }
}