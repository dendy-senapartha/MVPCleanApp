package com.example.data.user.repository.source;

import com.example.data.AbstractEntityDataFactory;
import com.example.data.user.repository.source.network.NetworkUserEntityData;
import com.example.data.user.repository.source.network.UserNetwork;


import javax.inject.Inject;

public class UserEntityDataFactory extends AbstractEntityDataFactory<UserEntityData> {

    private final UserNetwork userNetwork;

    @Inject
    public UserEntityDataFactory(UserNetwork userNetwork) {
        this.userNetwork = userNetwork;
    }


    @Override
    public UserEntityData createData(String source) {
        return new NetworkUserEntityData(userNetwork);
    }
}
