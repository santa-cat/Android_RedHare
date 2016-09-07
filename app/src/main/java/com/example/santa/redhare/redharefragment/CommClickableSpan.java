package com.example.santa.redhare.redharefragment;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

/**
 * Created by santa on 16/8/22.
 */
public class CommClickableSpan extends ClickableSpan {
    private int color = Color.BLACK;
    private View.OnClickListener listener;
    private Context context;


    public CommClickableSpan(Context context) {
        super();
        this.context = context;
    }

    public CommClickableSpan(Context context, View.OnClickListener listener) {
        super();
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(color);
    }

    @Override
    public void onClick(View widget) {
        if (null != listener) {
            listener.onClick(new View(context));
        }
        Log.d("DEBUG", "comm actiy");
    }
}
