package com.zti.workoutLogger.utils.auth.impl;

import com.zti.workoutLogger.models.User;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserGetterImpl implements AuthenticatedUserGetter {

    @Override
    public User get() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}