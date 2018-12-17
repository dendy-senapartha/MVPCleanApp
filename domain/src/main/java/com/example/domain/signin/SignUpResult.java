package com.example.domain.signin;

public class SignUpResult {
    public String uid;
    public String username;
    public String email;
    public String exception;

    public SignUpResult(String uid, String username, String email) {
        this.uid = uid;
        this.username = username;
        this.email = email;
    }

    public SignUpResult() {

    }
}
