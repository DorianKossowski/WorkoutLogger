package com.zti.workoutLogger.models.dto;

public class UserDto {
    private String mail;
    private String username;
    private String password;
    private String repPassword;

    public UserDto(String mail, String username, String password, String repPassword) {
        this.mail = mail;
        this.username = username;
        this.password = password;
        this.repPassword = repPassword;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepPassword() {
        return repPassword;
    }

    public void setRepPassword(String repPassword) {
        this.repPassword = repPassword;
    }
}