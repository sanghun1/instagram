package com.cos.instagram.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.cos.instagram.R;
import com.cos.instagram.SignActivity;
import com.cos.instagram.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignBirthFragment extends Fragment implements View.OnClickListener {

    private SignViewModel model;

    private SignAgreeFragment agreeFragment = new SignAgreeFragment();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private TextView mDate, mAge;
    private DatePicker mDate_input;

    public static Context context;

    private User user;

    private long now;
    private Date year;
    private int nYear;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_birth_fragment, container, false);

        context = container.getContext();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        mDate = (TextView) view.findViewById(R.id.birth_date_tv);
        mAge = (TextView) view.findViewById(R.id.birth_age_tv);

        mDate_input = (DatePicker) view.findViewById(R.id.birth_date_input);

        mDate_input.init(mDate_input.getYear(), mDate_input.getMonth(), mDate_input.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDate.setText(String.format("%d년 %d월 %d일", year,monthOfYear + 1 , dayOfMonth));
                mAge.setText(Integer.toString(((SignActivity)getActivity()).getTime() - year));
            }
        });

        transaction.commit();

        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        user.setBirth(mDate.getText().toString());
        model.select(user);

//        Toast.makeText(context, model.getSelected().getValue().getBirth(), Toast.LENGTH_SHORT).show();

        transaction.replace(R.id.sign_frame, agreeFragment);
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(SignViewModel.class);
        view.findViewById(R.id.birth_next_btn).setOnClickListener(this);

        model.getSelected().observe(getViewLifecycleOwner(), u -> {
            user = u;


        });
    }

//    public int getTime(){
//        now = System.currentTimeMillis();
//        year = new Date(now);
//        nYear = Integer.parseInt(mFormat.format(year));
//        return nYear;
//    }
}
