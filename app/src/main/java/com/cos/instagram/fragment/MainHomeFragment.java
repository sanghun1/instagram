package com.cos.instagram.fragment;

import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cos.instagram.MainActivity;
import com.cos.instagram.R;
import com.cos.instagram.model.User;

public class MainHomeFragment extends Fragment {

    private MainViewModel model;

    private ImageButton btn_home, btn_search, btn_play, btn_shop, btn_profile;

    private static User user;

    private Context context;

    public static MainHomeFragment newInstance() {
        return new MainHomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_home_fragment, container, false);

        context = container.getContext();

        MainHomeFragment homeFragment = new MainHomeFragment();
        MainSearchFragment searchFragment = new MainSearchFragment();
        MainPlayFragment playFragment = new MainPlayFragment();
        MainShopFragment shopFragment = new MainShopFragment();
        MainProfileFragment profileFragment = new MainProfileFragment();

        btn_home = (ImageButton) view.findViewById(R.id.btn_home);
        btn_search = (ImageButton) view.findViewById(R.id.btn_search);
        btn_play = (ImageButton) view.findViewById(R.id.btn_play);
        btn_shop = (ImageButton) view.findViewById(R.id.btn_shop);
        btn_profile = (ImageButton) view.findViewById(R.id.btn_profile);

        view.findViewById(R.id.home_plus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "게시물 버튼 눌림", Toast.LENGTH_LONG).show();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, user.getUsername(), Toast.LENGTH_LONG).show();
            }
        });
//        btn_home.setOnClickListener(View -> changeFragment(homeFragment));
        btn_search.setOnClickListener(View -> changeFragment(searchFragment));
        btn_play.setOnClickListener(View -> changeFragment(playFragment));
        btn_shop.setOnClickListener(View -> changeFragment(shopFragment));
        btn_profile.setOnClickListener(View -> changeFragment(profileFragment));

        return view;
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        model.getSelected().observe(getViewLifecycleOwner(), u -> {
            user = u;
        });
    }

}