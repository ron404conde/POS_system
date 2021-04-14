package com.example.pos_system;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductInventory_FillUp extends AppCompatActivity {

    ActionBar actionBar;
    Spinner spinner_category;
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayAdapter<String> categoryAdapter;
    RequestQueue requestQueue;

    Button button_Next;
    TextView input_ProductID, input_ProductName, input_ProductStocks, input_ProductPrice, input_ProductDiscount, input_ProductReorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_inventory_fill_up);

        setActionBar_InventoryFillUp(); // Header BackButton
        get_ProductCategory();

        button_Next = findViewById(R.id.button_Next);
        input_ProductID = findViewById(R.id.input_ProductID);
        button_NextClick();
    }

    private void setActionBar_InventoryFillUp() {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.header_InventoryFillUp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back_button);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099FF")));
        }
    }

    private void get_ProductCategory() {
        requestQueue = Volley.newRequestQueue(this);
        spinner_category = findViewById(R.id.input_ProductCategory);
        String url = "http://192.168.43.32/pos_system/get_category.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("tblproductcategory");

                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String category_name = jsonObject.optString("category_name");
                        categoryList.add(category_name);
                        categoryAdapter = new ArrayAdapter<>(ProductInventory_FillUp.this, android.R.layout.simple_spinner_item, categoryList);
                        spinner_category.setAdapter(categoryAdapter);
                    }
                } catch (JSONException e) {
                    Toast.makeText(ProductInventory_FillUp.this, "Error 01: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> Toast.makeText(ProductInventory_FillUp.this, "Error 02: " + error.toString(), Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);
    }

    private void button_NextClick() {
        button_Next.setOnClickListener(view -> scan_QRandBarcode());
    }

    private void scan_QRandBarcode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning code . . . . .");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Product ID");

                input_ProductID.setText(result.getContents()); // setText for ProductID

                builder.setPositiveButton("Scan again", (dialogInterface, i) -> {
                    scan_QRandBarcode();
                }).setNegativeButton("Finish", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(this, "No result", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void check_inputNull() {
        // TextView input_ProductID, input_ProductName, input_ProductStocks, input_ProductPrice, input_ProductDiscount, input_ProductReorder;

    }

    private void initialize_inputVariables() {

    }
}