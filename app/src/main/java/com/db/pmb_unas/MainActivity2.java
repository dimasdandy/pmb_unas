package com.db.pmb_unas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.db.pmb_unas.Adapter.CategoryAdapter;
import com.db.pmb_unas.DBHelper.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity2 extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recycler_category;
    Dialog mDialog, mDialog2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FloatingActionButton floatingActionButton=findViewById(R.id.fab1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setContentView(R.layout.layout_popup);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });

        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.layout_popup);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("PMB Test");
        setSupportActionBar(toolbar);

        recycler_category = findViewById(R.id.recycler_category);
        recycler_category.setHasFixedSize(true);
        recycler_category.setLayoutManager(new GridLayoutManager(this, 1));

        //GetCategory
        CategoryAdapter adapter = new CategoryAdapter(MainActivity2.this, DBHelper.getInstance(this).getAllCategories());
        int spacePixel = 1;
        recycler_category.addItemDecoration(new SpaceDecoration(spacePixel));
        recycler_category.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent gotoawal = new Intent(MainActivity2.this, myprofile.class);
        startActivity(gotoawal);
        finish();
    }
}
