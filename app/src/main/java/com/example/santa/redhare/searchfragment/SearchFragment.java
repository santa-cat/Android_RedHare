package com.example.santa.redhare.searchfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.santa.redhare.activityactivity.ActivityActivity;
import com.example.santa.redhare.activityactivity.ActivityItem;
import com.example.santa.redhare.activityactivity.ActivityManager;
import com.example.santa.redhare.R;
import com.example.santa.redhare.topicactivity.TopicActivity;
import com.example.santa.redhare.searchactivity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santa on 16/8/2.
 */
public class SearchFragment extends Fragment {
    private View self;
    private static SearchFragment instance;
    private List<ActivityItem> mActivitys;

    public static SearchFragment getInstance() {
        if (instance == null) {
            synchronized (SearchFragment.class) {
                if (instance == null)
                    instance = new SearchFragment();
            }
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (null == this.self) {
            this.self = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search, container, false);
            initActivitys(this.self);
            initRecyclerView(this.self);
            initSearch(this.self);
            initTopics(this.self);
        }

        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }

        return this.self;
    }

    private void initTopics(View view) {
        View topicMain = view.findViewById(R.id.sc_topicMain);
        topicMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopicActivity.class);
                intent.putExtra("topic", "又到了奥运年，你有什么关于奥运会的美好记忆");
                startActivity(intent);
            }
        });
        TextView one = (TextView) view.findViewById(R.id.topic_one);
        TextView two = (TextView) view.findViewById(R.id.topic_two);
        TextView three = (TextView) view.findViewById(R.id.topic_three);
        TextView four = (TextView) view.findViewById(R.id.topic_four);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopicActivity.class);
                intent.putExtra("topic", ((TextView)v).getText());
                startActivity(intent);
            }
        };
        one.setOnClickListener(listener);
        two.setOnClickListener(listener);
        three.setOnClickListener(listener);
        four.setOnClickListener(listener);

    }

    private void initActivitys(View view){
        View atyMain = view.findViewById(R.id.sc_activity);
        atyMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityActivity.class);
                intent.putExtra("imageId", R.mipmap.activity1);
                startActivity(intent);
            }
        });

        mActivitys = ActivityManager.getActivitys();
        LinearLayout activitys = (LinearLayout) view.findViewById(R.id.sc_activitys);
        for (int i = 0 ; i<activitys.getChildCount(); i++) {
            View activity = activitys.getChildAt(i);
            final ActivityItem item = mActivitys.get(i);
            ImageView image = (ImageView) activity.findViewById(R.id.sc_atyimage);
            TextView num = (TextView) activity.findViewById(R.id.sc_atynum);
            TextView title = (TextView) activity.findViewById(R.id.sc_atytitle);
            TextView name = (TextView) activity.findViewById(R.id.sc_atyname);
            TextView time = (TextView) activity.findViewById(R.id.sc_atytime);

            image.setImageResource(item.getImageId());
            num.setText(item.getNumOfPeople());
            title.setText(item.getTitle());
            name.setText(item.getName()+" | "+item.getStation());
            time.setText(item.getTime());
            activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ActivityActivity.class);
                    intent.putExtra("imageId", item.getImageId());
                    startActivity(intent);
                }
            });
        }
    }

    private void initSearch(View view) {
        View sc = view.findViewById(R.id.sc_search);
        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
        });
    }


    private void initRecyclerView(View view) {
        AutoCycleView autoCycleView = (AutoCycleView) view.findViewById(R.id.sc_cycleview);
        autoCycleView.setViewList(getViews());
        autoCycleView.startCycle();
    }

    private ArrayList<View> getViews() {
        ArrayList<View> list = new ArrayList<>();
        {
            ActivityItem item = mActivitys.get(mActivitys.size()-1);
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(item.getImageId());
            list.add(imageView);
        }
        for (final ActivityItem item : mActivitys) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(item.getImageId());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ActivityActivity.class);
                    intent.putExtra("imageId", item.getImageId());
                    startActivity(intent);
                }
            });
            list.add(imageView);
        }
        {
            ActivityItem item = mActivitys.get(0);
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(item.getImageId());
            list.add(imageView);
        }
        return list;
    }
}
