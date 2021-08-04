package com.example.androidnetworking.server_client;

public class Constants {
    private static final String BASE_URL = "http://192.168.1.5:8080/php/login_manager/";
    public static final String URL_REGISTER = BASE_URL + "register.php";
    public static final String URL_LOGIN = BASE_URL + "login.php";

    // Shared emlement name
    public static final String TRANSITION_REGISTER = "root_register";
    public static final String TRANSITION_LOGIN = "root_login";

}
