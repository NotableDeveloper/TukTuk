package com.example.tuktuk.users.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class IdentityParser {

    private IdentityParser() {
    }

    public static List<Map<String, Object>> parseIdentities(String identitiesString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(identitiesString, new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }
}
