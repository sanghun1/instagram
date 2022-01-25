package com.cos.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.cos.instagram.fragment.MainHomeFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainHomeFragment mainHomeFragment = new MainHomeFragment();

        transaction.replace(R.id.main_frame, mainHomeFragment);
        transaction.commit();
    }
}