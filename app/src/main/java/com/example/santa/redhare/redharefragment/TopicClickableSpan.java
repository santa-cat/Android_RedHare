package com.example.santa.redhare.redharefragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import com.example.santa.redhare.topicactivity.TopicActivity;

/**
 * Created by santa on 16/8/22.
 */
public class TopicClickableSpan extends ClickableSpan {
    private int color = 0xff497eb0;
    private Context context;
    private String text;

    public TopicClickableSpan(String text, Context context) {
        super();
        this.text = text;
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(color);
    }

    @Override
    public void onClick(View widget) {
        Intent intent = new Intent(context, TopicActivity.class);
        intent.putExtra("topic", text.replaceAll("#",""));
        Log.d("DEBUG", "onClickonClickonClick");
        context.startActivity(intent);
    }
}
