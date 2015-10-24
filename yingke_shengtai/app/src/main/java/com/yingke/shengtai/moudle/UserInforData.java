package com.yingke.shengtai.moudle;

import java.io.Serializable;


/**
 * Created by yanyiheng on 15-8-30.
 */
public class UserInforData implements Serializable{
    private static final long serialVersionUID = -2818022834711820122L;
    /**
     * result : 1
     * usertype : 1
     * message : 注册成功
     * userdetail : {"uid":25,"customerid":"CUS10025","usertype":1,"name":"你好","displayname":"你好","sex":0,"mobile":"17090024334","devicenumber":"865982027519803","location":"北京","imid":"cus1709002433447b7","token":"47b7437db66d4472aada6e4fd7e53af5","refereeid":0,"refereename":"","keyword":null,"regdate":"2015-08-30T21:29:12.157"}
     */

    private String result;
    private String usertype;
    private String message;
    private UserdetailEntity userdetail;
    private Saledetail saledetail;

//    http://stackoverflow.com/questions/12912287/intellij-idea-generating-serialversionuid


//    勾上就OK了！～File => Settings... => Inspections => Serialization issues => Serializable class without 'serialVersionUID'


    public void setResult(String  result) {
        this.result = result;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserdetail(UserdetailEntity userdetail) {
        this.userdetail = userdetail;
    }

    public String getResult() {
        return result;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getMessage() {
        return message;
    }

    public UserdetailEntity getUserdetail() {
        return userdetail;
    }

    public Saledetail getSaledetail() {
        return saledetail;
    }

    public void setSaledetail(Saledetail saledetail) {
        this.saledetail = saledetail;
    }

    public static class UserdetailEntity implements Serializable{
        private static final long serialVersionUID = -7777923840790364569L;
        /**
         * uid : 25
         * customerid : CUS10025
         * usertype : 1
         * name : 你好
         * displayname : 你好
         * sex : 0
         * mobile : 17090024334
         * devicenumber : 865982027519803
         * location : 北京
         * imid : cus1709002433447b7
         * token : 47b7437db66d4472aada6e4fd7e53af5
         * refereeid : 0
         * refereename :
         * keyword : null
         * regdate : 2015-08-30T21:29:12.157
         */

        private String uid;
        private String sid;
        private String rate;
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

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

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

        public String getSid() {
            return sid;
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

    public static class Saledetail extends UserdetailEntity implements Serializable{
        private static final long serialVersionUID = 7816018616414190974L;

    }
}
