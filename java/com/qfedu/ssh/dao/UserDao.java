package com.qfedu.ssh.dao;

import com.qfedu.ssh.bean.User;

public interface UserDao {
    User selectUserByLoginInfo(String loginInfo,String password);
    void insertIntoUser(User user);
    boolean selectUsername(String username);
    boolean selectEmail(String email);
    boolean selectTelephone(String telephone);
    void updateUser(User user);
}
