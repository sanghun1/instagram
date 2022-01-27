package com.cos.instagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cos.instagram.fragment.MainHomeFragment;
import com.cos.instagram.fragment.MainViewModel;
import com.cos.instagram.fragment.SignViewModel;
import com.cos.instagram.model.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity1";

    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        model = new ViewModelProvider(this).get(MainViewModel.class);
        model.select(user);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainHomeFragment mainHomeFragment = new MainHomeFragment();

        transaction.replace(R.id.main_frame, mainHomeFragment);
        transaction.commit();
    }


}