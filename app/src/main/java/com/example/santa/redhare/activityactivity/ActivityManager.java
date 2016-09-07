package com.example.santa.redhare.activityactivity;

import com.example.santa.redhare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santa on 16/8/20.
 */
public class ActivityManager {
    private static List<ActivityItem> mActivitys;


    public static List<ActivityItem> getActivitys(){
        if (null == mActivitys) {
            initActivity();
        }
        return mActivitys;
    }

    private static void initActivity(){
        mActivitys = new ArrayList<>();
        {
            ActivityItem activityItem = new ActivityItem();
            activityItem.setImageId(R.mipmap.activity2);
            activityItem.setName("林忆菁");
            activityItem.setTitle("哪种职业会是你的Mr.Right？");
            activityItem.setTime("09月21日 20:00");
            activityItem.setNumOfPeople("67人已报名");
            activityItem.setStation("英语新闻主播、记者");
            mActivitys.add(activityItem);
        }
        {
            ActivityItem activityItem = new ActivityItem();
            activityItem.setImageId(R.mipmap.activity3);
            activityItem.setName("杨子楠");
            activityItem.setTitle("十年，我待这份工作依旧如初");
            activityItem.setTime("09月14日 20:00");
            activityItem.setNumOfPeople("57人已报名");
            activityItem.setStation("CRI 主持人");
            mActivitys.add(activityItem);
        }
        {
            ActivityItem activityItem = new ActivityItem();
            activityItem.setImageId(R.mipmap.activity4);
            activityItem.setName("崔莉莎");
            activityItem.setTitle("拆出你的物权（四）");
            activityItem.setTime("09月10日 20:00");
            activityItem.setNumOfPeople("38人已报名");
            activityItem.setStation("合伙人律师");
            mActivitys.add(activityItem);
        }
        {
            ActivityItem activityItem = new ActivityItem();
            activityItem.setImageId(R.mipmap.activity1);
            activityItem.setName("于际敬");
            activityItem.setTitle("从0-1，教你成为职场精英");
            activityItem.setTime("08月25日 20:00");
            activityItem.setNumOfPeople("953人已报名");
            activityItem.setStation("剑桥大学 培训讲师");
            mActivitys.add(activityItem);
        }
    }

    public static ActivityItem getAtyByImageId(int imageId){
        for (ActivityItem item : mActivitys) {
            if (imageId == item.getImageId()) {
                return item;
            }
        }
        return null;
    }

}
