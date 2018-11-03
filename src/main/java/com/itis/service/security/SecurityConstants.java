package com.itis.service.security;

public class SecurityConstants {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    static final String SECRET = "ITISServiceSecretKey";
    static final long EXPIRATION_TIME = 864_000_000;
    static final String SIGN_UP_URL = "/users/registration";

}
