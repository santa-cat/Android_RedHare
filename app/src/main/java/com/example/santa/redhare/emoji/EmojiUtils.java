package com.example.santa.redhare.emoji;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/8.
 */
public class EmojiUtils {


    public final static int MAXNUM_ONEPAGER = 23;


    /**
     * 表情类型标志符
     */
    public static final int EMOTION_CLASSIC_TYPE=0x0001;//经典表情
    /**
     * key-表情文字;
     * value-表情图片资源
     */
    public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;
    
    static {
        EMOTION_CLASSIC_MAP = new ArrayMap<>();
        EMOTION_CLASSIC_MAP.put("[e1f600]", R.drawable.e1f600);
        EMOTION_CLASSIC_MAP.put("[e1f601]", R.drawable.e1f601);
        EMOTION_CLASSIC_MAP.put("[e1f602]", R.drawable.e1f602);
        EMOTION_CLASSIC_MAP.put("[e1f603]", R.drawable.e1f603);
        EMOTION_CLASSIC_MAP.put("[e1f604]", R.drawable.e1f604);
        EMOTION_CLASSIC_MAP.put("[e1f605]", R.drawable.e1f605);
        EMOTION_CLASSIC_MAP.put("[e1f606]", R.drawable.e1f606);
        EMOTION_CLASSIC_MAP.put("[e1f607]", R.drawable.e1f607);
        EMOTION_CLASSIC_MAP.put("[e1f608]", R.drawable.e1f608);
        EMOTION_CLASSIC_MAP.put("[e1f609]", R.drawable.e1f609);
        EMOTION_CLASSIC_MAP.put("[e1f610]", R.drawable.e1f610);
        EMOTION_CLASSIC_MAP.put("[e1f611]", R.drawable.e1f611);
        EMOTION_CLASSIC_MAP.put("[e1f612]", R.drawable.e1f612);
        EMOTION_CLASSIC_MAP.put("[e1f613]", R.drawable.e1f613);
        EMOTION_CLASSIC_MAP.put("[e1f614]", R.drawable.e1f614);
        EMOTION_CLASSIC_MAP.put("[e1f615]", R.drawable.e1f615);
        EMOTION_CLASSIC_MAP.put("[e1f616]", R.drawable.e1f616);
        EMOTION_CLASSIC_MAP.put("[e1f617]", R.drawable.e1f617);
        EMOTION_CLASSIC_MAP.put("[e1f618]", R.drawable.e1f618);
        EMOTION_CLASSIC_MAP.put("[e1f619]", R.drawable.e1f619);
        EMOTION_CLASSIC_MAP.put("[e1f620]", R.drawable.e1f620);
        EMOTION_CLASSIC_MAP.put("[e1f621]", R.drawable.e1f621);
        EMOTION_CLASSIC_MAP.put("[e1f622]", R.drawable.e1f622);
        EMOTION_CLASSIC_MAP.put("[e1f623]", R.drawable.e1f623);
        EMOTION_CLASSIC_MAP.put("[e1f624]", R.drawable.e1f624);
        EMOTION_CLASSIC_MAP.put("[e1f625]", R.drawable.e1f625);
        EMOTION_CLASSIC_MAP.put("[e1f626]", R.drawable.e1f626);
        EMOTION_CLASSIC_MAP.put("[e1f627]", R.drawable.e1f627);
        EMOTION_CLASSIC_MAP.put("[e1f628]", R.drawable.e1f628);
        EMOTION_CLASSIC_MAP.put("[e1f629]", R.drawable.e1f629);
        EMOTION_CLASSIC_MAP.put("[e1f630]", R.drawable.e1f630);
        EMOTION_CLASSIC_MAP.put("[e1f631]", R.drawable.e1f631);
        EMOTION_CLASSIC_MAP.put("[e1f632]", R.drawable.e1f632);
        EMOTION_CLASSIC_MAP.put("[e1f633]", R.drawable.e1f633);
        EMOTION_CLASSIC_MAP.put("[e1f634]", R.drawable.e1f634);
        EMOTION_CLASSIC_MAP.put("[e1f635]", R.drawable.e1f635);
        EMOTION_CLASSIC_MAP.put("[e1f636]", R.drawable.e1f636);
        EMOTION_CLASSIC_MAP.put("[e1f637]", R.drawable.e1f637);
        EMOTION_CLASSIC_MAP.put("[e1f638]", R.drawable.e1f638);
        EMOTION_CLASSIC_MAP.put("[e1f639]", R.drawable.e1f639);
        EMOTION_CLASSIC_MAP.put("[e1f640]", R.drawable.e1f640);
        EMOTION_CLASSIC_MAP.put("[e1f641]", R.drawable.e1f641);
        EMOTION_CLASSIC_MAP.put("[e1f642]", R.drawable.e1f642);
        EMOTION_CLASSIC_MAP.put("[e1f643]", R.drawable.e1f643);
        EMOTION_CLASSIC_MAP.put("[e1f644]", R.drawable.e1f644);
//        EMOTION_CLASSIC_MAP.put(DELETE, R.drawable.edelete);
    }
    /**
     * 根据名称获取当前表情图标R值
     * @param EmotionType 表情类型标志符
     * @param name 索引
     * @return
     */
    public static int getImgByName(int EmotionType, String name) {
        
        Integer integer=null;
        switch (EmotionType){
            case EMOTION_CLASSIC_TYPE:
                integer = EMOTION_CLASSIC_MAP.get(name);
                break;
            default:
                Log.e("DEBUG", "the emojiMap is null!!");
                break;
        }
        return integer == null ? -1 : integer;
    }
    /**
     * 根据类型获取表情数据
     * @param EmotionType
     * @return
     */
    public static ArrayMap<String ,Integer> getEmojis(int EmotionType){
        ArrayMap<String ,Integer> Emojimap=null;
        switch (EmotionType){
            case EMOTION_CLASSIC_TYPE:
                Emojimap=EMOTION_CLASSIC_MAP;
                break;
            default:
                Log.e("DEBUG", "the emojiMap is null!!");
                break;
        }
        return Emojimap;
    }





}
