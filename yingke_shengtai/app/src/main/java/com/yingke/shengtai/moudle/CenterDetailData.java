package com.yingke.shengtai.moudle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-9-2.
 */
public class CenterDetailData implements Serializable {
    private static final long serialVersionUID = 816583007647239870L;


    /**
     * id : 41
     * date : 2015-08-29
     * title : 盈科长沙分所成功中标湖南省建行个人类贷款委外催收服务采购项目
     * brief :
     * text : 分所讯  2015年7月8日，
     */

    private String id;
    private String date;
    private String title;
    private String brief;
    private String text;
    private String channelname;
    private String total;

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    private ArrayList<Medialist> medialist;

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getBrief() {
        return brief;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Medialist> getMedialist() {
        return medialist;
    }

    public void setMedialist(ArrayList<Medialist> medialist) {
        this.medialist = medialist;
    }

    public class Medialist implements Serializable{

        private String id;
        private String type;
        private String url;
        private String width;
        private String height;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }
    }
}
