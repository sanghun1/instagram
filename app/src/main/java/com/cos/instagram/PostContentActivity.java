package com.cos.instagram;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cos.instagram.fragment.MainHomeFragment;
import com.cos.instagram.model.FirebaseID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class PostContentActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private EditText post_content;
    private Button post_exit;

    private String username;

    public static int postnum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content);

        post_content = findViewById(R.id.post_content);

        if(mAuth.getCurrentUser() != null){
            mStore.collection(FirebaseID.user).document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult() != null){
                                username = (String) task.getResult().getData().get(FirebaseID.username);
                            }
                        }
                    });
        }

        findViewById(R.id.post_content_commit).setOnClickListener(this);
        findViewById(R.id.post_content_exit).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.post_content_commit:
                if(mAuth.getCurrentUser() != null){
                    String postId = mStore.collection(FirebaseID.post).document().getId();
                    Map<String, Object> data = new HashMap<>();
                    data.put(FirebaseID.postId, mAuth.getCurrentUser().getUid());
                    data.put(FirebaseID.username, username);
                    data.put(FirebaseID.contents, post_content.getText().toString());
                    data.put(FirebaseID.postdate, FieldValue.serverTimestamp());
                    mStore.collection(FirebaseID.post).document(postId).set(data, SetOptions.merge());
//                    startActivity(new Intent(this, MainActivity.class));
                    postnum = 1;
                    startActivity(new Intent(this, MainActivity.class));

                    finish();
                }
                break;
            case R.id.post_content_exit:
                finish();
                break;
        }
    }
}