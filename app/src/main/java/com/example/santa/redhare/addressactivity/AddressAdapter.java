package com.example.santa.redhare.addressactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.santa.redhare.R;
import com.example.santa.redhare.connfragment.Conn;
import com.example.santa.redhare.personactivity.PersonActivity;

import java.util.ArrayList;

/**
 * Created by santa on 16/8/6.
 */
public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Conn> mData;
    private ArrayList<Integer> mTags;
    public final static int TAG_ADDR = 0;
    public final static int TAG_TAG = 1;



    public AddressAdapter(Context context, ArrayList<Conn> data, ArrayList<Integer> tags) {
        mContext = context;
        mData = data;
        mTags = tags;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TAG_ADDR) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_address, parent, false);
            return new AddrHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_addresstag, parent, false);
            return new TagHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TAG_ADDR) {
            ((AddrHolder)holder).name.setText(mData.get(position).getName());
            ((AddrHolder)holder).company.setText(mData.get(position).getCompany());
            ((AddrHolder)holder).station.setText(mData.get(position).getStation());
            ((AddrHolder)holder).allLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PersonActivity.class);
                    intent.putExtra("name", mData.get(position).getName());
                    intent.putExtra("company", mData.get(position).getCompany());
                    intent.putExtra("station", mData.get(position).getStation());
                    mContext.startActivity(intent);
                }
            });
        } else {
            ((TagHolder)holder).tag.setText(mData.get(position).getName());

        }
    }

    @Override
    public int getItemViewType(int position) {
        return mTags.get(position);
    }


    public class AddrHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView company;
        private TextView station;
        private View allLayout;

        public AddrHolder(View itemView) {
            super(itemView);
            allLayout = itemView;
            name = (TextView) itemView.findViewById(R.id.addr_name);
            company = (TextView) itemView.findViewById(R.id.addr_company);
            station = (TextView) itemView.findViewById(R.id.addr_station);
        }
    }
    public class TagHolder extends RecyclerView.ViewHolder{
        private TextView tag;

        public TagHolder(View itemView) {
            super(itemView);
            tag = (TextView) itemView.findViewById(R.id.addr_tag);

        }
    }

}
