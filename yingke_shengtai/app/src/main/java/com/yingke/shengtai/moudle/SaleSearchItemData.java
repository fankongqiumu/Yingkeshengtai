package com.yingke.shengtai.moudle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanyiheng on 15-9-9.
 */
public class SaleSearchItemData implements Serializable {

    /**
     * result : 1
     * message : 成功
     * page : 1
     * count : 1
     * gainlist : [{"id":"50","date":"2015-09-01T00:00:00","amount":"0.0000","count":"0","star":"0.0000","sid":"30"}]
     */

    private String result;
    private String message;
    private String page;
    private int count;
    private List<GainlistEntity> gainlist;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setGainlist(List<GainlistEntity> gainlist) {
        this.gainlist = gainlist;
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

    public int getCount() {
        return count;
    }

    public List<GainlistEntity> getGainlist() {
        return gainlist;
    }

    public static class GainlistEntity implements Serializable{
        private static final long serialVersionUID = 4658221599806717029L;
        /**
         * id : 50
         * date : 2015-09-01T00:00:00
         * amount : 0.0000
         * count : 0
         * star : 0.0000
         * sid : 30
         */

        private String id;
        private String date;
        private String amount;
        private String count;
        private String star;
        private String sid;

        public void setId(String id) {
            this.id = id;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getId() {
            return id;
        }

        public String getDate() {
            return date;
        }

        public String getAmount() {
            return amount;
        }

        public String getCount() {
            return count;
        }

        public String getStar() {
            return star;
        }

        public String getSid() {
            return sid;
        }
    }
}
