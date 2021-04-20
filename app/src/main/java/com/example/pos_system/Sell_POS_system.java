package com.example.pos_system;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Sell_POS_system extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_pos_system);

        bottomNavigation_menuItemClick();
        setActionBar_DashBoard();
    }

    private void setActionBar_DashBoard() {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.header_sell_pos);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_icon);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099FF")));
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void bottomNavigation_menuItemClick() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.sell);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), Dashboard_POS_system.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.sell:
                    return true;
            }
            return false;
        });
    }
}