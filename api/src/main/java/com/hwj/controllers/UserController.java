package com.hwj.controllers;

import com.hwj.beans.User;
import com.hwj.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Huang Weijie
 */
@RequestMapping( "/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/listUser")
    public List<User> listUser(){
        List<User> users = userService.listUser();
        return users;
    }

    @PostMapping("/addUser")
    public boolean addUser(@RequestBody User user){
        boolean b = true;
        List<User> users = userService.listUser();
        for (User u:users) {
            if(u.getUsername().equals(user.getUsername())){
                b = false;
                break;
            }
        }
        if(b) {
            userService.addUser(user);
            return true;
        }else {
            return false;
        }
    }

    @PostMapping("/findUser")
    public List<User> findUserByName(@RequestBody String username){
        List<User> users = userService.findUserByName(username);
        return users;
    }

    @PostMapping("/deleteUser")
    public boolean deleteUser(@RequestBody String username){
        userService.deleteUser(username);
        return true;
    }

    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody User user){
        userService.updateUser(user);
        return true;
    }
}
