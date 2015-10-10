package com.yingke.shengtai.moudle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class SaleCustomListData implements Serializable {
    private static final long serialVersionUID = 7663850938161822764L;


    /**
     * result : 1
     * message : 成功
     * page : 1
     * count : 4
     * userlist : [{"uid":"20","customerid":"CUS10020","usertype":"1","name":"测试用户8-9","displayname":"测试用户8-9","sex":"0","mobile":"13900000809","devicenumber":"865982027519803","location":"湖南 长沙","imid":"cus1390000080939e3","token":"771e692d7c3f4b3cac4f83d2c55e66e3","refereeid":"0","refereename":"","keyword":"null","regdate":"2015-08-29T14:51:31.663"}]
     */

    private String result;
    private String message;
    private String page;
    private String count;
    private ArrayList<UserlistEntity> userlist;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setUserlist(ArrayList<UserlistEntity> userlist) {
        this.userlist = userlist;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getPage() {
        return page;
    }

    public String getCount() {
        return count;
    }

    public ArrayList<UserlistEntity> getUserlist() {
        return userlist;
    }

    public static class UserlistEntity {
        /**
         * uid : 20
         * customerid : CUS10020
         * usertype : 1
         * name : 测试用户8-9
         * displayname : 测试用户8-9
         * sex : 0
         * mobile : 13900000809
         * devicenumber : 865982027519803
         * location : 湖南 长沙
         * imid : cus1390000080939e3
         * token : 771e692d7c3f4b3cac4f83d2c55e66e3
         * refereeid : 0
         * refereename :
         * keyword : null
         * regdate : 2015-08-29T14:51:31.663
         */

        private String uid;
        private String customerid;
        private String usertype;
        private String name;
        private String displayname;
        private String sex;
        private String mobile;
        private String devicenumber;
        private String location;
        private String imid;
        private String token;
        private String refereeid;
        private String refereename;
        private String keyword;
        private String regdate;

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setCustomerid(String customerid) {
            this.customerid = customerid;
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

        public void setLocation(String location) {
            this.location = location;
        }

        public void setImid(String imid) {
            this.imid = imid;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setRefereeid(String refereeid) {
            this.refereeid = refereeid;
        }

        public void setRefereename(String refereename) {
            this.refereename = refereename;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }

        public String getUid() {
            return uid;
        }

        public String getCustomerid() {
            return customerid;
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

        public String getLocation() {
            return location;
        }

        public String getImid() {
            return imid;
        }

        public String getToken() {
            return token;
        }

        public String getRefereeid() {
            return refereeid;
        }

        public String getRefereename() {
            return refereename;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getRegdate() {
            return regdate;
        }
    }
}
