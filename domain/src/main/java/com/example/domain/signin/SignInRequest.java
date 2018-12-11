package com.example.domain.signin;

public class SignInRequest {

    public String email;
    public String password;

    public SignInRequest(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
}
