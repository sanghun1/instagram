package com.cos.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.instagram.fragment.SignBirthFragment;
import com.cos.instagram.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import org.w3c.dom.Text;

public class ProfileBirthActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView birth_tv, dialog_birth;
    private Dialog commit_dialog;
    private DatePicker birth_input;

    private String myBirth;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_birth);

        user = (User) getIntent().getSerializableExtra("user");

        birth_tv = (TextView) findViewById(R.id.profile_birth_tv);
        birth_tv.setText(user.getBirth());

        commit_dialog = new Dialog(ProfileBirthActivity.this);       // Dialog 초기화
        commit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        commit_dialog.setContentView(R.layout.commit_dialog);
        commit_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        birth_input = (DatePicker) findViewById(R.id.profile_birth_input);

        birth_input.init(birth_input.getYear(), birth_input.getMonth(), birth_input.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myBirth = String.format("%d년 %d월 %d일", year,monthOfYear + 1 , dayOfMonth);
                birth_tv.setText(myBirth);

            }
        });

        findViewById(R.id.profile_birth_exit).setOnClickListener(this);
        findViewById(R.id.profile_birth_commit).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_birth_exit:
                finish();
                break;
            case R.id.profile_birth_commit:
                commitDialog();
                break;
        }
    }

    public void commitDialog(){
        commit_dialog.show();
        dialog_birth = commit_dialog.findViewById(R.id.dialog_birth);
        dialog_birth.setText(myBirth);

        commit_dialog.findViewById(R.id.dialog_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit_dialog.dismiss();
            }
        });

        commit_dialog.findViewById(R.id.dialog_commit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileBirthActivity.this, "생일이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                user.setBirth(myBirth);
                Intent birthIntent = new Intent(getBaseContext(), ProfileActivity.class);
                birthIntent.putExtra("user", user);

                startActivity(birthIntent);
                finish();
            }
        });
    }
}