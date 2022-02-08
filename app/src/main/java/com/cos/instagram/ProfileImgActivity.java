package com.cos.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cos.instagram.adapter.ProfileImgAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class ProfileImgActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager gridLayoutManager;

    public ImageView imgView;

    public static Context ImgContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_img);

        ImgContext = this;

        gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);

        findViewById(R.id.gallery_exit).setOnClickListener(this);
        findViewById(R.id.gallery_commit).setOnClickListener(this);

        imgView = (ImageView)findViewById(R.id.gallery_iv);

        recyclerView = (RecyclerView)findViewById(R.id.gallery_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new ProfileImgAdapter(this, getImagesPath(this));
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gallery_exit:
                Toast.makeText(ImgContext, "나가기 누름", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.gallery_commit:
                Toast.makeText(ImgContext, "확인 누름", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(PathOfImage);
        }
        return listOfAllImages;
    }


}