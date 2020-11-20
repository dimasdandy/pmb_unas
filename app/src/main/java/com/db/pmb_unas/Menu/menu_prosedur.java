package com.db.pmb_unas.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.db.pmb_unas.R;
import com.db.pmb_unas.webview.webview_pdf_prosedur;

public class menu_prosedur extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_prosedur);

        TextView tv_klikdisini_satu = findViewById(R.id.tv_klikdisini_satu);
        tv_klikdisini_satu.setOnClickListener(this);

        TextView tv_klikdisini_dua = findViewById(R.id.tv_klikdisini_dua);
        tv_klikdisini_dua.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_klikdisini_satu:
                Intent kliksatu = new Intent (menu_prosedur.this, webview_pdf_prosedur.class);
                startActivity(kliksatu);
                break;
            case R.id.tv_klikdisini_dua:
                Intent klikdua = new Intent (menu_prosedur.this, menu_materiujian.class);
                startActivity(klikdua);
                break;
        }
    }
}
