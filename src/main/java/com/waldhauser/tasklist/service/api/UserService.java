package com.waldhauser.tasklist.service.api;

import com.waldhauser.tasklist.domain.model.User;
import com.waldhauser.tasklist.rest.model.user.LoginResponse;

public interface UserService {

    User register(String name, String password);

    LoginResponse login(String name, String password);

}
