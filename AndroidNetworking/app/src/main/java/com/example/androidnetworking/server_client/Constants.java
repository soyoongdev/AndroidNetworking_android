package com.example.androidnetworking.server_client;

public class Constants {
    private static String ip = "192.168.1.10";
    private static final String BASE_URL_LOGIN = "http://"+ ip +":8080/php/login_manager/login_manager.php?apicall=";
    public static final String URL_REGISTER = BASE_URL_LOGIN + "signup";
    public static final String URL_LOGIN = BASE_URL_LOGIN + "login";
    public static final String URL_CHECK_USER = BASE_URL_LOGIN + "checkInfo";
    public static final String URL_CHANGE_PASSWORD = BASE_URL_LOGIN + "changePass";

    private static final String BASE_URL_PRODUCT = "http://"+ ip +":8080/php/products/product_controller.php?apicall=";
    public static final String URL_INSERT_PRODUCT = BASE_URL_PRODUCT + "insertProduct";
    public static final String URL_UPDATE_PRODUCT = BASE_URL_PRODUCT + "updateProduct";
    public static final String URL_GET_ALL_PRODUCT = BASE_URL_PRODUCT + "getAllProduct";
    public static final String URL_DELETE_PRODUCT = BASE_URL_PRODUCT + "deleteProduct";

    private static final String BASE_URL_CAT = "http://"+ ip +":8080/php/products/category_controller.php?apicall=";
    public static final String URL_INSERT_CAT = BASE_URL_CAT + "insertCat";
    public static final String URL_UPDATE_CAT = BASE_URL_CAT + "updateCat";
    public static final String URL_GET_ALL_CAT = BASE_URL_CAT + "getAllCat";
    public static final String URL_DELETE_CAT = BASE_URL_CAT + "deleteCat";


}
