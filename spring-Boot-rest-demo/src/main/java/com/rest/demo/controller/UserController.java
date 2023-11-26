package com.rest.demo.controller;

import com.rest.demo.model.User;
import com.rest.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> findAll() {
        List<User> usersList = new ArrayList<>();
        try{
            usersList =userService.findAll();
        }catch (Exception e){
            logger.error("Exception caused in findAll "+e);
        }
        return usersList;
    }



    @GetMapping("/{id}")
    public User findById(@PathVariable int id) {
        User user = new User();
        try{
            user =userService.findById(id);
        }catch (Exception e){
            logger.error("Exception caused in findById "+e);
        }
        return user;
    }

    @PostMapping("")
    public User save(@RequestBody User user) {
        try{
            user = userService.save(user);
            if (user == null) {
                return null;
            }
        }catch (Exception e){
            logger.error("Exception caused in save "+e);
        }
        return user;
    }

    @DeleteMapping("/{id}")
    public User deleteById(@PathVariable int id) {
        User user = new User();
        try{
            user = userService.deleteById(id);
            if (user == null) {
                return null;
            }
        }catch (Exception e){
            logger.error("Exception caused in deleteById "+e);
        }
        return user;
    }

    @PutMapping("/{id}")
    public User update(@PathVariable int id, @RequestBody User user) {
        try{
            user = userService.update(user, id);
            if (user == null) {
                return null;
            }
        }catch (Exception e){
            logger.error("Exception caused in update "+e);
        }
        return user;
    }
}
