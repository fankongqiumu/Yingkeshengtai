package com.yingke.shengtai.moudle;

import java.io.Serializable;

/**
 * Created by yanyiheng on 15-9-8.
 */
public class BussinessDetailData implements Serializable {
    private static final long serialVersionUID = 2116632000983954527L;


    /**
     * result : 1
     * message :
     * detail : {"bid":"15","uid":"15","cusname":"测试用户4","cusmobile":"18600000003","businessid":"BUZ10015","contractid":"contractid0003","createdate":"2015-08-25T16:01:32.55","title":"title contractid0001234","sid":"30","salename":"业务员8","statusid":"9","status":"立案中","statusvalue":"20","channeltype":"1","channelname":"","refereeid":"0","refereename":"推荐人","amount":"15000","saleamount":"2560.38","refereeamount":"1200.99","text":"本业务于2015/8/31 22:27:17被总业务管理员修改 本业务于2015/9/1 17:41:19被知识产权业务员8修改 ","star":"0"}
     * userdetail : {"uid":"15","customerid":"CUS10015","usertype":"1","name":"测试用户8-4","displayname":"测试用户8-4","sex":"0","mobile":"13900000804","devicenumber":"352XXXXXXXX61328","location":"湖南 长沙","imid":"cus139000008046b8f","token":"","refereeid":"0","refereename":"","keyword":"null","regdate":"2015-08-29T14:49:31.183"}
     * saledetail : {"sid":"30","usertype":"24","name":"业务员8","displayname":"知识产权业务员8","sex":"0","mobile":"18600000101","devicenumber":"865982027519803","channelid":"14","channelname":"知识产权法律事务","rate":"0.18","location":"北京","imid":"bes1860000010140fd","token":"","managerid":"0","userids":"12,15,20,30,13"}
     */

    private String result;
    private String message;
    private DetailEntity detail;
    private UserdetailEntity userdetail;
    private SaledetailEntity saledetail;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetail(DetailEntity detail) {
        this.detail = detail;
    }

    public void setUserdetail(UserdetailEntity userdetail) {
        this.userdetail = userdetail;
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

    public DetailEntity getDetail() {
        return detail;
    }

    public UserdetailEntity getUserdetail() {
        return userdetail;
    }

    public SaledetailEntity getSaledetail() {
        return saledetail;
    }

    public static class DetailEntity implements Serializable{
        private static final long serialVersionUID = -3265788013671732064L;
        /**
         * bid : 15
         * uid : 15
         * cusname : 测试用户4
         * cusmobile : 18600000003
         * businessid : BUZ10015
         * contractid : contractid0003
         * createdate : 2015-08-25T16:01:32.55
         * title : title contractid0001234
         * sid : 30
         * salename : 业务员8
         * statusid : 9
         * status : 立案中
         * statusvalue : 20
         * channeltype : 1
         * channelname :
         * refereeid : 0
         * refereename : 推荐人
         * amount : 15000
         * saleamount : 2560.38
         * refereeamount : 1200.99
         * text : 本业务于2015/8/31 22:27:17被总业务管理员修改 本业务于2015/9/1 17:41:19被知识产权业务员8修改
         * star : 0
         */

        private String bid;
        private String uid;
        private String cusname;
        private String cusmobile;
        private String businessid;
        private String contractid;
        private String createdate;
        private String title;
        private String sid;
        private String salename;
        private String statusid;
        private String status;
        private String statusvalue;
        private String channeltype;
        private String channelname;
        private String refereeid;
        private String refereename;
        private String amount;
        private String saleamount;
        private String refereeamount;
        private String text;
        private int star;

        public void setBid(String bid) {
            this.bid = bid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setCusname(String cusname) {
            this.cusname = cusname;
        }

        public void setCusmobile(String cusmobile) {
            this.cusmobile = cusmobile;
        }

        public void setBusinessid(String businessid) {
            this.businessid = businessid;
        }

        public void setContractid(String contractid) {
            this.contractid = contractid;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public void setSalename(String salename) {
            this.salename = salename;
        }

        public void setStatusid(String statusid) {
            this.statusid = statusid;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setStatusvalue(String statusvalue) {
            this.statusvalue = statusvalue;
        }

        public void setChanneltype(String channeltype) {
            this.channeltype = channeltype;
        }

        public void setChannelname(String channelname) {
            this.channelname = channelname;
        }

        public void setRefereeid(String refereeid) {
            this.refereeid = refereeid;
        }

        public void setRefereename(String refereename) {
            this.refereename = refereename;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public void setSaleamount(String saleamount) {
            this.saleamount = saleamount;
        }

        public void setRefereeamount(String refereeamount) {
            this.refereeamount = refereeamount;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getBid() {
            return bid;
        }

        public String getUid() {
            return uid;
        }

        public String getCusname() {
            return cusname;
        }

        public String getCusmobile() {
            return cusmobile;
        }

        public String getBusinessid() {
            return businessid;
        }

        public String getContractid() {
            return contractid;
        }

        public String getCreatedate() {
            return createdate;
        }

        public String getTitle() {
            return title;
        }

        public String getSid() {
            return sid;
        }

        public String getSalename() {
            return salename;
        }

        public String getStatusid() {
            return statusid;
        }

        public String getStatus() {
            return status;
        }

        public String getStatusvalue() {
            return statusvalue;
        }

        public String getChanneltype() {
            return channeltype;
        }

        public String getChannelname() {
            return channelname;
        }

        public String getRefereeid() {
            return refereeid;
        }

        public String getRefereename() {
            return refereename;
        }

        public String getAmount() {
            return amount;
        }

        public String getSaleamount() {
            return saleamount;
        }

        public String getRefereeamount() {
            return refereeamount;
        }

        public String getText() {
            return text;
        }

        public int getStar() {
            return star;
        }
    }

    public static class UserdetailEntity implements Serializable{
        private static final long serialVersionUID = 165262418105364702L;
        /**
         * uid : 15
         * customerid : CUS10015
         * usertype : 1
         * name : 测试用户8-4
         * displayname : 测试用户8-4
         * sex : 0
         * mobile : 13900000804
         * devicenumber : 352XXXXXXXX61328
         * location : 湖南 长沙
         * imid : cus139000008046b8f
         * token :
         * refereeid : 0
         * refereename :
         * keyword : null
         * regdate : 2015-08-29T14:49:31.183
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

    public static class SaledetailEntity implements Serializable{
        private static final long serialVersionUID = -7963454791328650407L;
        /**
         * sid : 30
         * usertype : 24
         * name : 业务员8
         * displayname : 知识产权业务员8
         * sex : 0
         * mobile : 18600000101
         * devicenumber : 865982027519803
         * channelid : 14
         * channelname : 知识产权法律事务
         * rate : 0.18
         * location : 北京
         * imid : bes1860000010140fd
         * token :
         * managerid : 0
         * userids : 12,15,20,30,13
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
