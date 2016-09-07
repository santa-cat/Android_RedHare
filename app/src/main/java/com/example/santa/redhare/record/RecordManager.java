package com.example.santa.redhare.record;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by santa on 16/8/10.
 */
public class RecordManager {

    private static RecordManager mInstance;
    private MediaRecorder mMediaRecorder;
    private String mCurrentFilePath;


    private boolean isPrepare;

    public static RecordManager getInstance() {
        if (null == mInstance) {
            synchronized(RecordManager.class) {
                if (null == mInstance) {
                    mInstance = new RecordManager();
                }
            }
        }

        return mInstance;
    }


    /**
     * 使用接口 用于回调
     */
    public interface OnAudioStateListener {
        void wellPrepared();
    }

    public OnAudioStateListener mAudioStateListener;

    /**
     * 回调方法
     */
    public void setOnAudioStateListener(OnAudioStateListener listener) {
        mAudioStateListener = listener;
    }

    // 去准备
    public void prepareAudio() {
        try {
            String dirStr = Environment.getExternalStorageDirectory()+"/redhard/record";

            isPrepare = false;
            File dir = new File(dirStr);
            if (!dir.exists()) {
                Log.d("DEBUG", ""+dir.mkdirs());
            }
            String fileName = generateFileName();
            File file = new File(dir, fileName);

            mCurrentFilePath =file.getAbsolutePath();

//            Log.d("DEBUG", mDir);
//            Log.d("DEBUG", fileName);
//            Log.d("DEBUG", mCurrentFilePath);
//            Log.d("DEBUG", file.getAbsolutePath());

            mMediaRecorder = new MediaRecorder();
            // 设置输出文件
            mMediaRecorder.setOutputFile(mCurrentFilePath);
            // 设置MediaRecorder的音频源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            // 设置音频编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            // 准备录音
            mMediaRecorder.prepare();
            // 开始
            mMediaRecorder.start();
            // 准备结束
            isPrepare = true;
            if (mAudioStateListener != null) {
                mAudioStateListener.wellPrepared();
            }

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 随机生成文件的名称
     */
    private String generateFileName() {
        return String.valueOf(System.currentTimeMillis()) + ".amr";
    }

    public int getVoiceLevel(int maxlevel) {
        if (isPrepare) {
            // mMediaRecorder.getMaxAmplitude() 1~32767
            return maxlevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
        }
        return 1;
    }

    /**
     * 释放资源
     */
    public void release() {
        //mMediaRecorder.stop();
        if (null != mMediaRecorder) {
            mMediaRecorder.reset();
            mMediaRecorder = null;
        }
    }

    /**
     * 取消录音
     */
    public void cancel() {
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }

    }

    public String getCurrentFilePath() {

        return mCurrentFilePath;
    }

}
