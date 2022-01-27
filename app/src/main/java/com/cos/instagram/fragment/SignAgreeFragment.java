package com.cos.instagram.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.cos.instagram.LoginActivity;
import com.cos.instagram.MainActivity;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignAgreeFragment extends Fragment {

    public static String TAG = "SignAgreeFragment : ";

    private SignViewModel model;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private Button agree_next_btn;
    private ToggleButton agree_all_check_btn, agree_check_1_btn, agree_check_2_btn, agree_check_3_btn;

    private boolean bool1 = false;
    private boolean bool2 = false;
    private boolean bool3 = false;

    private User user;

    private Context context;

    private long now = System.currentTimeMillis();

    private Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_agree_fragment, container, false);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        context = container.getContext();

        agree_next_btn = (Button) view.findViewById(R.id.agree_next_btn);
        agree_all_check_btn = (ToggleButton) view.findViewById(R.id.agree_all_check_btn);
        agree_check_1_btn = (ToggleButton) view.findViewById(R.id.agree_check_1_btn);
        agree_check_2_btn = (ToggleButton) view.findViewById(R.id.agree_check_2_btn);
        agree_check_3_btn = (ToggleButton) view.findViewById(R.id.agree_check_3_btn);

        agree_all_check_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_on);
                    agree_check_1_btn.setBackgroundResource(R.drawable.img_check_on);
                    agree_check_2_btn.setBackgroundResource(R.drawable.img_check_on);
                    agree_check_3_btn.setBackgroundResource(R.drawable.img_check_on);
                    agree_next_btn.setEnabled(true);
                    bool1 = true;
                    bool2 = true;
                    bool3 = true;

                } else {
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_check_1_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_check_2_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_check_3_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_next_btn.setEnabled(false);
                    bool1 = false;
                    bool2 = false;
                    bool3 = false;
                }
            }
        });

        agree_check_1_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    agree_check_1_btn.setBackgroundResource(R.drawable.img_check_on);
                    bool1 = true;
                } else {
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_check_1_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_next_btn.setEnabled(false);
                    bool1 = false;
                }

                if (bool1 && bool2 && bool3) {
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_on);
                    agree_next_btn.setEnabled(true);
                }
            }
        });

        agree_check_2_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    agree_check_2_btn.setBackgroundResource(R.drawable.img_check_on);
                    bool2 = true;
                } else {
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_check_2_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_next_btn.setEnabled(false);
                    bool2 = false;
                }
                if (bool1 && bool2 && bool3) {
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_on);
                    agree_next_btn.setEnabled(true);
                }
            }
        });

        agree_check_3_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    agree_check_3_btn.setBackgroundResource(R.drawable.img_check_on);
                    bool3 = true;
                } else {
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_check_3_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_next_btn.setEnabled(false);
                    bool3 = false;
                }
                if (bool1 && bool2 && bool3) {
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_on);
                    agree_next_btn.setEnabled(true);
                }
            }
        });

//        if(bool1 && bool2 && bool3){
//            agree_all_check_btn.setBackgroundResource(R.drawable.check_on);
//            agree_next_btn.setEnabled(true);
//        }


        agree_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                            .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    FirebaseUser fbUser = mAuth.getCurrentUser();
                                    if (task.isSuccessful()) {
                                        Map<String, Object> userMap = new HashMap<>();
                                        userMap.put(FirebaseID.documentId, fbUser.getUid());
                                        userMap.put(FirebaseID.username, user.getUsername());
                                        userMap.put(FirebaseID.email, user.getEmail());
                                        userMap.put(FirebaseID.name, user.getName());
                                        userMap.put(FirebaseID.password, user.getPassword());
                                        userMap.put(FirebaseID.birth, user.getBirth());
                                        userMap.put(FirebaseID.date, sdf.format(date));
                                        mStore.collection(FirebaseID.user).document(fbUser.getUid()).set(userMap, SetOptions.merge());


                                        startActivity(new Intent(getActivity().getBaseContext(), LoginActivity.class));
                                        getActivity().finish();
//                                        Toast.makeText(context, user.getUsername(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                }



            }
        });


        transaction.commit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(SignViewModel.class);

        model.getSelected().observe(getViewLifecycleOwner(), u -> {
            user = u;
        });
    }
}
