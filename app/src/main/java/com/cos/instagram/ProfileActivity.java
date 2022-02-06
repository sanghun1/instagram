package com.cos.instagram;

import static com.cos.instagram.model.FirebaseID.documentId;
import static com.cos.instagram.model.FirebaseID.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.instagram.fragment.MainProfileFragment;
import com.cos.instagram.model.FirebaseID;
import com.cos.instagram.model.User;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private LinearLayout profile_edit_birth, profile_layout;
    private TextInputEditText edit_name, edit_username, edit_number, edit_info;
    private TextView edit_birth_tv;
    private SlidingUpPanelLayout slide_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user = (User) getIntent().getSerializableExtra("user");

        Intent profileIntent = new Intent(getBaseContext(), ProfileBirthActivity.class);
        profileIntent.putExtra("user", user);

        Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);
        mainIntent.putExtra("user", user);

        edit_birth_tv = (TextView) findViewById(R.id.profile_edit_birth_tv);

        edit_name = (TextInputEditText) findViewById(R.id.profile_edit_name);
        edit_username = (TextInputEditText) findViewById(R.id.profile_edit_username);
        edit_number = (TextInputEditText) findViewById(R.id.profile_edit_number);
        edit_info = (TextInputEditText) findViewById(R.id.profile_edit_info);

        slide_layout = (SlidingUpPanelLayout) findViewById(R.id.profile_slide_layout);

        edit_name.setText(user.getName());
        edit_username.setText(user.getUsername());
        edit_birth_tv.setText(user.getBirth());

        if(user.getNumber() == null){
            edit_number.setText("");
        }
        else{
            edit_number.setText(user.getNumber());
        }

        if(user.getInfo() == null){
            edit_info.setText("");
        }
        else{
            edit_info.setText(user.getInfo());
        }

        findViewById(R.id.profile_edit_exit).setOnClickListener(this);
        findViewById(R.id.profile_layout).setOnClickListener(this);
        findViewById(R.id.profile_img_change).setOnClickListener(this);

        findViewById(R.id.profile_edit_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ProfileActivity.this, user.getDocumentId(), Toast.LENGTH_SHORT).show();
                DocumentReference doc = mStore.collection(FirebaseID.user).document(user.getDocumentId());

                user.setUsername(edit_username.getText().toString());
                user.setName(edit_name.getText().toString());
                user.setNumber(edit_number.getText().toString());
                user.setInfo(edit_info.getText().toString());
                user.setBirth(edit_birth_tv.getText().toString());

                mStore.collection(FirebaseID.user).document(user.getDocumentId())
                        .update(
                                FirebaseID.username, edit_username.getText().toString(),
                                FirebaseID.name, edit_name.getText().toString(),
                                FirebaseID.number, edit_number.getText().toString(),
                                FirebaseID.info, edit_info.getText().toString(),
                                FirebaseID.birth, edit_birth_tv.getText().toString()
                        );
                ((MainActivity)MainActivity.mainContext).moveNum = 1;;
                finish();
                startActivity(mainIntent);
            }
        });

        profile_edit_birth = (LinearLayout) findViewById(R.id.profile_edit_birth);
        profile_edit_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ProfileActivity.this, documentId, Toast.LENGTH_SHORT).show();
                startActivity(profileIntent);
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_edit_exit:
                finish();
                break;
            case R.id.profile_layout:
                slide_layout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                break;
            case R.id.profile_img_change:
                startActivity(new Intent(getBaseContext(), ProfileImgActivity.class));
                break;

        }
    }
}