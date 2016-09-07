package com.example.santa.redhare.imageselect;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.santa.redhare.utils.ImageLoaderManager;
import com.example.santa.redhare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by santa on 16/8/16.
 */

public class ImageSelectAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mImages;
    private DisplayImageOptions options;
    private List<String> mSelect;
    private AlertDialog.Builder mDialog;
    private int mMaxNum;

    public ImageSelectAdapter(Context context, List<String> images, List<String> select, int maxNum) {
        mContext = context;
        mImages = images;
        mMaxNum = maxNum;
        options = ImageLoaderManager.getImageOptions();
        mSelect = select;
        initDialog();
    }


    private void initDialog() {
        mDialog = new AlertDialog.Builder(mContext);
        mDialog.setMessage("最多能够选择"+mMaxNum+"张照片");
        mDialog.setNegativeButton("确定", null);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageSelectHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_imageselect, parent, false);
            holder = new ImageSelectHolder();
            holder.imageSelectView = (ImageSelectView) convertView.findViewById(R.id.imageselect_image);
            convertView.setTag(holder);
        } else {
            holder = (ImageSelectHolder) convertView.getTag();
        }

        Log.d("DEBUG", "position = "+position);
        Log.d("DEBUG", "uri = "+mImages.get(position));

        ImageLoader.getInstance().displayImage(mImages.get(position), holder.imageSelectView.mMainImage, options);
        holder.imageSelectView.setOnSelectListener(new ImageSelectView.OnSelectListener() {
            @Override
            public boolean onSelect() {
                return addSelectImages(position);
            }

            @Override
            public boolean onCancel() {
                return rmvSelectImages(position);
            }
        });

        return convertView;
    }



    private boolean addSelectImages(int position) {
        if (mSelect.size() >= mMaxNum) {
            mDialog.show();
            return false;
        } else {
            mSelect.add(mImages.get(position));
            return true;
        }
    }

    private boolean rmvSelectImages(int position) {
        mSelect.remove(mImages.get(position));
        return true;
    }


    private class ImageSelectHolder{
        private ImageSelectView imageSelectView;
    }


}
