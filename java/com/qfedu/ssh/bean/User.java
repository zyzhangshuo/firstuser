package com.qfedu.ssh.bean;

import javax.persistence.*;
//实体类
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "gender")
    private String gender;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "registertime")
    private String registertime;
    @Column(name = "icon")
    private String icon;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getRegistertime() {
        return registertime;
    }

    public String getIcon() {
        return icon;
    }

    public User() {
    }

    public User(String username, String password, String gender, String email, String telephone) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.telephone = telephone;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public User setRegistertime(String registertime) {
        this.registertime = registertime;
        return this;
    }

    public User setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", registertime='" + registertime + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
