package com.omniasia.retrofit2crud.remote;

import com.omniasia.retrofit2crud.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("categories")
    Call<List<Category>> getCategory();

    @POST("categories/add")
    Call<Category> addCategory(@Body Category category);

    @POST("categories/update/{id}")
    Call<Category> updateCategory(@Path("id") int id, @Body Category category);

    @POST("categories/delete/{id}")
    Call<Category> deleteCategory(@Path("id") int id);
}
