package com.rest.demo.service.impl;

import com.rest.demo.model.User;
import com.rest.demo.service.UserService;
import com.rest.demo.utility.Constants;
import com.rest.demo.utility.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try{
            String response = Utils.apiCall(null,0, Constants.GET);
            userList =Utils.convertJsonToUserList(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User findById(int id) {
        User user = new User();
        try{
            String response = Utils.apiCall(null,id,Constants.GET);
            user =Utils.convertJsonToUser(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User save(User user) {
        try{
            String response = Utils.apiCall(user,0,Constants.POST);
            user =Utils.convertJsonToUser(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


    @Override
    public User deleteById(int id) {
        User user = new User();
        try{
            user = findById(id);
            String response = Utils.apiCall(null,id,Constants.DELETE);
            user =Utils.convertJsonToUser(response);
            if (user == null) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    @Override
    public User update(User user, int id) {
        User existingUser = new User();
        try{
            existingUser = findById(id);
            if(Objects.nonNull(existingUser)){
                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                existingUser.setLocation(user.getLocation());
                existingUser.setProfilePicture(user.getProfilePicture());
                String response = Utils.apiCall(existingUser,id,Constants.PUT);
                if(response != null)
                    user =Utils.convertJsonToUser(response);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

}
