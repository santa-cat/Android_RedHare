package com.example.santa.redhare.topicactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.santa.redhare.R;
import com.example.santa.redhare.addactivity.AddRedActivity;
import com.example.santa.redhare.redharefragment.RedAdapter;
import com.example.santa.redhare.redharefragment.RedItem;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/7.
 */
public class TopicActivity extends AppCompatActivity {
    private ListView listView;
    private View topBar;

    private Drawable topBarDrawable;
    private View rebackIcon;
    private View moreIcon;
    private View rebackText;
    private View moreText;
    private TextView title;

    private String titleContent;
    private final static int ADD_TIP = 1;
    private ArrayList<RedItem> mDatas;
    private BaseAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        titleContent = "#"+getIntent().getStringExtra("topic")+"#";

        initTopBar();
        listView = (ListView) findViewById(R.id.topic_list);
        View header = LayoutInflater.from(this).inflate(R.layout.topic_header, null);
        TextView title = (TextView) header.findViewById(R.id.topic_title);
        title.setText(titleContent);

        listView.addHeaderView(header);
        listView.setAdapter(mAdapter = new RedAdapter(this, getData()));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount > 0 && firstVisibleItem == 0) {
                    View header = view.getChildAt(0);
                    int imageHeight = header.findViewById(R.id.tp_headerLayout).getHeight();
                    int height = view.getChildAt(0).getHeight();
                    int bottom = view.getChildAt(0).getBottom();
                    Log.d("DEBUG", "headBottom = " + bottom);
                    Log.d("DEBUG", "height = " + height);

                    float percent = bottom > (height-imageHeight) ? (bottom - (height-imageHeight))*1.0f/imageHeight : 0;
                    setTopBarAlpha(percent);
                    Log.d("DEBUG", "percent = " + percent);

                }

            }
        });
        initJoinTopic();
    }
    private void initJoinTopic() {
        View view = findViewById(R.id.topic_join);
        assert view != null;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicActivity.this, AddRedActivity.class);
                intent.putExtra("topic", titleContent);
                startActivityForResult(intent, ADD_TIP);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_TIP:
                if (Activity.RESULT_OK == resultCode) {
                    ArrayList<String> images = data.getStringArrayListExtra("images");
                    String content = data.getStringExtra("content");
                    addRedItem(images, content);
                }
                break;
            default:
                Log.e("DEBUG", "requestCode = "+requestCode);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void addRedItem(ArrayList<String> images, String content) {
        RedItem item = new RedItem();
        item.setUserName("张三");
        item.setTrade("互联网／软件");
        item.setJob("开发");
        item.setCompany("华为");
        item.setStation("开发");
        item.setSrc("自己");
        item.setContent(content);
        item.setLikeNum(0);
        item.setImages(images);
        mDatas.add(0, item);
        mAdapter.notifyDataSetChanged();
    }

    private void setTopBarAlpha(float alpha){
        if (null == topBarDrawable || null == rebackIcon || null == rebackText
                || null == moreIcon || null == moreText) {
            return;
        }
        if (null != topBarDrawable) {
            topBarDrawable.setAlpha((int) ((1-alpha)*255));
        }

        if (alpha >= 0.1) {
            rebackIcon.setVisibility(View.VISIBLE);
            moreIcon.setVisibility(View.VISIBLE);
            rebackText.setVisibility(View.GONE);
            moreText.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
        } else {
            rebackIcon.setVisibility(View.GONE);
            moreIcon.setVisibility(View.GONE);
            rebackText.setVisibility(View.VISIBLE);
            moreText.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
        }
    }

    private void initTopBar() {
        topBarDrawable = getResources().getDrawable(R.drawable.person_bg);
        topBar  = findViewById(R.id.topic_topBar);
        topBar.setBackgroundDrawable(topBarDrawable);

        rebackIcon = findViewById(R.id.topic_rbIcon);
        rebackText = findViewById(R.id.topic_rbText);
        moreIcon = findViewById(R.id.topic_moreIcon);
        moreText = findViewById(R.id.topic_moreText);
        title = (TextView) findViewById(R.id.topic_titleTop);
        title.setText(titleContent);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        rebackIcon.setOnClickListener(listener);
        rebackText.setOnClickListener(listener);


    }

    private ArrayList<RedItem> getData() {
        mDatas = new ArrayList<>();
        {
            RedItem item = new RedItem();
            item.setUserName("周哲");
            item.setTrade("互联网／软件");
            item.setJob("开发");
            item.setCompany("领英中国");
            item.setStation("工程经理");
            item.setSrc("同行业");
            item.setLikeNum(2);
            ArrayList<String> images = new ArrayList<>();
            images.add("drawable://" + R.mipmap.op1);
            images.add("drawable://" + R.mipmap.op2);
            item.setImages(images);
            item.setContent("1955年乔布斯出生在旧金山，生母乔安娜未婚先孕，迫于舆论压力将儿子送给在激光仪器厂里当工人的保罗•乔布斯和妻子克拉拉。乔布斯23岁时，与大学同居女友生下私生女丽莎，后来曾一度拒认丽莎是自己的女儿。27岁时，他找回自己的亲妹妹，美国作家莫娜。乔布斯与妻子劳伦•鲍威尔相识于斯坦福大学，1991年喜结连理，育有3个子女。");
            mDatas.add(item);
        }
        {
            RedItem item = new RedItem();
            item.setUserName("犹大");
            item.setTrade("互联网／软件");
            item.setJob("开发");
            item.setCompany("1号店");
            item.setStation("软件开发工程师");
            item.setSrc("好友");
            item.setContent("乔布斯生活在美国“硅谷“附近，邻居都是惠普公司的职员。在这些人的影响下，乔布斯从小迷恋电子学。一个惠普的工程师看他如此痴迷，就推荐他参加惠普公司的“发现者俱乐部“，这是个专门为年轻工程师举办的聚会，每星期二晚上在公司的餐厅中举行。在一次聚会中，乔布斯第一次见到了电脑，他开始对计算机有了一个朦胧的认识");
            item.setLikeNum(123);
            mDatas.add(item);
        }
        {
            RedItem item = new RedItem();
            item.setUserName("二货");
            item.setTrade("金融");
            item.setJob("证券");
            item.setCompany("宜信财富");
            item.setStation("经理");
            item.setSrc("小编推荐");
            item.setContent("1955年2月24日，史蒂夫·乔布斯出生在美国旧金山。刚刚出世就\n" +
                    "青年乔布斯\n" +
                    "青年乔布斯\n" +
                    "被父母遗弃了。幸运的是，保罗·乔布斯和克拉拉·乔布斯——一对好心的夫妻领养了他");
            mDatas.add(item);
        }
        {
            RedItem item = new RedItem();
            item.setUserName("周哲");
            item.setTrade("互联网／软件");
            item.setJob("开发");
            item.setCompany("领英中国");
            item.setStation("工程经理");
            item.setSrc("同行业");
            item.setLikeNum(0);
            item.setTitle("全部动态");
            item.setContent("乔布斯一边上班，一边常常与沃兹尼亚克一道，在自家的小车\n打坐的乔布斯库里琢磨电脑。他们梦想能够拥有一台自己的计算机，可是当时市面上卖的都是商用的，且体积庞大，极其昂贵，于是他们准备自己开发。1976年在旧金山威斯康星计算机产品展销会上买到了6502芯片，带着6502芯片，两个年轻人在乔布斯家的车库里装好了第一台电脑");
            mDatas.add(item);
        }
        {
            RedItem item = new RedItem();
            item.setUserName("周哲");
            item.setTrade("互联网／软件");
            item.setJob("开发");
            item.setCompany("领英中国");
            item.setStation("工程经理");
            item.setSrc("同行业");
            item.setContent("哈哈哈哈");
            ArrayList<String> images = new ArrayList<>();
            item.setImages(images);
            mDatas.add(item);
        }
        return mDatas;
    }

}
