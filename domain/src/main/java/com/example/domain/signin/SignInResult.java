package com.example.domain.signin;

public class SignInResult {
    public String uid;
    public String username;
    public String email;

    public SignInResult(String uid, String username, String email) {
        this.uid = uid;
        this.username = username;
        this.email = email;
    }
}
