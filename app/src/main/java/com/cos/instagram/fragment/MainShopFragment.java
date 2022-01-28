package com.cos.instagram.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cos.instagram.R;

public class MainShopFragment extends Fragment {

    private MainViewModel mViewModel;

    private ImageButton btn_home, btn_search, btn_play, btn_shop, btn_profile;

    public static MainShopFragment newInstance() {
        return new MainShopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_shop_fragment, container, false);

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

        btn_home.setOnClickListener(View -> changeFragment(homeFragment));
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

}