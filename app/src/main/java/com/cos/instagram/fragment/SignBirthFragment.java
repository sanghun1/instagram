package com.cos.instagram.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cos.instagram.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignBirthFragment extends Fragment implements View.OnClickListener {

    private SignViewModel mViewModel;

    private SignAgreeFragment agreeFragment = new SignAgreeFragment();

    private TextView mDate, mAge;
    private DatePicker mDate_input;

    private long now;
    private Date year;
    private int nYear;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_birth_fragment, container, false);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        mDate = (TextView) view.findViewById(R.id.birth_date_tv);
        mAge = (TextView) view.findViewById(R.id.birth_age_tv);

        mDate_input = (DatePicker) view.findViewById(R.id.birth_date_input);

        mDate_input.init(mDate_input.getYear(), mDate_input.getMonth(), mDate_input.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDate.setText(String.format("%d년 %d월 %d일", year,monthOfYear + 1 , dayOfMonth));
                mAge.setText(Integer.toString(getTime() - year));
            }
        });

        view.findViewById(R.id.birth_next_btn).setOnClickListener(this);

        transaction.commit();

        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.sign_frame, agreeFragment);
        transaction.commit();
    }

    private int getTime(){
        now = System.currentTimeMillis();
        year = new Date(now);
        nYear = Integer.parseInt(mFormat.format(year));
        return nYear;
    }
}
