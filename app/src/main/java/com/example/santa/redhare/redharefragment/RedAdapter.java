package com.example.santa.redhare.redharefragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.santa.redhare.R;
import com.example.santa.redhare.commentactivity.CommActivity;
import com.example.santa.redhare.emoji.SpanStringUtils;
import com.example.santa.redhare.personactivity.PersonActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santa on 16/8/2.
 */
public class RedAdapter extends BaseAdapter {
    private Context mContext;
    private List<RedItem> mData;
    private View.OnClickListener mShareListener;

    public RedAdapter(Context context, List<RedItem> list) {
        mContext = context;
        mData = list;
        initShareListener(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final RedHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_redlist, parent, false);
            holder = new RedHolder();
            holder.headportrait = (ImageView) convertView.findViewById(R.id.main_headportrait);
            holder.userName = (TextView) convertView.findViewById(R.id.main_username);
            holder.trade = (TextView) convertView.findViewById(R.id.main_trade);
            holder.job = (TextView) convertView.findViewById(R.id.main_job);
            holder.company = (TextView) convertView.findViewById(R.id.main_company);
            holder.station = (TextView) convertView.findViewById(R.id.main_station);
            holder.src = (TextView) convertView.findViewById(R.id.main_src);
            holder.content = (ShrinkText) convertView.findViewById(R.id.main_connent);
            holder.images = (GridView) convertView.findViewById(R.id.main_images);
            holder.big = (ImageView) convertView.findViewById(R.id.red_big);
            holder.bignum = (TextView) convertView.findViewById(R.id.red_bignum);
            holder.share = convertView.findViewById(R.id.red_share);
            holder.commLayout = convertView.findViewById(R.id.red_commlayout);
            holder.titleLayout = convertView.findViewById(R.id.main_titlelayout);
            holder.title = (TextView) convertView.findViewById(R.id.main_title);
            holder.bigLayout = convertView.findViewById(R.id.red_biglayout);
            holder.redAll = convertView.findViewById(R.id.red_all);
            convertView.setTag(holder);
        } else {
            holder = (RedHolder) convertView.getTag();
        }

        holder.userName.setText(mData.get(position).getUserName());
        holder.trade.setText(mData.get(position).getTrade());
        holder.job.setText(mData.get(position).getJob());
        holder.company.setText(mData.get(position).getCompany());
        holder.station.setText(mData.get(position).getStation());
        holder.src.setText(mData.get(position).getSrc());
        holder.content.setText(SpanStringUtils.getTopicContent(mContext,
                SpanStringUtils.getEmojiContent(1, mContext, (int) (holder.content.getTextSize() * 15 / 10), mData.get(position).getContent())));
        holder.content.setMovementMethod(CustomLinkMovementMethod.getInstance());
        holder.share.setOnClickListener(mShareListener);
        holder.bignum.setText(String.valueOf(mData.get(position).getLikeNum()));
        int id = mData.get(position).isLike() ? R.mipmap.like_checked : R.mipmap.like;
        holder.big.setImageDrawable(mContext.getResources().getDrawable(id));
        holder.bigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLike(holder.big, holder.bignum, position);
            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommActivity.class);
                intent.putExtra("comm", mData.get(position));
                mContext.startActivity(intent);
            }
        };
        holder.content.setOnClickListener(listener);
        holder.commLayout.setOnClickListener(listener);
        holder.redAll.setOnClickListener(listener);

        holder.headportrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersonActivity.class);
                intent.putExtra("name", mData.get(position).getUserName());
                intent.putExtra("company", mData.get(position).getCompany());
                intent.putExtra("station", mData.get(position).getStation());
                mContext.startActivity(intent);
            }
        });

        if (null == mData.get(position).getTitle() || "".equals(mData.get(position).getTitle())) {
            holder.titleLayout.setVisibility(View.GONE);
        } else {
            holder.title.setText(mData.get(position).getTitle());
            holder.titleLayout.setVisibility(View.VISIBLE);
        }


        if (mData.get(position).getImages() == null) {
            holder.images.setAdapter(ImagesAdapterBuilder.getAdapter(mContext, new ArrayList<String>()));
            return convertView;
        }

        //如果只有一张照片放大
        if (mData.get(position).getImages().size() == 1) {
            holder.images.setNumColumns(2);
        } else {
            holder.images.setNumColumns(3);
        }
        holder.images.setAdapter(ImagesAdapterBuilder.getAdapter(mContext, mData.get(position).getImages()));
        holder.images.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);



        return convertView;
    }

    private void clickLike(ImageView imageView, TextView textView, int position){
        if (!mData.get(position).isLike()) {
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.like_checked));
            textView.setText(String.valueOf(Integer.valueOf(textView.getText().toString()) + 1));
            mData.get(position).setLike(true);
        }

    }


    private void initShareListener(final Context context) {
        mShareListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View contentView = LayoutInflater.from(context).inflate(R.layout.popu_share, null);

                final PopupWindow popupWindow = new PopupWindow(contentView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);// 取得焦点
                //点击推出,要设置backgroundDrawable
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                /**设置PopupWindow弹出和退出时候的动画效果*/
                popupWindow.setAnimationStyle(R.style.animation);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                    }
                });
                backgroundAlpha(0.4f);
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                };
                View cancle = contentView.findViewById(R.id.popu_cancle);
                cancle.setOnClickListener(listener);
            }
        };

    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity)mContext).getWindow().setAttributes(lp);
    }

    private class RedHolder{
        private TextView userName;
        private TextView trade;
        private TextView job;
        private TextView company;
        private TextView station;
        private TextView src;
        private ShrinkText content;
        private GridView images;
        private View share;
        private ImageView big;
        private TextView bignum;
        private View bigLayout;
        private View commLayout;
        private View redAll;
        private ImageView headportrait;

        private View titleLayout;
        private TextView title;
    }
}
