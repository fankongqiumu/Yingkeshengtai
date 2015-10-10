package com.yingke.shengtai.moudle;

import java.io.Serializable;

/**
 * Created by yanyiheng on 15-9-5.
 */
public class TotalCustomerData implements Serializable {
    private static final long serialVersionUID = -4412454591555288927L;


    /**
     * result : 1
     * message :
     * saledetail : {"s_id":"2","s_keyid":"e0382f8b-07ce-4fc2-b7f0-ddeecffe73cf","s_memberid":"SAL10002","s_username":"18610000002","s_password":"","s_deviceid":null,"s_imid":"bes_root_servicemanager","s_token":"","s_name":"总客服","s_sex":"1","SalesSex":"1","s_showname":"总客服","s_membertype":"4","MemberType":"4","s_sectorid":"1","s_rate":"0.1","s_managerid":"0","s_childids":null,"s_latestlogin":null,"s_location":"北京","s_block":"false","s_delete":"false","s_rowspage":"5","s_default":true,"s_userids":"","s_imresponse":"","s_amount":"15000","DataKey":"s_id"}
     */

    private String result;
    private String message;
    private SaledetailEntity saledetail;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSaledetail(SaledetailEntity saledetail) {
        this.saledetail = saledetail;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public SaledetailEntity getSaledetail() {
        return saledetail;
    }

    public static class SaledetailEntity {
        /**
         * s_id : 2
         * s_keyid : e0382f8b-07ce-4fc2-b7f0-ddeecffe73cf
         * s_memberid : SAL10002
         * s_username : 18610000002
         * s_password :
         * s_deviceid : null
         * s_imid : bes_root_servicemanager
         * s_token :
         * s_name : 总客服
         * s_sex : 1
         * SalesSex : 1
         * s_showname : 总客服
         * s_membertype : 4
         * MemberType : 4
         * s_sectorid : 1
         * s_rate : 0.1
         * s_managerid : 0
         * s_childids : null
         * s_latestlogin : null
         * s_location : 北京
         * s_block : false
         * s_delete : false
         * s_rowspage : 5
         * s_default : true
         * s_userids :
         * s_imresponse :
         * s_amount : 15000
         * DataKey : s_id
         */

        private String s_id;
        private String s_keyid;
        private String s_memberid;
        private String s_username;
        private String s_password;
        private String s_deviceid;
        private String s_imid;
        private String s_token;
        private String s_name;
        private String s_sex;
        private String SalesSex;
        private String s_showname;
        private String s_membertype;
        private String MemberType;
        private String s_sectorid;
        private String s_rate;
        private String s_managerid;
        private Object s_childids;
        private Object s_latestlogin;
        private String s_location;
        private String s_block;
        private String s_delete;
        private String s_rowspage;
        private String s_default;
        private String s_userids;
        private String s_imresponse;
        private String s_amount;
        private String DataKey;

        public void setS_id(String s_id) {
            this.s_id = s_id;
        }

        public void setS_keyid(String s_keyid) {
            this.s_keyid = s_keyid;
        }

        public void setS_memberid(String s_memberid) {
            this.s_memberid = s_memberid;
        }

        public void setS_username(String s_username) {
            this.s_username = s_username;
        }

        public void setS_password(String s_password) {
            this.s_password = s_password;
        }

        public void setS_deviceid(String s_deviceid) {
            this.s_deviceid = s_deviceid;
        }

        public void setS_imid(String s_imid) {
            this.s_imid = s_imid;
        }

        public void setS_token(String s_token) {
            this.s_token = s_token;
        }

        public void setS_name(String s_name) {
            this.s_name = s_name;
        }

        public void setS_sex(String s_sex) {
            this.s_sex = s_sex;
        }

        public void setSalesSex(String SalesSex) {
            this.SalesSex = SalesSex;
        }

        public void setS_showname(String s_showname) {
            this.s_showname = s_showname;
        }

        public void setS_membertype(String s_membertype) {
            this.s_membertype = s_membertype;
        }

        public void setMemberType(String MemberType) {
            this.MemberType = MemberType;
        }

        public void setS_sectorid(String s_sectorid) {
            this.s_sectorid = s_sectorid;
        }

        public void setS_rate(String s_rate) {
            this.s_rate = s_rate;
        }

        public void setS_managerid(String s_managerid) {
            this.s_managerid = s_managerid;
        }

        public void setS_childids(Object s_childids) {
            this.s_childids = s_childids;
        }

        public void setS_latestlogin(Object s_latestlogin) {
            this.s_latestlogin = s_latestlogin;
        }

        public void setS_location(String s_location) {
            this.s_location = s_location;
        }

        public void setS_block(String s_block) {
            this.s_block = s_block;
        }

        public void setS_delete(String s_delete) {
            this.s_delete = s_delete;
        }

        public void setS_rowspage(String s_rowspage) {
            this.s_rowspage = s_rowspage;
        }

        public void setS_default(String s_default) {
            this.s_default = s_default;
        }

        public void setS_userids(String s_userids) {
            this.s_userids = s_userids;
        }

        public void setS_imresponse(String s_imresponse) {
            this.s_imresponse = s_imresponse;
        }

        public void setS_amount(String s_amount) {
            this.s_amount = s_amount;
        }

        public void setDataKey(String DataKey) {
            this.DataKey = DataKey;
        }

        public String getS_id() {
            return s_id;
        }

        public String getS_keyid() {
            return s_keyid;
        }

        public String getS_memberid() {
            return s_memberid;
        }

        public String getS_username() {
            return s_username;
        }

        public String getS_password() {
            return s_password;
        }

        public String getS_deviceid() {
            return s_deviceid;
        }

        public String getS_imid() {
            return s_imid;
        }

        public String getS_token() {
            return s_token;
        }

        public String getS_name() {
            return s_name;
        }

        public String getS_sex() {
            return s_sex;
        }

        public String getSalesSex() {
            return SalesSex;
        }

        public String getS_showname() {
            return s_showname;
        }

        public String getS_membertype() {
            return s_membertype;
        }

        public String getMemberType() {
            return MemberType;
        }

        public String getS_sectorid() {
            return s_sectorid;
        }

        public String getS_rate() {
            return s_rate;
        }

        public String getS_managerid() {
            return s_managerid;
        }

        public Object getS_childids() {
            return s_childids;
        }

        public Object getS_latestlogin() {
            return s_latestlogin;
        }

        public String getS_location() {
            return s_location;
        }

        public String getS_block() {
            return s_block;
        }

        public String getS_delete() {
            return s_delete;
        }

        public String getS_rowspage() {
            return s_rowspage;
        }

        public String getS_default() {
            return s_default;
        }

        public String getS_userids() {
            return s_userids;
        }

        public String getS_imresponse() {
            return s_imresponse;
        }

        public String getS_amount() {
            return s_amount;
        }

        public String getDataKey() {
            return DataKey;
        }
    }
}
