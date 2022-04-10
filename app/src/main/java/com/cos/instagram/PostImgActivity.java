package com.cos.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cos.instagram.adapter.PostImgAdapter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostImgActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager gridLayoutManager;

    public ImageView imgView;

    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private boolean previewing = false;
    private LayoutInflater controlInflater = null;

    public static Context ImgContext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_img);

        //권한 체크
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("카메라 권한을 거부하셨습니다.")
                .setPermissions(Manifest.permission.CAMERA)
                .check();

        getWindow().setFormat(PixelFormat.UNKNOWN);

        findViewById(R.id.stopcamerapreview).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(camera != null && previewing){
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                    previewing = false;
                }
            }});

        ImgContext1 = this;

        gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);

        findViewById(R.id.gallery_exit).setOnClickListener(this);
        findViewById(R.id.gallery_commit).setOnClickListener(this);

        findViewById(R.id.img_slide_btn).setOnClickListener(this);
        findViewById(R.id.cam_slide_btn).setOnClickListener(this);

        imgView = (ImageView)findViewById(R.id.gallery_iv);

        recyclerView = (RecyclerView)findViewById(R.id.gallery_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new PostImgAdapter(this, getImagesPath(this));
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gallery_exit:
                Toast.makeText(ImgContext1, "나가기 누름", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.gallery_commit:
                Toast.makeText(ImgContext1, "확인 누름", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PostContentActivity.class);
                startActivity(intent);
                break;
            case R.id.img_slide_btn:
                findViewById(R.id.profile_edit_layout).animate().translationX(0f)
                        .setDuration(250)
                        .start();

                findViewById(R.id.trans_border).animate().translationX(0f)
                        .setDuration(250)
                        .start();

                findViewById(R.id.cam_slide_btn).animate().alpha(0.5f)
                        .setDuration(250)
                        .start();
                findViewById(R.id.img_slide_btn).animate().alpha(1f)
                        .setDuration(250)
                        .start();
                break;

            case R.id.cam_slide_btn:
                findViewById(R.id.profile_edit_layout).animate().translationX(-1080f)
                        .setDuration(250)
                        .start();

                findViewById(R.id.trans_border).animate().translationX(540f)
                        .setDuration(250)
                        .start();

                findViewById(R.id.img_slide_btn).animate().alpha(0.5f)
                        .setDuration(250)
                        .start();
                findViewById(R.id.cam_slide_btn).animate().alpha(1f)
                        .setDuration(250)
                        .start();
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

    public int rotate(){
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0;
                break;

            case Surface.ROTATION_90: degrees = 90;
                break;

            case Surface.ROTATION_180: degrees = 180;
                break;

            case Surface.ROTATION_270: degrees = 270;
                break;
        }

        int result  = (90 - degrees + 360) % 360;

        return result;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        camera = Camera.open();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
        if(previewing){
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null){

            camera.setDisplayOrientation(rotate());

            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
//            Toast.makeText(MainActivity.this, "권한이 허용됨", Toast.LENGTH_SHORT).show();
            surfaceView = findViewById(R.id.surfaceview);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(PostImgActivity.this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            camera = Camera.open();

//            Camera.Parameters params = camera.getParameters();
//            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//            camera.setParameters(params);

            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            camera.setDisplayOrientation(rotate());
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(PostImgActivity.this, "권한이 거부됨\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };
}