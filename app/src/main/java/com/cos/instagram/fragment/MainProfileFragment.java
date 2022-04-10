package com.cos.instagram.fragment;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.cos.instagram.MainActivity;
import com.cos.instagram.PostImgActivity;
import com.cos.instagram.ProfileActivity;
import com.cos.instagram.R;
import com.cos.instagram.SignActivity;
import com.cos.instagram.model.FirebaseID;
import com.cos.instagram.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainProfileFragment extends Fragment implements View.OnClickListener {

    private MainViewModel model;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private User user;

    private TextView profile_id, profile_name_tv, profile_intro_tv;
    private ImageButton btn_home, btn_search, btn_play, btn_shop, btn_profile;
    private ImageButton profile_plus_btn;
    private Button profile_edit_btn;
    private SlidingUpPanelLayout slide_layout;

    private LinearLayout profile_post_btn;

    private Context context;

    private FragmentTransaction transaction;

    private String username;

    private int cnt = 0;

    public static MainProfileFragment newInstance() {
        return new MainProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_profile_fragment, container, false);

        context = container.getContext();
        transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();

        MainHomeFragment homeFragment = new MainHomeFragment();
        MainSearchFragment searchFragment = new MainSearchFragment();
        MainPlayFragment playFragment = new MainPlayFragment();
        MainShopFragment shopFragment = new MainShopFragment();
        MainProfileFragment profileFragment = new MainProfileFragment();

        slide_layout = (SlidingUpPanelLayout) view.findViewById(R.id.slide_layout);

        profile_id = (TextView) view.findViewById(R.id.profile_id);
        profile_name_tv = (TextView) view.findViewById(R.id.profile_name_tv);
        profile_intro_tv = (TextView) view.findViewById(R.id.profile_intro_tv);

        btn_home = (ImageButton) view.findViewById(R.id.btn_home);
        btn_search = (ImageButton) view.findViewById(R.id.btn_search);
        btn_play = (ImageButton) view.findViewById(R.id.btn_play);
        btn_shop = (ImageButton) view.findViewById(R.id.btn_shop);
        btn_profile = (ImageButton) view.findViewById(R.id.btn_profile);

        profile_plus_btn = (ImageButton) view.findViewById(R.id.profile_plus_btn);
        profile_edit_btn = (Button) view.findViewById(R.id.profile_edit_btn);

        profile_post_btn = (LinearLayout) view.findViewById(R.id.profile_post_btn);

        btn_home.setOnClickListener(View -> changeFragment(homeFragment));
        btn_search.setOnClickListener(View -> changeFragment(searchFragment));
        btn_play.setOnClickListener(View -> changeFragment(playFragment));
        btn_shop.setOnClickListener(View -> changeFragment(shopFragment));
        btn_profile.setOnClickListener(View -> changeFragment(profileFragment));

        profile_plus_btn.setOnClickListener(this);
        profile_post_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_plus_btn:
                slide_layout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                break;

            case R.id.profile_post_btn:
                startActivity(new Intent(getActivity(), PostImgActivity.class));
                break;

        }

    }

    // 버튼을 누를 경우 선택한 Fragment 변경
    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        user = model.user;
        profile_id.setText(user.getUsername());
        profile_name_tv.setText(user.getName());
        if(user.getInfo() == null){
            profile_intro_tv.setText("");
        }
        else{
            profile_intro_tv.setText(user.getInfo());
        }
        model.getSelected().observe(getViewLifecycleOwner(), u -> {
            user = u;

        });

        Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
        profileIntent.putExtra("user", user);

        profile_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(profileIntent);
            }
        });

    }


}