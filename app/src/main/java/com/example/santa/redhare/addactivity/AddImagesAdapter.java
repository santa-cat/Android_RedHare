package com.example.santa.redhare.addactivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.santa.redhare.imageactivity.ImageActivity;
import com.example.santa.redhare.R;
import com.example.santa.redhare.imageselect.ImageSelectView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/3.
 */
public class AddImagesAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mImages;
    private View.OnClickListener mPictureListener;
    private DisplayImageOptions options;

    public AddImagesAdapter(Context context, ArrayList<String> images, View.OnClickListener listener) {
        mContext = context;
        mImages = images;
        mPictureListener = listener;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.picture)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .showImageForEmptyUri(R.mipmap.picture)
                .showImageOnFail(R.mipmap.picture)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int position) {
        return mImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_addimage, parent, false);
            holder = new Holder();
            holder.imageSelect = (ImageSelectView) convertView.findViewById(R.id.image_square);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


        if (position == getCount()-1) {
            Log.d("DEBUG", "add_grid");
            if (mImages.size() == 1 || mImages.size() > 9) {
                holder.imageSelect.setVisibility(View.GONE);
            } else {
                holder.imageSelect.setVisibility(View.VISIBLE);
            }
            ImageLoader.getInstance().displayImage(mImages.get(position), holder.imageSelect.mMainImage, options);
            holder.imageSelect.mMainImage.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.imageSelect.setCheckImageVisbility(View.GONE);
            holder.imageSelect.setOnClickListener(mPictureListener);
        } else {
            if (holder.imageSelect.getVisibility() == View.GONE) {
                holder.imageSelect.setVisibility(View.VISIBLE);
            }
            holder.imageSelect.setCheckImageVisbility(View.VISIBLE);
            holder.imageSelect.mMainImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.imageSelect.setOnClickListener(null);
            ImageLoader.getInstance().displayImage(mImages.get(position), holder.imageSelect.mMainImage, options);
            holder.imageSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putExtra("curPosition", position);
                    intent.putStringArrayListExtra("images", mImages);
                    mContext.startActivity(intent);
                }
            });

            holder.imageSelect.setOnSelectListener(new ImageSelectView.OnSelectListener() {
                @Override
                public boolean onSelect() {
                    removePosition(position);
                    return false;
                }

                @Override
                public boolean onCancel() {
                    return false;
                }
            });
        }
        return convertView;
    }

    private void removePosition(int position) {
        mImages.remove(position);
        notifyDataSetChanged();
    }

    public class Holder{
        private ImageSelectView imageSelect;
    };
}
