package com.app.util;

import org.springframework.security.core.Authentication;

public class AuthUtil {

    public static Long getCurrentUserId(Authentication authentication){
        return Long.parseLong((String) authentication.getPrincipal());
    }
}
