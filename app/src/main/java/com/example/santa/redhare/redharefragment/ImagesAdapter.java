package com.example.santa.redhare.redharefragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.santa.redhare.imageactivity.ImageActivity;
import com.example.santa.redhare.utils.ImageLoaderManager;
import com.example.santa.redhare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/3.
 */
public class ImagesAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mImages;
    private DisplayImageOptions mOptions;

    public ImagesAdapter(Context context, ArrayList<String> images) {
        mContext = context;
        mImages = images;
        mOptions = ImageLoaderManager.getImageOptions();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
            holder = new Holder();
            holder.imageSuqare = (ImageView) convertView.findViewById(R.id.image_square);
            holder.imageScale= (ImageView) convertView.findViewById(R.id.image_scale);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


        if (getCount() == 1) {
            ImageLoader.getInstance().displayImage(mImages.get(position), holder.imageScale, mOptions);
        } else {
            ImageLoader.getInstance().displayImage(mImages.get(position), holder.imageSuqare, mOptions);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putExtra("curPosition", position);
                intent.putStringArrayListExtra("images", mImages);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }



    public class Holder{
        private ImageView imageSuqare;
        private ImageView imageScale;
    };
}
