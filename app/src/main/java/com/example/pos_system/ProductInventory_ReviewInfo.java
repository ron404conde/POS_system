package com.example.pos_system;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProductInventory_ReviewInfo extends AppCompatActivity {

    ActionBar actionBar;
    TextView text_ProductID, text_ProductName, text_ProductStocks, text_ProductPrice, text_ProductDiscount, text_ProductReorder;
    Button button_Back, button_Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_inventory_reviewinfo);

        setActionBar_InventoryReviewInfo();
        initialize_AllVariables();
        get_ProductInfo_ForReview();
        button_BackCLick();
    }

    private void initialize_AllVariables() {
        text_ProductID = findViewById(R.id.input_ProductID);
        text_ProductName = findViewById(R.id.input_ProductName);
        text_ProductStocks = findViewById(R.id.input_ProductStocks);
        text_ProductPrice = findViewById(R.id.input_ProductPrice);
        text_ProductDiscount = findViewById(R.id.input_ProductDiscount);
        text_ProductReorder = findViewById(R.id.input_ProductReorder);

        button_Back = findViewById(R.id.button_Back);
        button_Save = findViewById(R.id.button_Save);
    }

    private void setActionBar_InventoryReviewInfo() {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.header_InventoryReviewInfo);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back_button);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099FF")));
        }
    }

    private void get_ProductInfo_ForReview() {
        Intent i = getIntent();
        text_ProductID.setText(i.getStringExtra("i_productID"));
        text_ProductName.setText(i.getStringExtra("i_productName"));
        text_ProductStocks.setText(i.getStringExtra("i_productStocks"));
        text_ProductPrice.setText(i.getStringExtra("i_productPrice"));
        text_ProductDiscount.setText(i.getStringExtra("i_productDiscount"));
        text_ProductReorder.setText(i.getStringExtra("i_productReorder"));
    }

    private void button_BackCLick() {
        // button_Back.setOnClickListener(view -> startActivity(new Intent(ProductInventory_ReviewInfo.this, ProductInventory_FillUp.class)));

        button_Back.setOnClickListener(view -> super.onBackPressed());
    }


    public boolean onOptionsItemSelected(MenuItem item){
//        Intent myIntent = new Intent(getApplicationContext(), ProductInventory_FillUp.class);
//        startActivityForResult(myIntent, 0);

        super.onBackPressed();
        return true;
    }
}