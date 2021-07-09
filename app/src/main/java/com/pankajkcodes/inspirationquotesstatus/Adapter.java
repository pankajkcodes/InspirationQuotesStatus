package com.pankajkcodes.inspirationquotesstatus;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    Context context;
    ArrayList<String> quotes ;
    ArrayList<String> author ;

    public Adapter(Context context, ArrayList<String> quotes, ArrayList<String> author) {
        this.context = context;
        this.quotes = quotes;
        this.author = author;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(quotes.get(position));
        holder.author.setText("Author : "+author.get(position));
        holder.copyBtn.setOnClickListener((v)->{
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text",holder.text.getText()+"\n"+
                    holder.author.getText());
            if (clipboardManager != null){
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
            }
        });
        holder.shareBtn.setOnClickListener((v)->{
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,holder.text.getText()+"\n"+
                    holder.author.getText());
            context.startActivity(Intent.createChooser(intent,"Share to"));
        });

    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        TextView author;
        Button shareBtn,copyBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.quotes);
            author = itemView.findViewById(R.id.author);
            shareBtn = itemView.findViewById(R.id.shareBtn);
            copyBtn = itemView.findViewById(R.id.copyBtn);
        }
    }
}