package com.yingke.shengtai.moudle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class CenterListData implements Serializable{
    private static final long serialVersionUID = -8650165900233185010L;


    /**
     * page : 1
     * count : 18
     * newslist : [{"id":"41","date":"2015-08-29","title":"盈科长沙分所成功中标湖南省建行个人类贷款委外催收服务采购项目","brief":"2015年7月8日，湖南省建行通过对湖南省内十余家律师事务所综合考察对比评判，由北京盈科（长沙）律师事务所汤敏佳、包凡律师等专业律师组成的建行催收服务团队最终成功中标\u201c湖南建行长沙地区个人类贷款委外催收服务采购项目\u201d。","text":""}]
     */

    private String page;
    private String count;
    private ArrayList<NewslistEntity> newslist;

    public void setPage(String page) {
        this.page = page;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setNewslist(ArrayList<NewslistEntity> newslist) {
        this.newslist = newslist;
    }

    public String getPage() {
        return page;
    }

    public String getCount() {
        return count;
    }

    public ArrayList<NewslistEntity> getNewslist() {
        return newslist;
    }

    public  class NewslistEntity implements Serializable {
        private static final long serialVersionUID = -4960832028772679315L;
        /**
         * id : 41
         * date : 2015-08-29
         * title : 盈科长沙分所成功中标湖南省建行个人类贷款委外催收服务采购项目
         * brief : 2015年7月8日，湖南省建行通过对湖南省内十余家律师事务所综合考察对比评判，由北京盈科（长沙）律师事务所汤敏佳、包凡律师等专业律师组成的建行催收服务团队最终成功中标“湖南建行长沙地区个人类贷款委外催收服务采购项目”。
         * text :
         */

        private String id;
        private String date;
        private String title;
        private String brief;
        private String text;
        private String img;

        public String getImageUrl(){
            return img;
        }

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
    }
}
