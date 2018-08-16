package com.omniasia.retrofit2crud;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.omniasia.retrofit2crud.model.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    private Context context;
    private List<Category> categories;

    public CategoryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.categories = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_category, parent, false);

        TextView txtCatId = (TextView) rowView.findViewById(R.id.txtCategoryId);
        TextView txtCatname = (TextView) rowView.findViewById(R.id.txtCategoryName);
        TextView txtCatDesc = (TextView) rowView.findViewById(R.id.txtCategoryDesc);

        txtCatId.setText(String.format("#ID: %d", categories.get(pos).getId()));
        txtCatname.setText(String.format("Category NAME: %s", categories.get(pos).getCategory_name()));
        txtCatDesc.setText(String.format("Category Desc: %s", categories.get(pos).getCategory_description()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity User Form
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("cat_id", String.valueOf(categories.get(pos).getId()));
                intent.putExtra("cat_name", categories.get(pos).getCategory_name());
                intent.putExtra("cat_desc", categories.get(pos).getCategory_description());
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
