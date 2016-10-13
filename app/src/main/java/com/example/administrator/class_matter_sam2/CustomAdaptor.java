package com.example.administrator.class_matter_sam2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016-10-11.
 */

public class CustomAdaptor extends ArrayAdapter<Article> {
    private Context context;
    private int layoutResorceId;
    private ArrayList<Article> articles;

    public CustomAdaptor(Context context, int layoutResorceId, ArrayList<Article> articles) {
        super(context, layoutResorceId, articles);
        this.context = context;
        this.layoutResorceId = layoutResorceId;
        this.articles = articles;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row==null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResorceId,parent,false);
        }

        TextView tvText1 = (TextView) row.findViewById(R.id.custom_row_textView);
        TextView tvText2 = (TextView) row.findViewById(R.id.custom_row_textView2);

        tvText1.setText(articles.get(position).getName());
        tvText2.setText(articles.get(position).getDate());

        ImageView imageView = (ImageView) row.findViewById(R.id.custom_row_imageView1);

        Log.i("errererer", "getView: " + articles.get(position).getImgName());
        Picasso.with(context)
                .load(articles.get(position).getImgName())
                .placeholder(android.R.drawable.sym_def_app_icon)
                .into(imageView);
//
//
//        try {
//            InputStream is = context.getAssets().open(articles.get(position).getImgName());
//            Drawable d = Drawable.createFromStream(is, null);
//            imageView.setImageDrawable(d);
//
//        }catch (IOException e) {
//            Log.e("ERROR", "ERROR : "+e);
//        }
        return row;
    }
}
