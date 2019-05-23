package com.ff.factoryimage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ff.factoryimage.utils.LogUtil;
import com.ff.factoryimage.utils.ImageUtil;
import com.ff.factoryimage.utils.FileUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String IMAGE_PATH = "imagePath";
    private static final String SHOW_BUTTON = "showButton";
    private static final String COMPRESS = "compress";
    private static final int REQUEST_CODE = 200;
    private static final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE};

    private Button btnShowQRcode;
    private ImageView mImageViewContent;

    private String mImagePath;
    private boolean isShowButton = false;//default not show
    private boolean needCompress = true;//default need
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShowQRcode = findViewById(R.id.btn_show_qrcode);
        btnShowQRcode.setOnClickListener(this);
        mImageViewContent = findViewById(R.id.iv_content);
        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
                LogUtil.d("lack permission");
                ActivityCompat.requestPermissions(this,
                        permissions, REQUEST_CODE);
            } else {
                dealIntent();
            }
        } else {
            dealIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        LogUtil.d("onRequestPermissionsResult " + permissions[i] + " does not grant");
                        Toast.makeText(this, R.string.need_permission_tips, Toast.LENGTH_LONG).show();
                        break;
                    } else if (i == grantResults[grantResults.length - 1]) {
                        //last permission
                        LogUtil.d("onRequestPermissionsResult get all permission");
                        dealIntent();
                    }
                }
            }
        }
    }

    private void dealIntent() {
        LogUtil.d(TAG, "deal intent");
        Intent intent = getIntent();
        LogUtil.d(TAG, "intent = " + intent);
        if (intent.hasExtra(IMAGE_PATH)) {
            mImagePath = intent.getStringExtra(IMAGE_PATH);
        }

        if (intent.hasExtra(SHOW_BUTTON)) {
            isShowButton = intent.getBooleanExtra(SHOW_BUTTON, false);
        }

        if (intent.hasExtra(COMPRESS)) {
            needCompress = intent.getBooleanExtra(COMPRESS, true);
        }
        LogUtil.d(TAG, "mImagePath = " + mImagePath + "   isShowButton = " + isShowButton + "   needCompress = " + needCompress);
        if (!TextUtils.isEmpty(mImagePath)) {
            if (FileUtil.pathExist(mImagePath)) {
                if (needCompress) {
                    mBitmap = ImageUtil.getBitmapFromPath(this, mImagePath);
                } else {
                    mBitmap = ImageUtil.getBitmapFromPath(this, mImagePath, false);
                }
                LogUtil.d(TAG, "mBitmap = " + mBitmap);
            } else {
                // file does not exsist
                LogUtil.d(mImagePath + " does not exist");
                Toast.makeText(this, R.string.file_does_not_exist, Toast.LENGTH_LONG).show();
            }
        } else {
            //no file
            LogUtil.d(" The value of imagePath is null");
            Toast.makeText(this, R.string.file_does_not_exist, Toast.LENGTH_LONG).show();
        }

        if (isShowButton) {
            mImageViewContent.setVisibility(View.GONE);
        } else {
            mImageViewContent.setImageBitmap(mBitmap);
            mImageViewContent.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_show_qrcode) {
            mImageViewContent.setVisibility(View.VISIBLE);
            mImageViewContent.setBackgroundColor(Color.WHITE);
            mImageViewContent.setImageBitmap(mBitmap);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBitmap != null && !mBitmap.isRecycled()){
            LogUtil.d("recycle mBitmap");
            mBitmap.recycle();
        }
    }
}
