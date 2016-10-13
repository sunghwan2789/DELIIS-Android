package com.example.administrator.class_matter_sam2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageViewActivity extends AppCompatActivity {
    PhotoViewAttacher mAttacher;
    private ImageView image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        image_view = (ImageView)findViewById(R.id.image_view);

        Picasso.with(this)
                .load(getIntent().getExtras().getString("image"))
                .into((ImageView)findViewById(R.id.image_view));
        mAttacher=new PhotoViewAttacher(image_view);
    }


}
