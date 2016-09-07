package com.example.santa.redhare.messagefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.santa.redhare.R;
import com.example.santa.redhare.searchactivity.SearchActivity;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/2.
 */
public class MessageFragment extends Fragment {
    private View self;
    private static MessageFragment instance;

    public static MessageFragment getInstance() {
        if (instance == null) {
            synchronized (MessageFragment.class) {
                if (instance == null)
                    instance = new MessageFragment();
            }
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (null == this.self) {
            this.self = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_message, container, false);
            initRecyclerView(this.self);
            initSearch(this.self);
        }

        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }

        return this.self;
    }

    private void initSearch(View view) {
        View sc = view.findViewById(R.id.msg_search);
        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
        });
    }


    private void initRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.msg_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MsgAdapter(getActivity(), getMsgData()));

    }

    private ArrayList<MsgInfo> getMsgData() {
        ArrayList<MsgInfo> list = new ArrayList<>();
        {
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setName("招聘小秘书");
            list.add(msgInfo);
        }
        {
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setName("王长宝");
            msgInfo.setContent("你好，不知道您现在看机会吗");
            msgInfo.setTime("07月21日 11:07");
            list.add(msgInfo);
        }
        {
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setName("刘燕政");
            msgInfo.setContent("我通过了你的添加好友请求，我们开始聊天吧");
            msgInfo.setTime("07月17日 15:02");
            list.add(msgInfo);
        }
        {
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setName("蒙秋敏");
            msgInfo.setContent("我通过了你的添加好友请求，我们开始聊天吧");
            msgInfo.setTime("07月17日 14:30");
            list.add(msgInfo);
        }
        {
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setName("游鸿泽");
            msgInfo.setContent("我通过了你的添加好友请求，我们开始聊天吧");
            msgInfo.setTime("07月17日 12:07");
            list.add(msgInfo);
        }
        {
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setName("赵丹");
            msgInfo.setContent("哈哈");
            msgInfo.setTime("07月03日 11:07");
            list.add(msgInfo);
        }
        {
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setName("哥哥");
            msgInfo.setContent("哈咯");
            msgInfo.setTime("07月01日 11:07");
            list.add(msgInfo);
        }
        return list;
    }


}
