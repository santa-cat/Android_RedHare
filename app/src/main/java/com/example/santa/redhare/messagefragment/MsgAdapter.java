package com.example.santa.redhare.messagefragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.santa.redhare.R;
import com.example.santa.redhare.chatactivity.ChatActivity;
import com.example.santa.redhare.personactivity.PersonActivity;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/3.
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgHoloer> {
    private Context mContext;
    private ArrayList<MsgInfo> mData;

    public MsgAdapter(Context context, ArrayList<MsgInfo> list) {
        mContext = context;
        mData = list;
    }


    @Override
    public MsgHoloer onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_msg, parent, false);
        return new MsgHoloer(view);
    }

    @Override
    public void onBindViewHolder(MsgHoloer holder, final int position) {
        if (mData.get(position).getName().equals("招聘小秘书")) {
            holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bag));
            holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorBg));
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersonActivity.class);
                intent.putExtra("name", mData.get(position).getName());
                mContext.startActivity(intent);
            }
        });

        holder.name.setText(mData.get(position).getName());
        holder.content.setText(mData.get(position).getContent());
        holder.allLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("name", mData.get(position).getName());
                intent.putExtra("time", mData.get(position).getTime());
                mContext.startActivity(intent);
            }
        });
        holder.time.setText(mData.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MsgHoloer extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView content;
        private TextView time;
        private ImageView icon;
        private ViewGroup layout;
        private View allLayout;

        public MsgHoloer(View itemView) {
            super(itemView);
            allLayout = itemView;
            name = (TextView) itemView.findViewById(R.id.msg_name);
            content = (TextView) itemView.findViewById(R.id.msg_content);
            time = (TextView) itemView.findViewById(R.id.msg_time);
            icon = (ImageView) itemView.findViewById(R.id.msg_icon);
            layout = (ViewGroup) itemView.findViewById(R.id.msg_layout);
        }
    }
}
