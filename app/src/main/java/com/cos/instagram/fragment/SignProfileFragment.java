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
import com.cos.instagram.model.User;
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
    public static Context context_sign;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private SignViewModel model;

    private SignBirthFragment birthFragment = new SignBirthFragment();
    private SignAgreeFragment agreeFragment = new SignAgreeFragment();

    private EditText mName, mUsername;
    public EditText mEmail_edit, mPassword_edit;

    private Context context;


    public Map<String, Object> userMap = new HashMap<>();

    public static SignProfileFragment newInstance() {
        return new SignProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_profile_fragment, container, false);

        context = container.getContext();

        mEmail_edit = view.findViewById(R.id.sign_email);
        mName = view.findViewById(R.id.sign_name);
        mUsername = view.findViewById(R.id.sign_username);
        mPassword_edit = view.findViewById(R.id.sign_pw);


        return view;
    }

    @Override
    public void onClick(View view) {

        if (!mEmail_edit.getText().toString().equals("") || !mPassword_edit.getText().toString().equals("") || !mUsername.getText().toString().equals("") || !mName.getText().toString().equals("")) {

        } else {
            Toast.makeText(context, "에러", Toast.LENGTH_SHORT).show();
        }
        User inputUser = new User();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        inputUser.setEmail(mEmail_edit.getText().toString());
        inputUser.setName(mName.getText().toString());
        inputUser.setUsername(mUsername.getText().toString());
        inputUser.setPassword(mPassword_edit.getText().toString());
        model.select(inputUser);

//        Toast.makeText(context, model.getSelected().getValue().toString(), Toast.LENGTH_SHORT).show();

        transaction.replace(R.id.sign_frame, birthFragment);
        transaction.commit();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(SignViewModel.class);
        view.findViewById(R.id.sign_next_btn).setOnClickListener(this);
    }
}