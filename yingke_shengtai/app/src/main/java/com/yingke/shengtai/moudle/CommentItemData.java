package com.yingke.shengtai.moudle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanyiheng on 15-9-9.
 */
public class CommentItemData implements Serializable {
    private static final long serialVersionUID = 7570846245661112735L;


    /**
     * result : 1
     * message :
     * reviewlist : [{"rid":"4","uid":"15","createdate":"2015-08-25T04:30:06.05","salesdisplayname":"总业务管理员","sid":"1","text":"just test text."}]
     */

    private String result;
    private String message;
    private List<ReviewlistEntity> reviewlist;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReviewlist(List<ReviewlistEntity> reviewlist) {
        this.reviewlist = reviewlist;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public List<ReviewlistEntity> getReviewlist() {
        return reviewlist;
    }

    public static class ReviewlistEntity {
        /**
         * rid : 4
         * uid : 15
         * createdate : 2015-08-25T04:30:06.05
         * salesdisplayname : 总业务管理员
         * sid : 1
         * text : just test text.
         */

        private String rid;
        private String uid;
        private String createdate;
        private String salesdisplayname;
        private String sid;
        private String text;

        public void setRid(String rid) {
            this.rid = rid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public String getRid() {
            return rid;
        }

        public String getUid() {
            return uid;
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
