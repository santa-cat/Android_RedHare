package com.example.santa.redhare.addressactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.santa.redhare.R;
import com.example.santa.redhare.connfragment.Conn;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/6.
 */
public class AddressActivity extends AppCompatActivity {

    private ArrayList<Conn> list;
    private ArrayList<Integer> tags;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        initAddrData();
        recyclerView = (RecyclerView) findViewById(R.id.addr_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new AddressAdapter(this, list, tags));

        AddrSideIdx addrSideIdx = (AddrSideIdx) findViewById(R.id.addr_idx);
        addrSideIdx.setTags(getTags());
        addrSideIdx.setOnSlideListener(new AddrSideIdx.OnSlideListener() {
            @Override
            public void onSlide(String text) {
//                Log.d("DEBUG", text);
                scrollToText(text);
            }

            @Override
            public void onSlideToTop() {
                recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onSlideToBottom() {
                int count = recyclerView.getAdapter().getItemCount();
                recyclerView.smoothScrollToPosition(count - 1);

            }
        });

        View reback = findViewById(R.id.addr_reback);
        reback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private ArrayList<String> getTags(){
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0 ; i<tags.size(); i++) {
            if (tags.get(i) == AddressAdapter.TAG_TAG) {
                strings.add(list.get(i).getName());
            }
        }
        return strings;
    }

    private void scrollToText(String text) {
        if(null != recyclerView) {
            for (int i = 0; i<tags.size(); i++ ) {
                int tag = tags.get(i);
                if (tag == AddressAdapter.TAG_TAG && list.get(i).getName().equals(text)) {
                    recyclerView.smoothScrollToPosition(i);
                }
            }
        }
    }

    private void initAddrData() {
        list = new ArrayList<>();
        tags = new ArrayList<>();
        {
            Conn conn = new Conn();
            conn.setName("赤兔小秘书");
            conn.setCompany("赤兔官方小助手竭诚为您服务～");
            conn.setStation("");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("招聘小秘书");
            conn.setCompany("协助你解决投递、发布职位相关问题");
            conn.setStation("");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("F");
            tags.add(AddressAdapter.TAG_TAG);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("傅劲");
            conn.setCompany("阿里巴巴");
            conn.setStation("高级技术专家");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("富万国");
            conn.setCompany("华为");
            conn.setStation("软件开发");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("方华");
            conn.setCompany("腾讯科技");
            conn.setStation("软件工程师");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("方叶");
            conn.setCompany("蘑菇街");
            conn.setStation("运营");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("H");
            tags.add(AddressAdapter.TAG_TAG);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("何志涛");
            conn.setCompany("上海天禹");
            conn.setStation("开发人员");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("L");
            tags.add(AddressAdapter.TAG_TAG);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("刘燕致");
            conn.setCompany("网易游戏");
            conn.setStation("高级前端工程师");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("李明帝");
            conn.setCompany("杭州电子科技大学");
            conn.setStation("硕士");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("M");
            tags.add(AddressAdapter.TAG_TAG);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("孟秋敏");
            conn.setCompany("1号店");
            conn.setStation("软件开发工程师");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("毛毛");
            conn.setCompany("京东");
            conn.setStation("软件开发工程师");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("Q");
            tags.add(AddressAdapter.TAG_TAG);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("秦云云");
            conn.setCompany("淘宝");
            conn.setStation("资深UI设计师");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("秦勇");
            conn.setCompany("华为");
            conn.setStation("构架师");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("W");
            tags.add(AddressAdapter.TAG_TAG);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("王长宝");
            conn.setCompany("SheIn");
            conn.setStation("HR助理");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("王伍");
            conn.setCompany("猎娉");
            conn.setStation("HR助理");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("吴彩霞");
            conn.setCompany("knx");
            conn.setStation("猎头顾问");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("吴俊");
            conn.setCompany("爱德华");
            conn.setStation("业务代表");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("Y");
            tags.add(AddressAdapter.TAG_TAG);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("游鸿泽");
            conn.setCompany("高级开发专家");
            conn.setStation("高级开发专家");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("杨叶平");
            conn.setCompany("上海交通大学");
            conn.setStation("硕士");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("Z");
            tags.add(AddressAdapter.TAG_TAG);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("赵丹");
            conn.setCompany("香港城市大学");
            conn.setStation("语言研究");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("张帆");
            conn.setCompany("华为");
            conn.setStation("软件开发");
            tags.add(AddressAdapter.TAG_ADDR);
            list.add(conn);
        }
    }

}
