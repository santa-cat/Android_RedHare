package com.example.santa.redhare.connfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.santa.redhare.R;
import com.example.santa.redhare.addressactivity.AddressActivity;
import com.example.santa.redhare.searchactivity.SearchActivity;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/2.
 */
public class ConnentionFragment extends Fragment {
    private View self;
    private static ConnentionFragment instance;

    public static ConnentionFragment getInstance() {
        if (instance == null) {
            synchronized (ConnentionFragment.class) {
                if (instance == null)
                    instance = new ConnentionFragment();
            }
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == this.self) {
            this.self = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_connection, container, false);
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
        View sc = view.findViewById(R.id.conn_search);
        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
        });
    }

    private void initRecyclerView(View view) {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_conngroup, null);
        View myFrends = header.findViewById(R.id.conn_myFriends);
        myFrends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });
        ListView listView = (ListView) view.findViewById(R.id.conn_listview);
        listView.addHeaderView(header);
        listView.setAdapter(new ConnAdapter(getActivity(), getGroupData()));

    }


    private ArrayList<Conn> getGroupData() {
        ArrayList<Conn> list = new ArrayList<>();
        {
            Conn conn = new Conn();
            conn.setName("曾强");
            conn.setDu("2度");
            conn.setCompany("华为");
            conn.setStation("高级软件工程师");
            conn.setRelation("1个共同好友,同事，同行业");
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("邹旺");
            conn.setDu("2度");
            conn.setCompany("华为");
            conn.setStation("java软件工程师");
            conn.setRelation("1个共同好友,同事，同行业");
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("周文");
            conn.setDu("2度");
            conn.setCompany("深圳深信服");
            conn.setStation("开发经理");
            conn.setRelation("1个共同好友,同事，同行业");
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("王伍");
            conn.setDu("3度");
            conn.setCompany("待业");
            conn.setStation("软件");
            conn.setRelation("1个共同好友");
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("曾强");
            conn.setDu("2度");
            conn.setCompany("华为");
            conn.setStation("高级软件工程师");
            conn.setRelation("1个共同好友,同事，同行业");
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("邹旺");
            conn.setDu("2度");
            conn.setCompany("华为");
            conn.setStation("java软件工程师");
            conn.setRelation("1个共同好友,同事，同行业");
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("周文");
            conn.setDu("2度");
            conn.setCompany("深圳深信服");
            conn.setStation("开发经理");
            conn.setRelation("1个共同好友,同事，同行业");
            list.add(conn);
        }
        {
            Conn conn = new Conn();
            conn.setName("张三");
            conn.setDu("3度");
            conn.setCompany("待业");
            conn.setStation("开发");
            conn.setRelation("1个共同好友");
            list.add(conn);
        }

        return list;
    }


}
