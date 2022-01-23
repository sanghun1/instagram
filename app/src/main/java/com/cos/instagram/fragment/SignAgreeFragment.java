package com.cos.instagram.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cos.instagram.MainActivity;
import com.cos.instagram.R;

public class SignAgreeFragment extends Fragment {

    private SignViewModel mViewModel;

    private Button agree_next_btn;
    private ToggleButton agree_all_check_btn, agree_check_1_btn, agree_check_2_btn, agree_check_3_btn;

    private boolean bool1 = false;
    private boolean bool2 = false;
    private boolean bool3 = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_agree_fragment, container, false);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();


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

                }
                else{
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
                }
                else{
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_check_1_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_next_btn.setEnabled(false);
                    bool1 = false;
                }

                if(bool1 && bool2 && bool3){
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
                }
                else{
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_check_2_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_next_btn.setEnabled(false);
                    bool2 = false;
                }
                if(bool1 && bool2 && bool3){
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
                }
                else{
                    agree_all_check_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_check_3_btn.setBackgroundResource(R.drawable.img_check_off);
                    agree_next_btn.setEnabled(false);
                    bool3 = false;
                }
                if(bool1 && bool2 && bool3){
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
                startActivity(new Intent(getActivity().getApplication(), MainActivity.class));
                getActivity().finish();
            }
        });


        transaction.commit();

        return view;
    }
}
