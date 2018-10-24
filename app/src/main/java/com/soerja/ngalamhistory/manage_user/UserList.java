package com.soerja.ngalamhistory.manage_user;

public class UserList {

    private String email, pass, type, userName;

    public UserList(){

    }

    public UserList(String email, String pass, String type, String userName) {
        this.email = email;
        this.pass = pass;
        this.type = type;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
