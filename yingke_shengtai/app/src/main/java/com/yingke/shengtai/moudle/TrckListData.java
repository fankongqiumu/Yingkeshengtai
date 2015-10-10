package com.yingke.shengtai.moudle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanyiheng on 15-9-15.
 */
public class TrckListData implements Serializable {

    private static final long serialVersionUID = -6763486448686364580L;

    /**
     * result : 1
     * message : 成功
     * bid : 16
     * uid :
     * title : title contractid0001213
     * createdate : 2015-08-25T16:01:32.75
     * sid : 30
     * statusid : 2
     * status : 完成
     * statusvalue : 200
     * tracklist : [{"tid":"98","bid":"16","createdate":"2015-09-01T17:40:44.37","salesdisplayname":"知识产权业务员8","sid":"30","text":"asdsadasd"}]
     */

    private String result;
    private String message;
    private String bid;
    private String uid;
    private String title;
    private String createdate;
    private String sid;
    private String statusid;
    private String status;
    private String statusvalue;
    private List<TracklistEntity> tracklist;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public void setTracklist(List<TracklistEntity> tracklist) {
        this.tracklist = tracklist;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getBid() {
        return bid;
    }

    public String getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedate() {
        return createdate;
    }

    public String getSid() {
        return sid;
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

    public List<TracklistEntity> getTracklist() {
        return tracklist;
    }

    public static class TracklistEntity implements Serializable{
        private static final long serialVersionUID = -7416016938881596316L;
        /**
         * tid : 98
         * bid : 16
         * createdate : 2015-09-01T17:40:44.37
         * salesdisplayname : 知识产权业务员8
         * sid : 30
         * text : asdsadasd
         */

        private String tid;
        private String bid;
        private String createdate;
        private String salesdisplayname;
        private String sid;
        private String text;

        public void setTid(String tid) {
            this.tid = tid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public void setSalesdisplayname(String salesdisplayname) {
            this.salesdisplayname = salesdisplayname;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTid() {
            return tid;
        }

        public String getBid() {
            return bid;
        }

        public String getCreatedate() {
            return createdate;
        }

        public String getSalesdisplayname() {
            return salesdisplayname;
        }

        public String getSid() {
            return sid;
        }

        public String getText() {
            return text;
        }
    }
}
