package com.fluvina.hummnew.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fluvina.hummnew.R;

public class FullImageViewActivity extends AppCompatActivity {
    Context context;

    ImageView ivImage, ivClose;
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        context = this;
        ivImage = findViewById(R.id.ivImage);
        ivClose = findViewById(R.id.ivClose);

        loadBundle();
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .error(R.drawable.loading_gif)
                .placeholder(R.drawable.loading_gif)
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> glideAnimation) {
                        ivImage.setImageBitmap(resource);
                    }
                });

//        Glide.with(context).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                ivImage.setImageBitmap(resource);
//            }
//        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private void loadBundle() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra("imageUrl")) {
                    imageUrl = intent.getStringExtra("imageUrl");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}