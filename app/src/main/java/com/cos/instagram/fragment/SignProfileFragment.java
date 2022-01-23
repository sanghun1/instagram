package com.cos.instagram.fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cos.instagram.R;
import com.cos.instagram.model.FirebaseID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class SignProfileFragment extends Fragment implements View.OnClickListener {

    public static String TAG = "SignProfileFragment : ";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private SignViewModel mViewModel;

    private SignBirthFragment birthFragment = new SignBirthFragment();
    private SignAgreeFragment agreeFragment = new SignAgreeFragment();

    private EditText mEmail, mName, mUsername, mPassword;

    private Context context;

    public static SignProfileFragment newInstance() {
        return new SignProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_profile_fragment, container, false);

        context = container.getContext();

        mEmail = view.findViewById(R.id.sign_email);
        mName = view.findViewById(R.id.sign_name);
        mUsername = view.findViewById(R.id.sign_username);
        mPassword = view.findViewById(R.id.sign_pw);

        view.findViewById(R.id.sign_next_btn).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (!mEmail.getText().toString().equals("") || !mPassword.getText().toString().equals("") || !mUsername.getText().toString().equals("") || !mName.getText().toString().equals("")) {
            mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put(FirebaseID.documentId, user.getUid());
                                    userMap.put(FirebaseID.email, mEmail.getText().toString());
                                    userMap.put(FirebaseID.name, mName.getText().toString());
                                    userMap.put(FirebaseID.username, mUsername.getText().toString());
                                    userMap.put(FirebaseID.password, mPassword.getText().toString());
                                    mStore.collection(FirebaseID.user).document(user.getUid()).set(userMap, SetOptions.merge());

                                    Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show();
                                    transaction.replace(R.id.sign_frame, birthFragment);
                                    transaction.commit();
                                }
                            }
                            else {
                                Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {
            Toast.makeText(context, "에러", Toast.LENGTH_SHORT).show();
        }

    }
}