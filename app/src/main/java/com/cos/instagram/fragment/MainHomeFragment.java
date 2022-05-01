package com.cos.instagram.fragment;

import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cos.instagram.MainActivity;
import com.cos.instagram.PostContentActivity;
import com.cos.instagram.R;
import com.cos.instagram.adapter.PostContentAdapter;
import com.cos.instagram.model.FirebaseID;
import com.cos.instagram.model.Post;
import com.cos.instagram.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainHomeFragment extends Fragment {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private PostContentAdapter mAdapter;
    private List<Post> mDatas;

    private RecyclerView rv_post;

    private MainViewModel model;

    private ImageButton btn_home, btn_search, btn_play, btn_shop, btn_profile;

    private static User user;

    private Context context;

    PostContentActivity postContent = new PostContentActivity();


    public static MainHomeFragment newInstance() {
        return new MainHomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_home_fragment, container, false);



        context = container.getContext();
        rv_post = view.findViewById(R.id.rv_post);

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

    @Override
    public void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();

        if(postContent.postnum == 1){
            mStore.collection(FirebaseID.post)
                    .orderBy(FirebaseID.postdate, Query.Direction.DESCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                            mDatas.clear();
                            for(DocumentSnapshot snap : value.getDocuments()){
                                Map<String, Object> shot = snap.getData();
                                String postId = String.valueOf(shot.get(FirebaseID.postId));
                                String username = String.valueOf(shot.get(FirebaseID.username));
                                String contents = String.valueOf(shot.get(FirebaseID.contents));
                                String like = String.valueOf(shot.get(FirebaseID.like));
                                String reply = String.valueOf(shot.get(FirebaseID.reply));
                                Post data = new Post(postId, username, contents, 0, 0);
                                mDatas.add(data);
                                Toast.makeText(context, String.valueOf(shot.get(FirebaseID.contents)), Toast.LENGTH_LONG).show();
                            }

                            mAdapter = new PostContentAdapter(mDatas);
                            rv_post.setAdapter(mAdapter);
                        }
                    });
            postContent.postnum = 0;

        }
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