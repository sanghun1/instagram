package com.cos.instagram.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cos.instagram.ProfileImgActivity;
import com.cos.instagram.R;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileImgAdapter extends RecyclerView.Adapter<ProfileImgAdapter.ProfileImgHolder>{
    private ArrayList<String> mDataset;
    private Activity activity;

    public static class ProfileImgHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ProfileImgHolder(CardView v) {
            super(v);
            cardView = v;
        }

    }

    public ProfileImgAdapter(Activity activity, ArrayList<String> myDataset) {
        mDataset = myDataset;
        Collections.reverse(mDataset);
        this.activity = activity;
    }

    @Override
    public ProfileImgAdapter.ProfileImgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);

        return new ProfileImgHolder(cardView);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ProfileImgHolder holder, int position) {
        CardView cardView = holder.cardView;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmp = BitmapFactory.decodeFile(mDataset.get(holder.getAdapterPosition()));
                ((ProfileImgActivity)ProfileImgActivity.ImgContext).imgView.setImageBitmap(bmp);
            }
        });
        ImageView imageView = cardView.findViewById(R.id.imageView);
        Glide.with(activity).load(mDataset.get(position)).centerCrop().override(500).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
