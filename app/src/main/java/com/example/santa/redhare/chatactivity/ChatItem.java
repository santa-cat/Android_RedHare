package com.example.santa.redhare.chatactivity;

/**
 * Created by santa on 16/8/6.
 */
public class ChatItem {
    private String time;
    private int direction;

    //下面两个任意一种
    private String text;

    //for record
    private int imageId;
    private String filePath;

    //for picure
    private String pictureUri;


    public ChatItem() {
        direction = ChatStroke.DIR_LEFT;
        imageId = -1;
    }

    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
