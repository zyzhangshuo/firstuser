package com.qfedu.ssh.service.impl;

import com.qfedu.ssh.bean.User;
import com.qfedu.ssh.dao.UserDao;
import com.qfedu.ssh.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Resource(name = "userDao")
    private UserDao userDao;


    @Override
    public User findUser(String loginInfo, String password) {
        return userDao.selectUserByLoginInfo(loginInfo, password);
    }

    @Override
    public void addUser(User user) {
        userDao.insertIntoUser(user);
    }

    @Override
    public boolean findUsername(String username) {
        return userDao.selectUsername(username);
    }

    @Override
    public boolean findEmail(String email) {
        return userDao.selectEmail(email);
    }

    @Override
    public boolean findTelephone(String telephone) {
        return userDao.selectTelephone(telephone);
    }

    @Override
    public void editUser(User user) {
userDao.updateUser(user);

    }
}
