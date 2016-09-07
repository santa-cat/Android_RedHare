package com.example.santa.redhare.commentactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santa.redhare.R;
import com.example.santa.redhare.utils.UnderlineText;
import com.example.santa.redhare.emoji.SpanStringUtils;
import com.example.santa.redhare.redharefragment.ImagesAdapterBuilder;
import com.example.santa.redhare.redharefragment.RedItem;
import com.example.santa.redhare.redharefragment.ShrinkText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santa on 16/8/4.
 */
public class CommActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private String[] mTitles = {"评论 11"};
    private UnderlineText share;
    private UnderlineText like;
    private UnderlineText comm;
    private List<Comment> shareData;
    private List<Comment> likeData;
    private List<Comment> commData;
    private List<Comment> data;
    private ListView listView;
    private BaseAdapter adapter;
    private View commBar;

    private EditText editText;
    private InputMethodManager mImm;
    private View editLayout;
    private RedItem mRedItem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        mRedItem = (RedItem) intent.getSerializableExtra("comm");
        initAllData();
        initViewPager();
        initShareListener();
        initLike();
        initComm();
        initTopBar();
    }

    private void initTopBar(){
        View reback = findViewById(R.id.comm_reback);
        assert reback != null;
        reback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initComm() {
        commBar = findViewById(R.id.comm_bar);
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editLayout = findViewById(R.id.comm_editlayout);
        editText = (EditText) findViewById(R.id.comm_edit);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String  str = editText.getText().toString();
                if (actionId== EditorInfo.IME_ACTION_SEND) {
                    //do something;
                    if(str == null || "".equals(str)){
                        Toast.makeText(CommActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                        return true;
                    }else{
                        addCommItem(str);
                        v.setText("");
                    }
                    return true;
                }
                return false;
            }
        });


        View comm = findViewById(R.id.com_comlayout);
        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commBar.setVisibility(View.GONE);
                editLayout.setVisibility(View.VISIBLE);
                softInputShow();
            }
        });
    }

    private void addCommItem(String text) {
        Comment comment = new Comment();
        comment.setName("张三");
        comment.setContent(text);
        comment.setTime("刚刚");
        commData.add(0, comment);
        data.add(0, comment);
        editLayout.setVisibility(View.GONE);
        commBar.setVisibility(View.VISIBLE);
        softInputHide();
        comm.setText("评论 "+commData.size());
        adapter.notifyDataSetChanged();
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


    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.comm_viewpager);
        mViewPager.setAdapter(new CommAdapter(getViews(), mTitles));

    }

    private void setContent(View view) {
        TextView userName = (TextView) view.findViewById(R.id.comm_username);
        TextView trade = (TextView) view.findViewById(R.id.comm_trade);
        TextView job = (TextView) view.findViewById(R.id.comm_job);
        TextView company = (TextView) view.findViewById(R.id.comm_company);
        TextView station = (TextView) view.findViewById(R.id.comm_station);
        TextView src = (TextView) view.findViewById(R.id.comm_src);
        ShrinkText content = (ShrinkText) view.findViewById(R.id.comm_connent);
        GridView images = (GridView) view.findViewById(R.id.comm_images);


        userName.setText(mRedItem.getUserName());
        trade.setText(mRedItem.getTrade());
        job.setText(mRedItem.getJob());
        company.setText(mRedItem.getCompany());
        station.setText(mRedItem.getStation());
        src.setText(mRedItem.getSrc());
        content.setText(SpanStringUtils.getTopicContent(this,
                SpanStringUtils.getEmojiContent(1, this, (int) (content.getTextSize() * 15 / 10), mRedItem.getContent())));
        content.setMovementMethod(LinkMovementMethod.getInstance());
        if (mRedItem.getImages() == null) {
            images.setAdapter(ImagesAdapterBuilder.getAdapter(this, new ArrayList<String>()));
            return;
        }

        //如果只有一张照片放大
        if (mRedItem.getImages().size() == 1) {
            images.setNumColumns(2);
        } else {
            images.setNumColumns(3);
        }
        images.setAdapter(ImagesAdapterBuilder.getAdapter(this, mRedItem.getImages()));
        images.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    }

    private List<View> getViews() {
        List<View> views = new ArrayList<>();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        listView = new ListView(this);
        listView.setLayoutParams(layoutParams);
        data.addAll(commData);
        adapter = new CommListAdapter(this, data);
        View header = LayoutInflater.from(this).inflate(R.layout.header_comm, null);
        setContent(header);
        share = (UnderlineText) header.findViewById(R.id.comm_share);
        like = (UnderlineText) header.findViewById(R.id.comm_like);
        comm = (UnderlineText) header.findViewById(R.id.comm_comm);
        comm.setChecked();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllUnderText();
                ((UnderlineText)v).setChecked();
                data.clear();
                data.addAll(shareData);
                adapter.notifyDataSetChanged();
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllUnderText();
                ((UnderlineText)v).setChecked();
                data.clear();
                data.addAll(likeData);
                adapter.notifyDataSetChanged();
            }
        });

        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllUnderText();
                ((UnderlineText)v).setChecked();
                data.clear();
                data.addAll(commData);
                adapter.notifyDataSetChanged();

            }
        });
        share.setText("转发 "+shareData.size());
        like.setText("赞 "+likeData.size());
        comm.setText("评论 "+commData.size());

        listView.addHeaderView(header);
        listView.setAdapter(adapter);
        views.add(listView);
        return views;
    }

    private void initShareListener() {
        View view = findViewById(R.id.com_share);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View contentView = LayoutInflater.from(CommActivity.this).inflate(R.layout.popu_share, null);

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
        });

    }


    private void initLike() {
        View layout = findViewById(R.id.com_biglayout);
        final ImageView imageView = (ImageView) layout.findViewById(R.id.com_big);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.mipmap.like_checked);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }
    private void resetAllUnderText(){
        share.setUnChecked();
        like.setUnChecked();
        comm.setUnChecked();
    }

    private void initAllData() {
        data = new ArrayList<>();
        getCommDatas();
        getShareDatas();
        getLikeDatas();
    }

    private List<Comment> getShareDatas() {
        shareData = new ArrayList<>();
        {
            Comment comment = new Comment();
            comment.setName("叶英");
            comment.setContent("A公司 销售经理");
            comment.setTime("");
            shareData.add(comment);
        }

        return shareData;
    }

    private List<Comment> getLikeDatas() {
        likeData = new ArrayList<>();
        {
            Comment comment = new Comment();
            comment.setName("叶英");
            comment.setContent("A公司 销售经理");
            comment.setTime("");
            likeData.add(comment);
        }
        {
            Comment comment = new Comment();
            comment.setName("朱栋");
            comment.setContent("B公司 开发");
            comment.setTime("");
            likeData.add(comment);
        }
        {
            Comment comment = new Comment();
            comment.setName("张欢");
            comment.setContent("C公司 经理");
            comment.setTime("");
            likeData.add(comment);
        }

        return likeData;
    }

    private List<Comment> getCommDatas() {
        commData = new ArrayList<>();
        {
            Comment comment = new Comment();
            comment.setName("叶英");
            comment.setContent("根本原因在于人");
            comment.setTime("昨天 21:35");
            commData.add(comment);
        }
        {
            Comment comment = new Comment();
            comment.setName("朱栋");
            comment.setContent("工资不涨身价涨了");
            comment.setTime("昨天 20:28");
            commData.add(comment);
        }
        {
            Comment comment = new Comment();
            comment.setName("龚伟斌");
            comment.setContent("是的");
            comment.setTime("昨天 16:35");
            commData.add(comment);
        }
        {
            Comment comment = new Comment();
            comment.setName("珊子");
            comment.setContent("跳槽也是需要勇气的");
            comment.setTime("16-8-9");
            commData.add(comment);
        }
        {
            Comment comment = new Comment();
            comment.setName("张欢");
            comment.setContent("转发一下");
            comment.setTime("16-8-9");
            commData.add(comment);
        }
        {
            Comment comment = new Comment();
            comment.setName("谢贾玲");
            comment.setContent("应该不会吧");
            comment.setTime("16-8-9");
            commData.add(comment);
        }
        return commData;
    }


    private class CommAdapter extends PagerAdapter {
        private List<View> mViews;
        private String[] mTitles;

        public CommAdapter(List<View> views, String[] titles) {
            mViews = views;
            mTitles = titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }
    }

}
