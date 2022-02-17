package com.nika.ebook.api.constants;

public class RouteConstants {
    //Generic
    public static final String ASTERICS = "*";
    public static final String SLASH = "/";
    public static final String API = SLASH + "api";
    private static final String ID = "{id}";
    public static final String UPDATE = SLASH + "update";
    public static final String DELETE = SLASH + "delete";
    public static final String CREATE = SLASH + "create";

    public static final String UPDATE_BY_ID = UPDATE + SLASH + ID;
    public static final String DELETE_BY_ID = DELETE + SLASH + ID;

    //Auth
    public static final String AUTH = SLASH + "auth";
    public static final String AUTH_API = API + AUTH;
    public static final String SIGN_IN = SLASH + "signIn";
    public static final String REGISTER = SLASH + "register";

    //Books
    public static final String BOOKS = SLASH + "books";
    public static final String BOOKS_API = API + BOOKS;
    public static final String GET_BOOK_ENDPOINT = SLASH + "getBook";
    public static final String GET_BOOKS_ENDPOINT = SLASH + "getBooks";
    public static final String CATEGORY = SLASH + "category";
    public static final String GET_BOOK_BY_NAME = GET_BOOK_ENDPOINT + SLASH + "{name}";
    public static final String GET_BOOK_BY_CATEGORY = GET_BOOK_ENDPOINT + CATEGORY + SLASH + "{category}";
    public static final String UPLOAD_FILE = SLASH + "uploadFile" + SLASH + ID;
    public static final String GET_FILE_PDF = SLASH + "getFile" + SLASH + "{filename:.+}";
    //Users
    public static final String USERS = SLASH + "users";
    public static final String USERS_API = API + USERS;
    public static final String GET_BY_USERNAME = SLASH + "{username}";

    public static final String USERS_BOOKS_BY_USERNAME = BOOKS + GET_BY_USERNAME;

    //Combined
    public static final String ALL_AUTH_ROUTES = API + AUTH + SLASH + ASTERICS + ASTERICS;
    public static final String GET_ALL_BOOKS = API + BOOKS + GET_BOOKS_ENDPOINT;
    public static final String ALL_GET_BOOKS_WITH_CATEGORY_ROUTES = API + BOOKS + GET_BOOKS_ENDPOINT + CATEGORY + SLASH + ASTERICS + ASTERICS;
    public static final String ALL_GET_BOOK_ROUTES = API + BOOKS + GET_BOOK_ENDPOINT + SLASH + ASTERICS + ASTERICS;
    public static final String ALL_USERS_WITH_BOOKS_ROUTES = USERS_API + BOOKS + SLASH + ASTERICS + ASTERICS;
}
