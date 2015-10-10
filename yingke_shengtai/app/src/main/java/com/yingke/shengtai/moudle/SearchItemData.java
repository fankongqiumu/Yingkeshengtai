package com.yingke.shengtai.moudle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class SearchItemData implements Serializable{
    private static final long serialVersionUID = 2333030758751827471L;


    /**
     * result : 1
     * message : 成功
     * page : 2
     * count : 4
     * businesslist : [{"bid":"19","uid":"12","cusname":"测试用户8-1","cusmobile":"13900000801","businessid":"BUZ10019","contractid":"contractid0007","createdate":"2015-08-25T16:01:33.3","title":"title contractid0001227","sid":"40","salename":"业务员23-2","statusid":"1","status":"合同终止","statusvalue":"-1"}]
     */

    private String result;
    private String message;
    private String page;
    private String count;
    private ArrayList<BusinesslistEntity> businesslist;

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

    public void setBusinesslist(ArrayList<BusinesslistEntity> businesslist) {
        this.businesslist = businesslist;
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

    public ArrayList<BusinesslistEntity> getBusinesslist() {
        return businesslist;
    }

    public static class BusinesslistEntity implements Serializable{
        private static final long serialVersionUID = -1837812996936027673L;
        /**
         * bid : 19
         * uid : 12
         * cusname : 测试用户8-1
         * cusmobile : 13900000801
         * businessid : BUZ10019
         * contractid : contractid0007
         * createdate : 2015-08-25T16:01:33.3
         * title : title contractid0001227
         * sid : 40
         * salename : 业务员23-2
         * statusid : 1
         * status : 合同终止
         * statusvalue : -1
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
    }
}
