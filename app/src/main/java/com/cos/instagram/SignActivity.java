package com.cos.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import com.cos.instagram.fragment.SignAgreeFragment;
import com.cos.instagram.fragment.SignBirthFragment;
import com.cos.instagram.fragment.SignProfileFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignActivity extends AppCompatActivity {

    private SignProfileFragment signProfile;
    private SignBirthFragment signBirth;
    private SignAgreeFragment signAgree;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        context = this;

        signProfile = new SignProfileFragment();
        signBirth = new SignBirthFragment();
        signAgree = new SignAgreeFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.sign_frame, signProfile, SignProfileFragment.TAG).commit();
    }

    public int getTime() {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy");
        long now = System.currentTimeMillis();
        Date year = new Date(now);
        int nYear = Integer.parseInt(mFormat.format(year));

        return nYear;
    }
}