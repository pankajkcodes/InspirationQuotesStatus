package com.pankajkcodes.inspirationquotesstatus;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    ArrayList<String> quotes = new ArrayList<>();
    ArrayList<String> author = new ArrayList<>();

    String url = "https://play.google.com/store/apps/details?id=com.simplifiededtech.allhindishayariandquotes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(JsonDataFromAssets()));
            JSONArray jsonArray = jsonObject.getJSONArray("quotes");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject quotesData = jsonArray.getJSONObject(i);
                quotes.add(quotesData.getString("text"));
                author.add(quotesData.getString("author"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Adapter adapter = new Adapter(MainActivity.this, quotes, author);
        recyclerView.setAdapter(adapter);

    }

    private String JsonDataFromAssets() {
        String json;
        try {
            InputStream inputStream = getAssets().open("quotes.json");
            int sizeOfFile = inputStream.available();
            byte[] bufferData = new byte[sizeOfFile];
            inputStream.read(bufferData);
            inputStream.close();
            json = new String(bufferData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
        return json;

    }


    @Override
    public void onBackPressed() {
        //--------> Create a dialog for Show dialog after click backed --->//
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> MainActivity.super.onBackPressed()).setNegativeButton("No", (dialog, which) -> dialog.cancel()).setNeutralButton("More Apps", (dialog, which) -> {

                    //--------> Code for More Apps --->//
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create();
        builder.show();
    }
}