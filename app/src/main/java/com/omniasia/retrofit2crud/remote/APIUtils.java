package com.omniasia.retrofit2crud.remote;

public class APIUtils {
    private APIUtils(){
    };

    public static final String API_URL = "http://192.168.100.12/WhereToBuyAdmin/public/api/";

    public static CategoryService getCategoryService(){
        return RetrofitClient.getClient(API_URL).create(CategoryService.class);
    }
}
