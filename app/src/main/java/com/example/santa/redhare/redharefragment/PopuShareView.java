package com.example.santa.redhare.redharefragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/4.
 */
public class PopuShareView extends PopupWindow{

    public PopuShareView(Context context, View view) {


        View contentView = LayoutInflater.from(context).inflate(R.layout.popu_share, null);
        new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);// 取得焦点
        //点击推出,要设置backgroundDrawable
        setBackgroundDrawable(new BitmapDrawable());
        /**设置PopupWindow弹出和退出时候的动画效果*/
//        setAnimationStyle(R.style.animation);
        setOutsideTouchable(true);

        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

}
