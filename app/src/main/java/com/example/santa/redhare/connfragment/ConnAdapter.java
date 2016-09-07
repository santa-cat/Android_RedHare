package com.example.santa.redhare.connfragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.santa.redhare.R;
import com.example.santa.redhare.personactivity.PersonActivity;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/3.
 */
public class ConnAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Conn> mData;
    public ConnAdapter(Context context, ArrayList<Conn> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ConnHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_conn, parent, false);
            holder = new ConnHolder();
            holder.name = (TextView) convertView.findViewById(R.id.conn_name);
            holder.du = (TextView) convertView.findViewById(R.id.conn_du);
            holder.company = (TextView) convertView.findViewById(R.id.conn_company);
            holder.station = (TextView) convertView.findViewById(R.id.conn_station);
            holder.relation = (TextView) convertView.findViewById(R.id.conn_relation);
            holder.addFriend = convertView.findViewById(R.id.conn_addFriend);
            holder.headportrait = convertView.findViewById(R.id.conn_headportrait);
            convertView.setTag(holder);
        } else {
            holder = (ConnHolder) convertView.getTag();
        }

        holder.name.setText(mData.get(position).getName());
        holder.du.setText(mData.get(position).getDu());
        holder.company.setText(mData.get(position).getCompany());
        holder.station.setText(mData.get(position).getStation());
        holder.relation.setText(mData.get(position).getRelation());
        holder.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.headportrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersonActivity.class);
                intent.putExtra("name", mData.get(position).getName());
                intent.putExtra("company", mData.get(position).getCompany());
                intent.putExtra("station", mData.get(position).getStation());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }


    public class ConnHolder {
        private TextView name;
        private TextView du;
        private TextView company;
        private TextView station;
        private TextView relation;
        private View addFriend;
        private View headportrait;

    }
}
