package com.example.santa.redhare.commentactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.santa.redhare.R;

import java.util.List;

/**
 * Created by santa on 16/8/11.
 */
public class CommListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> mData;

    public CommListAdapter(Context context, List<Comment> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        CommHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comm, parent, false);
            holder = new CommHolder();
            holder.content = (TextView) convertView.findViewById(R.id.commi_content);
            holder.name = (TextView) convertView.findViewById(R.id.commi_name);
            holder.time = (TextView) convertView.findViewById(R.id.commi_time);
            convertView.setTag(holder);
        } else {
            holder = (CommHolder) convertView.getTag();
        }
        holder.content.setText(mData.get(position).getContent());
        holder.name.setText(mData.get(position).getName());
        holder.time.setText(mData.get(position).getTime());
        return convertView;
    }

    private class CommHolder{
        private TextView name;
        private TextView content;
        private TextView time;
    }

}
