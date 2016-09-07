package com.example.santa.redhare.chatactivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santa.redhare.R;
import com.example.santa.redhare.emoji.EmojiPagerAdapter;
import com.example.santa.redhare.imageselect.ImageSelectorActivity;
import com.example.santa.redhare.record.RecordTextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santa on 16/8/6.
 */
public class ChatActivity extends AppCompatActivity {
    private ArrayList<ChatItem> mData;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private EditText editText;
    private ViewPager viewPager;
    private InputMethodManager mImm;


    //emoji
    private ImageView mImageEmoji;
    private View mEmojiLayout;
    private View mPlusLayout;


    //record
    private RecordTextView mRecordText;

    //camera
    private final static int TAKE_PHOTO = 1;
    private final static int PICK_PHOTO = 2;

    private Uri imageUri;
    String mName;
    String mTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mTime = intent.getStringExtra("time");

        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);



        initRecyclerView();
        initEdit();
        initEmoji();
        initRecord();
        initPlus();
        initTopBar();

    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.chat_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new ChatAdapter(this, getData()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    hideAll();
                    editText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }


        });
    }

    private void initEdit() {
        editText = (EditText) findViewById(R.id.chat_edit);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    recyclerView.smoothScrollToPosition(mData.size() - 1);

                }
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String  str = editText.getText().toString();
                if (actionId== EditorInfo.IME_ACTION_SEND) {
                    //do something;
                    if(str == null || "".equals(str)){
                        Toast.makeText(ChatActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                        return true;
                    }else{
                        addChatItem(str);
                        v.setText("");
                    }
                    return true;
                }
                return false;
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG","hide all" );

                hideAll();
                editText.setVisibility(View.VISIBLE);
                softInputShow();
            }
        });
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    private void initTopBar() {
        View reback = findViewById(R.id.chat_reback);
        assert reback != null;
        reback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView name = (TextView) findViewById(R.id.chat_name);
        name.setText(mName);
    }

    private void initPlus() {
        mPlusLayout = findViewById(R.id.chat_plus);
        ImageView imageView = (ImageView) findViewById(R.id.chat_plusImage);
        assert imageView != null;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlusLayout.getVisibility() == View.GONE) {
                    hideAll();
                    editText.setVisibility(View.VISIBLE);
                    mPlusLayout.setVisibility(View.VISIBLE);
                } else {
                    hideAll();
                    editText.setVisibility(View.VISIBLE);
                    mPlusLayout.setVisibility(View.GONE);
                }
                setEditFocus();
            }
        });

        View camera = findViewById(R.id.chat_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });

        View album = findViewById(R.id.chat_album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto(v);
            }
        });


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
        startActivityForResult(intent, PICK_PHOTO);

    }


    private void addPictureItem(String imageUri) {
        ChatItem chatItem = new ChatItem();
        chatItem.setDirection(ChatStroke.DIR_RIGHT);
        chatItem.setPictureUri(imageUri);
        mData.add(chatItem);
        mAdapter.notifyItemInserted(mData.size() - 1);
        recyclerView.smoothScrollToPosition(mData.size() - 1);
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
        mPlusLayout.setVisibility(View.GONE);
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initEmoji(){
        mEmojiLayout = findViewById(R.id.chat_emojilayout);


        viewPager = (ViewPager) findViewById(R.id.chat_viewpager);

        viewPager.setAdapter(new EmojiPagerAdapter(this, editText));

        mImageEmoji = (ImageView) findViewById(R.id.chat_emojiImage);
        mImageEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmojiLayout.getVisibility() == View.GONE) {
                    hideAll();
                    editText.setVisibility(View.VISIBLE);
                    mEmojiLayout.setVisibility(View.VISIBLE);
                } else {
                    hideAll();
                    editText.setVisibility(View.VISIBLE);
                    softInputShow();
                }
                setEditFocus();
            }
        });

        View send = findViewById(R.id.chat_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if(str == null || "".equals(str)) {
                    Toast.makeText(ChatActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                addChatItem(str);
                editText.setText("");
            }
        });

    }


    private void initRecord() {
        mRecordText = (RecordTextView) findViewById(R.id.chat_recordText);
        mRecordText.setOnFinishRecordListener(new RecordTextView.OnFinishRecordListener() {
            @Override
            public void onFinish(int time, String filePath) {
                addRecordItem(time+"s", filePath);
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.chat_recordImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecordText.getVisibility() == View.GONE) {
                    hideAll();
                    editText.setVisibility(View.GONE);
                    ((ImageView)v).setImageResource(R.mipmap.chatting_softkeyboard);
                    mRecordText.setVisibility(View.VISIBLE);
                } else {
                    hideAll();
                    ((ImageView)v).setImageResource(R.mipmap.chatting_vodie);
                    editText.setVisibility(View.VISIBLE);
                    softInputShow();
                }
            }
        });


    }


    private void hideAll() {
        softInputHide();
        mEmojiLayout.setVisibility(View.GONE);
        mRecordText.setVisibility(View.GONE);
//        editText.setVisibility(View.GONE);
        mPlusLayout.setVisibility(View.GONE);
    }

    private void setEditFocus() {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }


    private void softInputShow() {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        mImm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    private void softInputHide() {
        mImm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }



    private void addRecordItem(String text, String filePath) {
        ChatItem chatItem = new ChatItem();
        chatItem.setText(text);
        chatItem.setDirection(ChatStroke.DIR_RIGHT);
        if ( null != filePath) {
            chatItem.setImageId(R.drawable.sound_all);
            chatItem.setFilePath(filePath);
        }
        mData.add(chatItem);
        mAdapter.notifyItemInserted(mData.size() - 1);
        recyclerView.smoothScrollToPosition(mData.size() - 1);
    }

    private void addChatItem(String text) {
        addRecordItem(text, null);
    }

    private ArrayList<ChatItem> getData() {
        mData = new ArrayList<>();
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setText("我通过了你的添加好友请求，我们开始聊天吧");
            chatItem.setTime(mTime);
            mData.add(chatItem);
        }
        if (!mName.equals("赵丹")) {
            return mData;
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setText("有病");
            chatItem.setTime("07月03日 12:02");
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setText("你有药？");
            chatItem.setDirection(ChatStroke.DIR_RIGHT);
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setText("哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢哥哥呢");
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setDirection(ChatStroke.DIR_RIGHT);
            chatItem.setText("睡觉呢");
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setText("你在干嘛啊？");
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setText("你在干嘛啊？？？？？？");
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setDirection(ChatStroke.DIR_RIGHT);
            chatItem.setText("写代码呢，写代码呢，写代码呢，写代码呢，写代码呢，写代码呢，写代码呢，写代码呢，写代码呢，写代码呢，写代码呢，写代码呢，写代码呢，写代码呢，写代码呢");
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setDirection(ChatStroke.DIR_RIGHT);
            chatItem.setText("吃晚饭没啊\n吃了啥？");
            chatItem.setTime("08月03日 16:03");
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setText("吃了面条");
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setText("你呢");
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setDirection(ChatStroke.DIR_RIGHT);
            chatItem.setText("肉");
            mData.add(chatItem);
        }
        {
            ChatItem chatItem = new ChatItem();
            chatItem.setDirection(ChatStroke.DIR_RIGHT);
            chatItem.setText("肉肉");
            mData.add(chatItem);
        }
        return mData;
    }


}
