package com.example.santa.redhare.utils;

import android.graphics.Bitmap;

import com.example.santa.redhare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by santa on 16/8/17.
 */
public class ImageLoaderManager {
    private static DisplayImageOptions mOptions;

    public static DisplayImageOptions getImageOptions() {
        if (null == mOptions) {
            mOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.near_gray)
                    .showImageForEmptyUri(R.mipmap.picture)
                    .showImageOnFail(R.mipmap.picture)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

        }
        return mOptions;
    }


}
