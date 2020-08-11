package com.testows.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/users";
    public static final String SIGN_IN_URL = "/api/v1/login";
    public static final String TOKEN_SECRET = "mavlyanov95";
    public static final String H2_CONSOLE_URL = "/h2-console/**";
}
