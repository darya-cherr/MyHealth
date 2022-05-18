package com.example.myhealth.Model;

public class User {
    private String email, name, password, zodiac;


    public User(){
    }

    public User(String email, String name, String password, String zodiac) {
        this.email = email;
        this.name = name;
        this.zodiac = zodiac;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
