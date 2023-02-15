package com.hwj.service.impl;

import com.hwj.beans.User;
import com.hwj.dao.UserDao;
import com.hwj.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public List<User> listUser() {
        List<User> users = userDao.listUser();
        return users;
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public List<User> findUserByName(String username) {
        List<User> users = userDao.findUserByName(username);
        return users;
    }

    @Override
    public void deleteUser(String username) {
        userDao.deleteUser(username);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
