package com.example.santa.redhare.redharefragment;

import android.content.Context;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/3.
 */
public class ImagesAdapterBuilder {




    public static BaseAdapter getAdapter(Context context, ArrayList<String> imageIds) {
        if (!imageIds.isEmpty()) {
            return new ImagesAdapter(context, imageIds);
        } else {
            return null;
        }
    };


}
