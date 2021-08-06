package com.example.androidnetworking.server_client;

public class Constants {
    private static final String BASE_URL_LOGIN = "http://192.168.1.10:8080/php/login_manager/login_manager.php?apicall=";
    public static final String URL_REGISTER = BASE_URL_LOGIN + "signup";
    public static final String URL_LOGIN = BASE_URL_LOGIN + "login";
    public static final String URL_CHECK_USER = BASE_URL_LOGIN + "checkInfo";
    public static final String URL_CHANGE_PASSWORD = BASE_URL_LOGIN + "changePass";

    private static final String BASE_URL_PRODUCT = "http://192.168.1.10:8080/php/products/product_controller.php?apicall=";
    public static final String URL_INSERT = BASE_URL_PRODUCT + "insertProduct";
    public static final String URL_UPDATE = BASE_URL_PRODUCT + "updateProduct";
    public static final String URL_GET_ALL = BASE_URL_PRODUCT + "getAllProduct";
    public static final String URL_DELETE = BASE_URL_PRODUCT + "deleteProduct";


}
