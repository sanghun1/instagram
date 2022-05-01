package com.cos.instagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cos.instagram.adapter.PostContentAdapter;
import com.cos.instagram.fragment.MainHomeFragment;
import com.cos.instagram.fragment.MainProfileFragment;
import com.cos.instagram.fragment.MainViewModel;
import com.cos.instagram.fragment.SignViewModel;
import com.cos.instagram.model.FirebaseID;
import com.cos.instagram.model.Post;
import com.cos.instagram.model.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity1";

    private User user;
    private FragmentTransaction transaction;
    private MainViewModel model;

    public static Context mainContext;
    public int moveNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainContext = this;

        PostContentActivity postContent = new PostContentActivity();

        user = (User) getIntent().getSerializableExtra("user");

        model = new ViewModelProvider(this).get(MainViewModel.class);
        model.select(user);

        transaction = getSupportFragmentManager().beginTransaction();
        MainHomeFragment mainHomeFragment = new MainHomeFragment();
        MainProfileFragment mainProfileFragment = new MainProfileFragment();

        if(moveNum < 1){
            transaction.replace(R.id.main_frame, mainHomeFragment);
        }
        else if(postContent.postnum == 1){
            transaction.replace(R.id.main_frame, mainHomeFragment);
        }
        else{
            transaction.replace(R.id.main_frame, mainProfileFragment);
        }
        transaction.commit();



    }


}