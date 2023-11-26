package com.rest.demo.service;

import com.rest.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(int id);

    User save(User user);

    User deleteById(int id);

    User update(User user, int id);

}