package com.omniasia.retrofit2crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.omniasia.retrofit2crud.model.Category;
import com.omniasia.retrofit2crud.remote.APIUtils;
import com.omniasia.retrofit2crud.remote.CategoryService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    CategoryService categoryService;
    EditText edtCId;
    EditText edtCname;
    EditText edtCDesc;
    Button btnSave;
    Button btnDel;
    TextView txtCId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        setTitle("Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtCId = (TextView) findViewById(R.id.txtCId);
        edtCId = (EditText) findViewById(R.id.edtCId);
        edtCname = (EditText) findViewById(R.id.edtCname);
        edtCDesc = (EditText) findViewById(R.id.edtCDesc);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        categoryService = APIUtils.getCategoryService();

        Bundle extras = getIntent().getExtras();
        final String CId = extras.getString("cat_id");
        String CName = extras.getString("cat_name");
        String CDesc = extras.getString("cat_desc");

        edtCId.setText(CId);
        edtCname.setText(CName);
        edtCDesc.setText(CDesc);

        if(CId != null && CId.trim().length() > 0 ){
            edtCId.setFocusable(false);
        } else {
            txtCId.setVisibility(View.INVISIBLE);
            edtCId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category c = new Category();
                c.setCategory_name(edtCname.getText().toString());
                c.setCategory_description(edtCDesc.getText().toString());
                if(CId != null && CId.trim().length() > 0){
                    //update category
                    updateCat(Integer.parseInt(CId), c);
                    Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //add category
                    addCat(c);
                    Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCat(Integer.parseInt(CId));

                Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addCat(Category c){
        Call<Category> call = categoryService.addCategory(c);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CategoryActivity.this, "Category created successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());

                Toast.makeText(CategoryActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateCat(int id, Category c){
        Call<Category> call = categoryService.updateCategory(id, c);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                Log.d("REsponse : ", response.toString());
                Toast.makeText(CategoryActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){
                    Toast.makeText(CategoryActivity.this, "Category updated successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteCat(int id){
        Call<Category> call = categoryService.deleteCategory(id);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CategoryActivity.this, "Category deleted successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
