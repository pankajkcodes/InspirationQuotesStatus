package com.pankajkcodes.inspirationquotesstatus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String text,author;

    ApiInterfaceEndPoint apiInterface;

    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataApi();

    }

    private void initDataApi() {
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterfaceEndPoint.class);

        apiInterface.getQuotes().enqueue(new Callback<List<QuotesData>>() {
            @Override
            public void onResponse(Call<List<QuotesData>> call, Response<List<QuotesData>> response) {
                if (response.body().size()>0){
                    ArrayList<QuotesData> list = (ArrayList<QuotesData>) response.body();
                    // Initialize first recycler view
                    recyclerView = findViewById(R.id.recyclerView);
                    list.add(new QuotesData());
                    Adapter adapter = new Adapter(MainActivity.this,list);
                    recyclerView.setAdapter(adapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                }else {
                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<QuotesData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("error",t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        //--------> Create a dialog for Show dialog after click backed --->//
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setNeutralButton("More Apps", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //--------> Code for More Apps --->//
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create();
        builder.show();
    }
}