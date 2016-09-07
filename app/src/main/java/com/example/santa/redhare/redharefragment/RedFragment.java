package com.example.santa.redhare.redharefragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.santa.redhare.addactivity.AddRedActivity;
import com.example.santa.redhare.pullrefresh.PullHandler;
import com.example.santa.redhare.pullrefresh.PullRefreshLayout;
import com.example.santa.redhare.R;
import com.example.santa.redhare.searchactivity.SearchActivity;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/2.
 */
public class RedFragment extends Fragment {
    private View self;
    private static RedFragment instance;
    private final static int ADD_TIP = 1;
    private ArrayList<RedItem> mDatas;
    private BaseAdapter mAdapter;

    public static RedFragment getInstance() {
        if (instance == null) {
            synchronized (RedFragment.class) {
                if (instance == null)
                    instance = new RedFragment();
            }
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (null == this.self) {
            this.self = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_red, container, false);
            Log.d("DEBUG","onCreateView");
            initListView(this.self);
            initSearch(this.self);
            initAddTip(this.self);
            initRefreshLayout(this.self);
        }

        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }

        return this.self;
    }

    private void initRefreshLayout(View view) {
        PullRefreshLayout prl = (PullRefreshLayout) view.findViewById(R.id.red_pullRefreshLayout);
        assert prl!=null;
        prl.setPullHandler(new PullHandler(){
            @Override
            public void onRefreshBegin(final PullRefreshLayout layout) {


                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从客户端获取数据

                        layout.refreshComplete();
                    }
                }, 2000);

            }

            @Override
            public void onRefreshFinshed() {
                //更新数据
                //addData();
            }
        });
    }

    private void initAddTip(View view) {
        View addTip = view.findViewById(R.id.red_addtip);
        addTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddRedActivity.class);
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

    private void initSearch(View view) {
        View sc = view.findViewById(R.id.red_sc);
        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
        });
    }

    private void initListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.red_listview);
        listView.setAdapter(mAdapter = new RedAdapter(getActivity(), getData()));

    }



    private ArrayList<RedItem> getData() {
        mDatas = new ArrayList<>();
        {
            RedItem item = new RedItem();
            item.setUserName("犹大");
            item.setTrade("互联网／软件");
            item.setJob("开发");
            item.setCompany("1号店");
            item.setStation("软件开发工程师");
            item.setSrc("好友");
            item.setContent("#参与讨论你所知道的乔布斯#乔布斯生活在美国“硅谷“附近，邻居都是惠普公司的职员。在这些人的影响下，乔布斯从小迷恋电子学。一个惠普的工程师看他如此痴迷，就推荐他参加惠普公司的“发现者俱乐部“，这是个专门为年轻工程师举办的聚会，每星期二晚上在公司的餐厅中举行。在一次聚会中，乔布斯第一次见到了电脑，他开始对计算机有了一个朦胧的认识");
            item.setLikeNum(123);
            ArrayList<String> images = new ArrayList<>();
            images.add("drawable://" + R.mipmap.op1);
            item.setImages(images);
            mDatas.add(item);
        }
        {
            RedItem item = new RedItem();
            item.setUserName("周二");
            item.setTrade("互联网／软件");
            item.setJob("开发");
            item.setCompany("领英中国");
            item.setStation("工程经理");
            item.setSrc("同行业");
            item.setLikeNum(2);
            ArrayList<String> images = new ArrayList<>();
            images.add("drawable://" + R.mipmap.op2);
            images.add("drawable://" + R.mipmap.op1);
            item.setImages(images);
            item.setContent("1955年乔布斯出生在旧金山，生母乔安娜未婚先孕，迫于舆论压力将儿子送给在激光仪器厂里当工人的保罗•乔布斯和妻子克拉拉。乔布斯23岁时，与大学同居女友生下私生女丽莎，后来曾一度拒认丽莎是自己的女儿。27岁时，他找回自己的亲妹妹，美国作家莫娜。乔布斯与妻子劳伦•鲍威尔相识于斯坦福大学，1991年喜结连理，育有3个子女。");
            mDatas.add(item);
        }

        {
            RedItem item = new RedItem();
            item.setUserName("张三");
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
            item.setUserName("李四");
            item.setTrade("互联网／软件");
            item.setJob("开发");
            item.setCompany("领英中国");
            item.setStation("工程经理");
            item.setSrc("同行业");
            item.setLikeNum(0);
            item.setContent("乔布斯一边上班，一边常常与沃兹尼亚克一道，在自家的小车\n打坐的乔布斯库里琢磨电脑。他们梦想能够拥有一台自己的计算机，可是当时市面上卖的都是商用的，且体积庞大，极其昂贵，于是他们准备自己开发。1976年在旧金山威斯康星计算机产品展销会上买到了6502芯片，带着6502芯片，两个年轻人在乔布斯家的车库里装好了第一台电脑");
            mDatas.add(item);
        }
        {
            RedItem item = new RedItem();
            item.setUserName("王五");
            item.setTrade("互联网／软件");
            item.setJob("开发");
            item.setCompany("领英中国");
            item.setStation("工程经理");
            item.setSrc("同行业");
            item.setLikeNum(0);
            item.setContent("乔布斯23岁时，与大学同居女友生下私生女丽莎，后来曾一度拒认丽莎是自己的女儿。27岁时，他找回自己的亲妹妹，美国作家莫娜。乔布斯与妻子劳伦•鲍威尔相识于斯坦福大学，1991年喜结连理，育有3个子女。");
            mDatas.add(item);
        }
        return mDatas;
    }


}
