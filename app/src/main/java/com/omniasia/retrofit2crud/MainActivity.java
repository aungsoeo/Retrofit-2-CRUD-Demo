package com.omniasia.retrofit2crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.omniasia.retrofit2crud.model.Category;
import com.omniasia.retrofit2crud.remote.APIUtils;
import com.omniasia.retrofit2crud.remote.CategoryService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnAddCat;
    Button btnEditCat;
    Button btnGetCategoryList;
    ListView listView;
    CategoryAdapter adapter;
    CategoryService categoryService;
    List<Category> list = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Retrofit 2 CRUD Demo");

        btnAddCat = (Button) findViewById(R.id.btnAddCategory);
        btnGetCategoryList = (Button) findViewById(R.id.btnGetCategoryList);
        listView = (ListView) findViewById(R.id.listView);
        categoryService = APIUtils.getCategoryService();

        getCategoryList();

        btnAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                intent.putExtra("cat_name", "");
                startActivity(intent);
            }
        });
    }

    public void getCategoryList(){
        Call<List<Category>> call = categoryService.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d("Categories",response.toString());
                if(response.isSuccessful()){
                    list = response.body();
                    Log.d("Categories",list.toString());
                    Toast.makeText(MainActivity.this, "Categories", Toast.LENGTH_SHORT).show();
                    listView.setAdapter(new CategoryAdapter(MainActivity.this, R.layout.list_category, list));
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
