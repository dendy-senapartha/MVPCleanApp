package com.example.data.user.repository.source.network.response;

import com.example.data.user.UserEntity;

public class SignInResponse {

    public UserEntity userEntity;
    public String exception;

    public SignInResponse() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public SignInResponse(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}
