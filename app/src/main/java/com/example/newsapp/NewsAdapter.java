package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentview = convertView;
        if (currentview == null){
            currentview = LayoutInflater.from(getContext()).inflate(R.layout.item_list,parent,false);
        }
        TextView text1 = currentview.findViewById(R.id.pillarName);
        text1.setText(getItem(position).getPillarname());
        TextView text2 = currentview.findViewById(R.id.webtitle);
        text2.setText(getItem(position).getWebTitle());
        TextView text3 = currentview.findViewById(R.id.sectionName);
        text3.setText(getItem(position).getSectionName());
        TextView text4 = currentview.findViewById(R.id.date);
        text4.setText(getItem(position).getWebPublicationDate());
        TextView text5 = currentview.findViewById(R.id.author);
        if (getItem(position).hasAuthor()){
            text5.setText(getItem(position).getAuthor());
            text5.setVisibility(View.VISIBLE);
        }
        else {
            text5.setVisibility(View.GONE);
        }
        return currentview;
    }

    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<News> news) {
        super(context, 0, news);
    }
}
