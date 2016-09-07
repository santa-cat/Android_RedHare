package com.example.santa.redhare.imageselect;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;

import com.example.santa.redhare.R;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santa on 16/8/15.
 */
public class ImageSelectorActivity extends AppCompatActivity {

    private List<String> mImages;
    private final static int MSG_FINISH = 1;
    private GridView mGridView;
    private ArrayList<String> mSelect;
    private int mMaxNum = 9;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageselector);
        Intent intent = getIntent();
        mMaxNum = intent.getIntExtra("maxNum", mMaxNum);

        initView();
        initImages();
    }

    private void initView() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int numColumns = 4;
        // 获取屏幕宽度
        int screenWidth = outMetrics.widthPixels;
        // item的间距
        int padding = (int) (outMetrics.density * 3);
        // 动态计算item的宽度和高度
        int itemWidth = (screenWidth - padding * numColumns) / numColumns;

        mGridView = (GridView) findViewById(R.id.imageselect_grid);
        mGridView.setNumColumns(4);
        mGridView.setPadding(padding, 0, padding, 0);
        mGridView.setHorizontalSpacing(padding);
        mGridView.setVerticalSpacing(padding);

        View cancel = findViewById(R.id.imageselect_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelect.clear();
                finish();
            }
        });

        final View finish = findViewById(R.id.imageselect_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("images", mSelect);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void initImages(){
        mImages = new ArrayList<>();
        mSelect = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = getContentResolver();

                // 只查询jpeg和png的图片
                Cursor cursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" },
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (null != cursor && cursor.moveToNext()) {
                    // 获取图片的路径
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    Log.d("DEBUG", "path = "+path);
                    String imageUrl = ImageDownloader.Scheme.FILE.wrap(path);
//                    Log.d("DEBUG", "imageUrl = "+imageUrl);

                    mImages.add(imageUrl);
                }
                mHandler.sendEmptyMessage(MSG_FINISH);
            }
        }).start();

    }



    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FINISH:
                    Log.d("DEBUG", "images size = "+mImages.size());
                    mGridView.setAdapter(new ImageSelectAdapter(ImageSelectorActivity.this, mImages ,mSelect, mMaxNum));
                    break;
                default:
                    break;
            }
        }
    };

}
