package com.qfedu.ssh.service;

import com.qfedu.ssh.bean.User;

public interface UserService {
    User findUser(String loginInfo, String password);
    void addUser(User user);
    boolean findUsername(String username);
    boolean findEmail(String email);
    boolean findTelephone(String telephone);
    void editUser(User user);
}
