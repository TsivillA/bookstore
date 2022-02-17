package com.nika.ebook.api.constants;

public class SwaggerConstant {
    public static final String SECURITY_REFERENCE = "Token Access";
    public static final String AUTHORIZATION_DESCRIPTION = "Full API Permission";
    public static final String AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_SCOPE = "Unlimited";
    public static final String CONTACT_EMAIL = "wivila2000nika@gmail.com";
    public static final String CONTACT_URL = "https://www.EBook.com";
    public static final String CONTACT_NAME = "EBoook Support";
    public static final String API_TITLE = "EBook Management open API";
    public static final String API_DESCRIPTION = "In the description property, in addition to describing your " +
            "overall API, you might want to provide some basic instructions to users on how to use Swagger UI. " +
            "If there’s a test account they should use, you can provide the information they need in this space. " +
            "In the description property, in addition to describing your " +
            "overall API, you might want to provide some basic instructions to users on how to use Swagger UI. " +
            "If there’s a test account they should use, you can provide the information they need in this space. " +
            "</br></br><h3> **Note**: This API requires an `API KEY`, please log into your account to access your key <a target='_blank' href=\"http://localhost:4200/login\">here</a> </h3>.";
    public static final String TERM_OF_SERVICE = "Your term of service will go here";
    public static final String API_VERSION = "1.1";
    public static final String LICENSE = "Apache License 2.1.0";
    public static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";
    public static final String SECURE_PATH = "/*/.*";

    public static final String USER_TAG = "Users-Controller";
    public static final String BOOK_TAG = "Books-Controller";
    public static final String AUTH_TAG = "Auth-Controller";

    public static final String[] PUBLIC_URLS = {
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "api/books/getBooks"

    };
}
