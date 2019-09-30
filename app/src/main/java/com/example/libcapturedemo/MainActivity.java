package com.example.libcapturedemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.libcameracapture.CameraControl;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private CameraControl cameraControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraControl = new CameraControl(this);

        verifyStoragePermissions();
    }

    //在大于23的android版本中,文件读写需要动态申请权限
    private void verifyStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                    (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                    (this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            } else {
                Log.d(TAG, "已有权限,不需要再申请");
            }
        }
    }

    public void onTakePic(View view) {
        cameraControl.captureStillPicture1();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraControl.close();
    }
}
