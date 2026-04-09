package Checking.OTP.verfification.dto;

import jakarta.persistence.Entity;


public class Userdto {
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
