package com.example.tuktuk.users.auth;

import com.example.tuktuk.users.domain.Provider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;

import java.util.List;
import java.util.Map;

import static com.example.tuktuk.users.auth.IdentityParser.parseIdentities;

@Getter
@Slf4j
public class UserInfo {

    private String id;

    private String email;

    private Provider provider;

    public UserInfo(List<AttributeType> attributes) {
        String providerName = "Regular"; // 기본값 설정
        for (AttributeType attribute : attributes) {
            String attributeName = attribute.name();
            String attributeValue = (String) attribute.value();

            switch (attributeName) {
                case "sub":
                    id = (String) attributeValue;
                    break;
                case "email":
                    email = (String) attributeValue;
                    break;
                case "identities":
                    String identitiesString = (String) attributeValue;
                    List<Map<String, Object>> identities = parseIdentities(identitiesString);
                    for (Map<String, Object> identity : identities) {
                        providerName = (String) identity.getOrDefault("providerName", providerName);

                        if (providerName != null) break;
                    }
                    break;
            }
        }
        provider = Provider.fromName(providerName);
    }


}




