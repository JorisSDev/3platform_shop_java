package com.example.kursinis_android.ui;

import static com.example.kursinis_android.helpers.Constants.GET_ALL_PRODUCTS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kursinis_android.R;
import com.example.kursinis_android.helpers.LocalDateDeserializer;
import com.example.kursinis_android.helpers.Rest;
import com.example.kursinis_android.model.Customer;
import com.example.kursinis_android.model.Manager;
import com.example.kursinis_android.model.Product;
import com.example.kursinis_android.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductsActivity extends AppCompatActivity {
    Customer connectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Intent intent = getIntent();
        String userInfo = intent.getStringExtra("userObject");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Gson userGson = gsonBuilder.create();

        connectedUser = userGson.fromJson(userInfo, Customer.class);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String response = Rest.sendGet(GET_ALL_PRODUCTS);
                handler.post(() -> {
                    if(!response.equals("Error")) {
                        Gson productsGson = new Gson();

                        Type productListType = new TypeToken<List<Product>>(){}.getType();
                        List<Product> productList = productsGson.fromJson(response, productListType);

                        ListView productListElement = (ListView) findViewById(R.id.productsListView);

                        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productList);
                        productListElement.setAdapter(adapter);
                        productListElement.setOnItemClickListener((parent, view, position, id) -> {
                            System.out.println(productList.get(position));
                        });
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void viewMyAccount(View view) {
//        Intent intent = new Intent(ProductsActivity.this, MyAccountActivity.class);
//        intent.putExtra("userObject", new Gson().toJson(connectedUser));
//        startActivity(intent);
    }

    public void viewMyPurchases(View view) {
    }
}