package com.zti.workoutLogger.utils.auth;

import com.zti.workoutLogger.models.User;

public interface AuthenticatedUserGetter {

    User get();
}