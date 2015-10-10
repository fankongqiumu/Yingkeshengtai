package com.yingke.shengtai.moudle;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class GuideDetailListData implements Serializable {
    private static final long serialVersionUID = 5363573108529320249L;



    private String title;
    @SerializedName("id")
    private String currentId;
    private String level;
    private String parentid;
    private String text;
    @SerializedName("subchannelinfo")
    private ArrayList<GuideListData> list;

    public void setLevel(String level) {
        this.level = level;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLevel() {
        return level;
    }

    public String getParentid() {
        return parentid;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public ArrayList<GuideListData> getList() {
        return list;
    }

    public void setList(ArrayList<GuideListData> list) {
        this.list = list;
    }
}
