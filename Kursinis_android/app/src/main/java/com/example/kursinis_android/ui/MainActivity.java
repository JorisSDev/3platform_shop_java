package com.example.kursinis_android.ui;

import static com.example.kursinis_android.helpers.Constants.VALIDATE_USER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.example.kursinis_android.R;
import com.example.kursinis_android.helpers.LocalDateDeserializer;
import com.example.kursinis_android.helpers.Rest;
import com.example.kursinis_android.model.Customer;
import com.example.kursinis_android.model.Manager;
import com.example.kursinis_android.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    public void validateUser(View view) {
//        TextView login = findViewById(R.id.loginField);
//        TextView password = findViewById(R.id.passwordField);
//
//        //String info = "{\"login\": \"" + login.getText().toString() + "\", \"password\": \"" + password.getText().toString() + "\"}";]
//
//        Gson gson = new Gson();
//        JsonObject jsonObject = new JsonObject();
//
//        jsonObject.addProperty("login", login.getText().toString());
//        jsonObject.addProperty("password", password.getText().toString());
//
//        String info = gson.toJson(jsonObject);
//
//        Executor executor = Executors.newSingleThreadExecutor();
//        Handler handler = new Handler(Looper.getMainLooper());
//
//        executor.execute(() -> {
//            try {
//                String response = Rest.sendPost(VALIDATE_USER, info);
//                handler.post(() -> {
//                    if(!response.equals("Error") && !response.equals("")){
//                        System.out.println(response);
//                        //viskas ok einam į kitą langą
//                        //reikia pasiparsinti koks useris?
//
//                        Gson userGson = new Gson();
//                        User connectedUser = userGson.fromJson(response, User.class);
//                        if(connectedUser.getClass() == Manager.class){
//
//                        }
//                        else{
//                            Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
//                            intent.putExtra("userObject", response);
//                            startActivity(intent);
//                        }
//                    }
//                    else{
//                        System.out.println("Error");
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        });
//    }
    public void validateUser(View view) {
        TextView login = findViewById(R.id.loginField);
        TextView password = findViewById(R.id.passwordField);

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("login", login.getText().toString());
        jsonObject.addProperty("password", password.getText().toString());

        String info = gson.toJson(jsonObject);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String response = Rest.sendPost(VALIDATE_USER, info);
                handler.post(() -> {
                    if(!response.equals("Error") && !response.equals("")){
                        System.out.println(response);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
                        Gson userGson = gsonBuilder.create();

                        User connectedUser = userGson.fromJson(response, Customer.class);

                        System.out.println(connectedUser.toString());

                        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
                        intent.putExtra("userObject", response);
                        startActivity(intent);
                    }
                    else{
                        System.out.println("Error");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}