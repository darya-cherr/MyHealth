package com.example.myhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView menuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        menuBtn = findViewById(R.id.menuBtn);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    public void navigationMenuItemClick(MenuItem item) {
        final int id = item.getItemId();
        switch (id){
            case R.id.menu_about_me:
                Intent intent = new Intent(MainActivity.this, AboutDevActivity.class);
                startActivity(intent);
                return;
            case R.id.menu_rules:
                Intent intent2 = new Intent(MainActivity.this, RulesActivity.class);
                startActivity(intent2);
                return;
        }
    }
}