package com.cos.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.cos.instagram.fragment.SignAgreeFragment;
import com.cos.instagram.fragment.SignBirthFragment;
import com.cos.instagram.fragment.SignProfileFragment;

public class SignActivity extends AppCompatActivity {

    private SignProfileFragment signProfile;
    private SignBirthFragment signBirth;
    private SignAgreeFragment signAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        signProfile = new SignProfileFragment();
        signBirth = new SignBirthFragment();
        signAgree = new SignAgreeFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.sign_frame, signProfile, SignProfileFragment.TAG).commit();
    }
}