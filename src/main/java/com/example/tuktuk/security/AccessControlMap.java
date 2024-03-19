package com.example.tuktuk.security;

import java.util.LinkedHashMap;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccessControlMap {
    /*
      HTTP Method, Allow URI 의 쌍으로 허용할 URI를 저장한다.
    */
    private final Map<AccessControlRecord, Boolean> accessControlMap = new LinkedHashMap<>();

    public AccessControlMap() {
        accessControlMap.put(AccessControlRecord.from("GET", "/"), Boolean.TRUE);
        accessControlMap.put(AccessControlRecord.from("GET", "/my-stadiums"), Boolean.FALSE);
        accessControlMap.put(AccessControlRecord.from("GET", "/my-schedules"), Boolean.FALSE);
        accessControlMap.put(AccessControlRecord.from("POST", "/users"), Boolean.TRUE);
        accessControlMap.put(AccessControlRecord.from("POST", "/fieldowners"), Boolean.TRUE);
    }

    public boolean checkURI(String httpMethod, String requestUri) {
        boolean flag = false;

        for (AccessControlRecord record : accessControlMap.keySet().stream().toList()) {
            if (requestUri.contains(record.getUrl()) && record.getHttpMethod().equals(httpMethod)) {
                flag = accessControlMap.get(record);
            }
        }

        return flag;
    }
}
