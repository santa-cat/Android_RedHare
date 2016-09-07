package com.example.santa.redhare.addactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santa.redhare.R;
import com.example.santa.redhare.emoji.EmojiPagerAdapter;
import com.example.santa.redhare.emoji.EmojiUtils;
import com.example.santa.redhare.emoji.SpanStringUtils;
import com.example.santa.redhare.imageselect.ImageSelectorActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santa on 16/8/17.
 */
public class AddRedActivity extends AppCompatActivity {

    private EditText mEditText;
    private InputMethodManager mImm;
    private GridView mGridView;
    private ArrayList<String> mImages;
    private View.OnClickListener mPictureListener;
    private BaseAdapter mGridAdapter;
    private TextView mSend;
    private TextView mCancel;

    //emoji
    private View mEmojiLayout;
    private int mEmojiType = EmojiUtils.EMOTION_CLASSIC_TYPE;

    //picture
    private Uri imageUri;
    private final static int TAKE_PHOTO = 1;
    private final static int PICK_PHOTO = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addred);

        initImages();
        initView();
    }


    private void initView(){
        initTopBar();
        initEditView();
        initPicture();
        initGridView();
        initEmoji();
        initTopic();
    }

    private void initTopic() {
        View topic = findViewById(R.id.add_topic);
        assert topic != null;
        topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curPosition = mEditText.getSelectionStart();
                StringBuilder sb = new StringBuilder(mEditText.getText().toString());
                sb.insert(curPosition, "##");
                // 特殊文字处理,将表情等转换一下
                mEditText.setText(SpanStringUtils.getEmojiContent(1,
                        AddRedActivity.this, (int) mEditText.getTextSize(), sb.toString()));
                // 将光标设置到新增完表情的右侧
                mEditText.setSelection(curPosition + 1);
            }
        });
    }

    private void initTopBar(){
        mSend = (TextView) findViewById(R.id.add_send);
        mCancel = (TextView) findViewById(R.id.add_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initEmoji() {
        mEmojiLayout = findViewById(R.id.add_emojilayout);

        ImageView emojiImage = (ImageView) findViewById(R.id.add_emoji);
        emojiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                softInputHide();
                mEmojiLayout.setVisibility(View.VISIBLE);
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.add_viewpager);
        viewPager.setAdapter(new EmojiPagerAdapter(this, mEditText));
    }

    private void initGridView() {
        mGridView = (GridView) findViewById(R.id.add_gridview);
        mGridView.setNumColumns(3);
        mGridView.setAdapter(mGridAdapter = new AddImagesAdapter(this, mImages, mPictureListener));
    }

    private void initEditView(){
        String topic = getIntent().getStringExtra("topic");
        mEditText = (EditText) findViewById(R.id.add_edit);
        if (null != topic) {
            mEditText.setText(topic);
            mEditText.setSelection(topic.length());
        }
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmojiLayout.setVisibility(View.GONE);
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String  str = mEditText.getText().toString();
                //do something;
                Log.d("DEBUG", "onEditorAction");
                if(str == null || "".equals(str)){
                    mSend.setBackgroundResource(R.drawable.red_add);
                    mSend.setTextColor(getResources().getColor(R.color.colorTextLightGray));
                    mSend.setOnClickListener(null);
                }else{
                    mSend.setBackgroundResource(R.drawable.red_addsend);
                    mSend.setTextColor(Color.WHITE);
                    mSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            mImages.remove(mImages.size() - 1);
                            intent.putStringArrayListExtra("images", mImages);
                            intent.putExtra("content", mEditText.getText().toString());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }
            }
        });
    }


    private void initPicture() {
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View picture = findViewById(R.id.add_picture);
        initPictureListener();
        picture.setOnClickListener(mPictureListener);
    }

    private void initPictureListener() {
        mPictureListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                softInputHide();
                View contentView = LayoutInflater.from(AddRedActivity.this).inflate(R.layout.popu_picture, null);

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

                View camera = contentView.findViewById(R.id.popu_takephoto);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getAvailableNum() > 0) {
                            takePhoto(v);
                            popupWindow.dismiss();
                        } else {
                            Toast.makeText(AddRedActivity.this, "最多发送9张照片", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                View pick = contentView.findViewById(R.id.popu_pickphoto);
                pick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getAvailableNum() > 0) {
                            pickPhoto(v);
                            popupWindow.dismiss();
                        } else {
                            Toast.makeText(AddRedActivity.this, "最多发送9张照片", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
    }



    private void softInputShow() {
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        mImm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
    }

    private void softInputHide() {
        mImm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }




    public void takePhoto(View view) {
        String dirStr = Environment.getExternalStorageDirectory()+"/redhard/photo";

        File dir = new File(dirStr);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File outputImage = new File(dirStr, getFileName());
        if (outputImage.exists()) {
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);

        Log.e("DEBUG", "imageUri = "+imageUri);

    }

    private String getFileName() {
        return String.valueOf(System.currentTimeMillis()) + ".jpg";
    }

    public void pickPhoto(View view) {

        Intent intent = new Intent(this, ImageSelectorActivity.class);

        intent.putExtra("maxNum", getAvailableNum());
        startActivityForResult(intent, PICK_PHOTO);

    }

    private int getAvailableNum() {
        int maxNum = 0;
        int size = mImages.size();
        if(size == 0) {
            maxNum = 9;
        } else if (size >= 1 && size <= 9) {
            maxNum = 9 - size + 1;
        }
        return maxNum;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("DEBUG", "imageUri = "+imageUri);

        switch (requestCode) {
            case TAKE_PHOTO:
                if (RESULT_OK == resultCode) {
                    addPictureItem(imageUri.toString());
                }
                break;
            case PICK_PHOTO:
                if (RESULT_OK == resultCode) {
                    List<String> images = data.getStringArrayListExtra("images");
                    for (String uri : images) {
                        addPictureItem(uri);
                    }
                }
                break;
            default:
                Log.e("DEBUG", "requestCode = "+requestCode);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void addPictureItem(String uri) {
        mImages.add(mImages.size()-1, uri);
        mGridAdapter.notifyDataSetChanged();
    }


    private void initImages() {
        mImages = new ArrayList<>();
        mImages.add("drawable://" + R.mipmap.add_grid);

    }


}
