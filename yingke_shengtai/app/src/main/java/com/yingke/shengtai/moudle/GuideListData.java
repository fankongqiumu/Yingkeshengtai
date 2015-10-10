package com.yingke.shengtai.moudle;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class GuideListData implements Serializable {
    private static final long serialVersionUID = 2402779157223097229L;

    @SerializedName("id")
    private String currentId;
    private String level;
    private String parentid;
    @SerializedName("shorttitle")
    private String title;
    private String brief;
    private String text;
    private ArrayList<CenterDetailData.Medialist> medialist;
    private ArrayList<Subchannelinfo> subchannelinfo;

    public String getCurrentId() {
        return currentId;
    }

    public String getLevel() {
        return level;
    }

    public String getParentid() {
        return parentid;
    }

    public String getTitle() {
        return title;
    }

    public String getBrief() {
        return brief;
    }

    public String getText(){
        return text;
    }

    public ArrayList<CenterDetailData.Medialist> getMedialist(){
        return medialist;
    }

    public ArrayList<Subchannelinfo> getSubchannelinfo() {
        return subchannelinfo;
    }

    public void setSubchannelinfo(ArrayList<Subchannelinfo> subchannelinfo) {
        this.subchannelinfo = subchannelinfo;
    }

    public class Subchannelinfo{

        /**
         * id : 47
         * level : 3
         * parentid : 19
         * shorttitle : 政府法律
         * title : 政府法律顾问专业委员会
         * brief : 政府法律顾问专业委员会
         */

        private String id;
        private String level;
        private String parentid;
        private String shorttitle;
        private String title;
        private String brief;

        public void setId(String id) {
            this.id = id;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public void setShorttitle(String shorttitle) {
            this.shorttitle = shorttitle;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getId() {
            return id;
        }

        public String getLevel() {
            return level;
        }

        public String getParentid() {
            return parentid;
        }

        public String getShorttitle() {
            return shorttitle;
        }

        public String getTitle() {
            return title;
        }

        public String getBrief() {
            return brief;
        }
    }
}
