package com.example.santa.redhare.record;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by santa on 16/8/10.
 */
public class RecordTextView extends TextView {
    private RecordManager mRecordManager;
    private boolean isReadToRecord = false;
    private DialogManager mDialogManager;
    private long mTime;
    private static final int DISTANCE_Y_CANCEL = 50;



    private static final int MSG_AUDIO_PREPARED = 0;
    private static final int MSG_VOICE_CHANGED = 1;
    private static final int MSG_DIALOG_DIMISS = 2;

    public RecordTextView(Context context) {
        this(context, null);
    }

    public RecordTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RecordTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        mDialogManager = new DialogManager(context);
        mRecordManager = RecordManager.getInstance();
        mRecordManager.setOnAudioStateListener(new RecordManager.OnAudioStateListener() {
            @Override
            public void wellPrepared() {
                mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mRecordManager.prepareAudio();
                setText("松开 结束");
                return false;
            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (isReadToRecord) {
                    // 如果想要取消，根据x,y的坐标看是否需要取消
                    if (isToCancle(event.getX(), event.getY())) {
                        mDialogManager.wantToCancel();
                    } else {
                        mDialogManager.recording();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isReadToRecord) {
                    reset();
                    mRecordManager.cancel();
                    return super.onTouchEvent(event);
                }
                long curTime = System.currentTimeMillis();
                if (curTime - mTime < 1000) {
                    mDialogManager.tooShort();
                    mRecordManager.cancel();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1000);
                } else {
                    mDialogManager.dismiss();
                    if(isToCancle(event.getX(), event.getY())) {
                        mRecordManager.cancel();
                    } else {
                        mRecordManager.release();
                        if (null != mListener) {
                            mListener.onFinish((int) ((curTime - mTime)/1000), mRecordManager.getCurrentFilePath());
                        }
                    }
                }

                reset();
                break;
        }

        return super.onTouchEvent(event);
    }


    private void reset() {
        isReadToRecord = false;
        mTime = 0;
        setText("按住发送语音");
    }

    private boolean isToCancle(float x, float y) {
        if (x < 0 || x > getWidth()) { // 超过按钮的宽度
            return true;
        }
        // 超过按钮的高度
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }

        return false;
    }

    /*
     * 获取音量大小的线程
     */
    private Runnable mGetVoiceLevelRunnable = new Runnable() {

        public void run() {
            while (isReadToRecord) {
                try {
                    Thread.sleep(100);
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    // 显示對話框在开始录音以后
                    isReadToRecord = true;
                    mDialogManager.show();
                    mTime = System.currentTimeMillis();
                    // 开启一个线程更新音量
                    new Thread(mGetVoiceLevelRunnable).start();
                    break;

                case MSG_VOICE_CHANGED:
//                    mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
                    break;

                case MSG_DIALOG_DIMISS:
                    mDialogManager.dismiss();
                    break;

            }

            super.handleMessage(msg);
        }
    };

    private OnFinishRecordListener mListener;

    public void setOnFinishRecordListener(OnFinishRecordListener listener) {
        mListener = listener;
    }

    public interface OnFinishRecordListener{
        void onFinish(int time, String filePath);
    }


}
