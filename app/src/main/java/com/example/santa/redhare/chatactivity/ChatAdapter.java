package com.example.santa.redhare.chatactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.santa.redhare.imageactivity.ImageActivity;
import com.example.santa.redhare.R;
import com.example.santa.redhare.emoji.SpanStringUtils;
import com.example.santa.redhare.record.MediaManager;
import com.example.santa.redhare.redharefragment.ScaleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/6.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ChatItem> mData;
    private DisplayImageOptions options;
    public ChatAdapter(Context context, ArrayList<ChatItem> list) {
        mContext = context;
        mData = list;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.near_gray)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .showImageForEmptyUri(R.mipmap.picture)
                .showImageOnFail(R.mipmap.picture)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getDirection();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ChatStroke.DIR_LEFT) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_chatleft, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_chatright, parent, false);
        }
        return new LeftHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (mData.get(position).getTime() != null && mData.get(position).getTime() != "") {
            ((LeftHolder) holder).time.setVisibility(View.VISIBLE);
            ((LeftHolder) holder).time.setText(mData.get(position).getTime());
        } else {
            ((LeftHolder) holder).time.setVisibility(View.GONE);
        }

        if (mData.get(position).getPictureUri() != null){
            ((LeftHolder) holder).picture.setVisibility(View.VISIBLE);
            ((LeftHolder) holder).picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ImagesCount().execute(position);
                }
            });
            ((LeftHolder) holder).text.setText("");
            ((LeftHolder) holder).image.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(mData.get(position).getPictureUri(), ((LeftHolder) holder).picture, options);

        } else {
            ((LeftHolder) holder).picture.setVisibility(View.GONE);
            if (mData.get(position).getImageId() != -1) {
                ((LeftHolder) holder).image.setVisibility(View.VISIBLE);
                ((LeftHolder) holder).image.setImageResource(mData.get(position).getImageId());
                ((LeftHolder) holder).layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MediaManager.playSound(mData.get(position).getFilePath(), new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                ((LeftHolder) holder).image.setImageResource(R.drawable.sound_all);
                            }
                        });
                        ((LeftHolder) holder).image.setImageResource(R.drawable.media_play);
                        AnimationDrawable animation = (AnimationDrawable) ((LeftHolder) holder).image.getDrawable();
                        animation.start();
                    }
                });
            } else {
                ((LeftHolder) holder).image.setVisibility(View.GONE);
                ((LeftHolder) holder).layout.setOnClickListener(null);
            }
            ((LeftHolder) holder).text.setText(SpanStringUtils.getEmojiContent(1, mContext, (int) (((LeftHolder) holder).text.getTextSize() * 15 / 10), mData.get(position).getText()));
        }

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class LeftHolder extends RecyclerView.ViewHolder{
        private TextView time;
        private ImageView icon;
        //以下两个只显示一个
        private TextView text;
        private ImageView image;

        private View layout;
        private ScaleImageView picture;

        public LeftHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.chat_time);
            text = (TextView) itemView.findViewById(R.id.chat_text);
            image = (ImageView) itemView.findViewById(R.id.chat_image);
            icon = (ImageView) itemView.findViewById(R.id.chat_icon);
            layout = itemView.findViewById(R.id.chat_cntlayout);
            picture = (ScaleImageView) itemView.findViewById(R.id.chat_picture);
        }
    }


    private class ImagesCount extends AsyncTask<Integer, Void, ArrayList<String>> {
        private int cur = 0;

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            if (params.length >= 1) {
                cur = params[0];
            }
            ArrayList<String> images = new ArrayList<>();
            for (ChatItem item : mData) {
                String uri = item.getPictureUri();
                if (uri != null) {
                    images.add(uri);
                }
            }
            return images;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            Intent intent = new Intent(mContext, ImageActivity.class);
            intent.putExtra("curPosition", cur);
            intent.putStringArrayListExtra("images", strings);
            mContext.startActivity(intent);

        }
    }



}
