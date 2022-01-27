package com.cos.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cos.instagram.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private EditText email, password;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_id);
        password = findViewById(R.id.login_pw);

        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.login_sign_button).setOnClickListener(this);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser mUser = mAuth.getCurrentUser();
//        if(mUser != null){
//            Toast.makeText(this, mUser.getEmail() + "로 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, MainActivity.class));
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_sign_button:
                startActivity(new Intent(this, SignActivity.class));
                break;

            case R.id.login_btn:

                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser mUser = mAuth.getCurrentUser();
                                    if (mUser != null) {
//                                        Toast.makeText(LoginActivity.this, "로그인 성공 : " + mUser.getUid(), Toast.LENGTH_SHORT).show();

//                                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                                        Bundle bundle = mainIntent.getExtras();
//
//                                        String signEmail = bundle.getString("email");
//                                        String signUsername = bundle.getString("username");
//                                        String signName = bundle.getString("name");
//                                        String signBirth = bundle.getString("birth");


                                        DocumentReference docRef = mStore.collection("user").document(mUser.getUid());
                                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                User user = documentSnapshot.toObject(User.class);

                                                Intent loginIntent = new Intent(getBaseContext(), MainActivity.class);

                                                loginIntent.putExtra("user", user);


//                                                Toast.makeText(LoginActivity.this, user.getUsername(), Toast.LENGTH_LONG).show();

                                                startActivity(loginIntent);

                                            }
                                        });


//                                        intent.putExtra("email", email);
//                                        intent.putExtra("username", username);
//                                        intent.putExtra("name", name);
//                                        intent.putExtra("birth", birth);


                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "로그인 에러 ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }
}