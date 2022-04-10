package com.cos.instagram;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PostContentActivity extends AppCompatActivity {

    private Button post_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content);

        Matrix sideInversion = new Matrix();
        post_exit = findViewById(R.id.post_exit);



    }
}