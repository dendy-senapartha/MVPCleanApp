package com.example.data.user.repository.mapper;

import com.example.data.user.repository.source.network.response.SignInResponse;
import com.example.domain.signin.SignInResult;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRespondMapper {

    @Inject
    public UserRespondMapper() {

    }

    public SignInResult transform(SignInResponse response) {
        SignInResult result = null;
        if(response !=null)
        {
            result = new SignInResult(response.userEntity.uid, response.userEntity.name, response.userEntity.email);
        }
        return result;
    }

}
