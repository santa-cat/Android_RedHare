package com.example.santa.redhare.record;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/10.
 */
public class DialogManager {

    private View mDialogView;
    private AlertDialog.Builder builder;
    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLable;

    private Context mContext;

    private AlertDialog mDialog;

    public DialogManager(Context context) {
        this.mContext = context;
    }

    public void show(){
        builder = new AlertDialog.Builder(mContext, R.style.NobackDialog);
        mDialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_record,null);

        mIcon = (ImageView) mDialogView.findViewById(R.id.dlg_main);
        mVoice = (ImageView) mDialogView.findViewById(R.id.dlg_sec);
        mLable = (TextView) mDialogView.findViewById(R.id.dlg_text);


        builder.setView(mDialogView);
        mDialog = builder.create();
        mDialog.show();

        recording();
    }


    public void dismiss() {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void recording(){
        if(mDialog != null && mDialog.isShowing()){ //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.mipmap.ic_launcher);
            mLable.setText("手指上滑，取消发送");
        }
    }

    // 显示想取消的对话框
    public void wantToCancel() {
        if(mDialog != null && mDialog.isShowing()){ //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

//            mIcon.setImageResource(R.drawable.cancel);
            mLable.setText("松开手指，取消发送");
        }
    }

    // 显示时间过短的对话框
    public void tooShort() {
        if(mDialog != null && mDialog.isShowing()){ //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

//            mIcon.setImageResource(R.drawable.voice_to_short);
            mLable.setText("说话时间太短");
        }
        Log.d("DEBUG", "说话时间太短" );
    }

    // 显示更新音量级别的对话框
    public void updateVoiceLevel(int level) {
        if(mDialog != null && mDialog.isShowing()){

            //设置图片的id
//            int resId = mContext.getResources().getIdentifier(v+level, drawable, mContext.getPackageName());
//            mVoice.setImageResource(resId);
        }
    }

}
