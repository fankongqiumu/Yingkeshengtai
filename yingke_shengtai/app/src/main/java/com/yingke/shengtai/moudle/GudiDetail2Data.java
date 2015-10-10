package com.yingke.shengtai.moudle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-9-2.
 */
public class GudiDetail2Data implements Serializable {
    private static final long serialVersionUID = -1151437934816332344L;


    /**
     * id : 34
     * level : 3
     * parentid : 16
     * title : 互联网金融专业委员会
     * text : 互联网金融专业委员会
     * saleslist : [{"sid":"1","usertype":"60","name":"总业务管理员","displayname":"总业务管理员","sex":"1","mobile":"18610000001","devicenumber":"352XXXXXXXX68888","channelid":"1","channelname":"盈科总部","rate":"0.1","location":"北京","imid":"bes_root_salesmanager","token":"","managerid":"0","userids":"12,13,15,17,19,20,23,24,16,21,22,14"}]
     */

    private String id;
    private String level;
    private String parentid;
    private String title;
    private String text;
    private ArrayList<SaleslistEntity> saleslist;

    public void setId(String id) {
        this.id = id;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSaleslist(ArrayList<SaleslistEntity> saleslist) {
        this.saleslist = saleslist;
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

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public ArrayList<SaleslistEntity> getSaleslist() {
        return saleslist;
    }

    public static class SaleslistEntity implements  Serializable{
        private static final long serialVersionUID = -6529327671862244850L;
        /**
         * sid : 1
         * usertype : 60
         * name : 总业务管理员
         * displayname : 总业务管理员
         * sex : 1
         * mobile : 18610000001
         * devicenumber : 352XXXXXXXX68888
         * channelid : 1
         * channelname : 盈科总部
         * rate : 0.1
         * location : 北京
         * imid : bes_root_salesmanager
         * token :
         * managerid : 0
         * userids : 12,13,15,17,19,20,23,24,16,21,22,14
         */

        private String sid;
        private String usertype;
        private String name;
        private String displayname;
        private String sex;
        private String mobile;
        private String devicenumber;
        private String channelid;
        private String channelname;
        private String rate;
        private String location;
        private String imid;
        private String token;
        private String managerid;
        private String userids;

        public void setSid(String sid) {
            this.sid = sid;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setDevicenumber(String devicenumber) {
            this.devicenumber = devicenumber;
        }

        public void setChannelid(String channelid) {
            this.channelid = channelid;
        }

        public void setChannelname(String channelname) {
            this.channelname = channelname;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setImid(String imid) {
            this.imid = imid;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setManagerid(String managerid) {
            this.managerid = managerid;
        }

        public void setUserids(String userids) {
            this.userids = userids;
        }

        public String getSid() {
            return sid;
        }

        public String getUsertype() {
            return usertype;
        }

        public String getName() {
            return name;
        }

        public String getDisplayname() {
            return displayname;
        }

        public String getSex() {
            return sex;
        }

        public String getMobile() {
            return mobile;
        }

        public String getDevicenumber() {
            return devicenumber;
        }

        public String getChannelid() {
            return channelid;
        }

        public String getChannelname() {
            return channelname;
        }

        public String getRate() {
            return rate;
        }

        public String getLocation() {
            return location;
        }

        public String getImid() {
            return imid;
        }

        public String getToken() {
            return token;
        }

        public String getManagerid() {
            return managerid;
        }

        public String getUserids() {
            return userids;
        }
    }
}
