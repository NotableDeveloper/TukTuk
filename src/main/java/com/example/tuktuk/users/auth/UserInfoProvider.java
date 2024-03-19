package com.example.tuktuk.users.auth;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GetUserResponse;

import java.util.List;

@Component
public class UserInfoProvider {

    public List<AttributeType> getUserInfoFromAuthServer(String accessToken) {
        CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        GetUserResponse userResponse = cognitoClient.getUser(GetUserRequest.builder()
                .accessToken(accessToken)
                .build());

        return userResponse.userAttributes();
    }
}
