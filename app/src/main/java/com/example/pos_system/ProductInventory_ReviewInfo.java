package com.example.pos_system;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

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

        save_ProductInventory_Information();
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
        button_Back.setOnClickListener(view -> super.onBackPressed());
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }



    private void save_ProductInventory_Information() {
        button_Save.setOnClickListener(view -> {
            try {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[6];
                    field[0] = "product_id";
                    field[1] = "product_name";
                    field[2] = "product_stocks";
                    field[3] = "product_price";
                    field[4] = "product_discount";
                    field[5] = "product_reorder";

                    String[] data = new String[6];
                    data[0] = String.valueOf(text_ProductID.getText());
                    data[1] = String.valueOf(text_ProductName.getText());
                    data[2] = String.valueOf(text_ProductStocks.getText());
                    data[3] = String.valueOf(text_ProductPrice.getText());
                    data[4] = String.valueOf(text_ProductDiscount.getText());
                    data[5] = String.valueOf(text_ProductReorder.getText());

                    PutData putData = new PutData("http://192.168.43.32/pos_system/saveproductinventory.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();

                            if (result.equals("Save success")) {
                                Toast.makeText(this, "Product Information save success!", Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception exception) {
                Toast.makeText(ProductInventory_ReviewInfo.this, "Error: " + exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}