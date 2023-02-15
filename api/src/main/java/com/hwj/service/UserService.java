package com.hwj.service;

import com.hwj.beans.User;

import java.util.List;
public interface UserService {

    public List<User> listUser();
    public void addUser(User user);
    public List<User> findUserByName(String username);
    public void deleteUser(String username);
    public void updateUser(User user);
}
