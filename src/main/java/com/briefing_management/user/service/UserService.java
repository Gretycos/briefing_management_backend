package com.briefing_management.user.service;

import com.briefing_management.user.model.User;

public interface UserService {
    User getUserByUsername(String username);
    User getUserById(String id);
    int addUser(User user);
}
