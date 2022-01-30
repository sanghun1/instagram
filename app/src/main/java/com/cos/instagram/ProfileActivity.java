package com.cos.instagram;

import static com.cos.instagram.model.FirebaseID.documentId;
import static com.cos.instagram.model.FirebaseID.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.instagram.model.FirebaseID;
import com.cos.instagram.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private LinearLayout profile_edit_birth;
    private TextInputEditText edit_name, edit_username, edit_number, edit_info;
    private TextView edit_birth_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        Intent birthIntent = new Intent(getBaseContext(), ProfileBirthActivity.class);
        birthIntent.putExtra("user", user);


        edit_birth_tv = (TextView) findViewById(R.id.profile_edit_birth_tv);

        edit_name = (TextInputEditText) findViewById(R.id.profile_edit_name);
        edit_username = (TextInputEditText) findViewById(R.id.profile_edit_username);
        edit_number = (TextInputEditText) findViewById(R.id.profile_edit_number);
        edit_info = (TextInputEditText) findViewById(R.id.profile_edit_info);

        edit_birth_tv.setText(user.getDocumentId());
        edit_name.setText(user.getName());
        edit_username.setText(user.getUsername());

        findViewById(R.id.profile_edit_exit).setOnClickListener(this);
        findViewById(R.id.profile_edit_commit).setOnClickListener(this);

        findViewById(R.id.profile_edit_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, user.getDocumentId(), Toast.LENGTH_SHORT).show();
                Map<String, Object> userMap = new HashMap<>();
                userMap.put(FirebaseID.username, edit_username.getText());
                userMap.put(FirebaseID.name, edit_name.getText());
                userMap.put(FirebaseID.number, edit_number.getText());
                userMap.put(FirebaseID.birth, edit_birth_tv.getText());

                user.setUsername(edit_username.getText().toString());
                user.setName(edit_name.getText().toString());
                user.setNumber(edit_number.getText().toString());
                user.setBirth(edit_birth_tv.getText().toString());

                Toast.makeText(ProfileActivity.this, user.getUsername(), Toast.LENGTH_SHORT).show();

                mStore.collection(FirebaseID.user).document(user.getDocumentId()).update(userMap);

//                finish();
            }
        });

        profile_edit_birth = (LinearLayout) findViewById(R.id.profile_edit_birth);
        profile_edit_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ProfileActivity.this, documentId, Toast.LENGTH_SHORT).show();
//                startActivity(birthIntent);
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_edit_exit:
                finish();
                break;
            case R.id.profile_edit_birth:
//                startActivity(birthIntent);
                break;
        }
    }
}