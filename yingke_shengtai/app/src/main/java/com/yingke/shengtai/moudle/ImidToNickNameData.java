package com.yingke.shengtai.moudle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanyiheng on 15-10-11.
 */
public class ImidToNickNameData implements Serializable {
    private static final long serialVersionUID = 2948064817916389460L;


    /**
     * result : 1
     * message : 成功
     * detaillist : [{"sid":0,"uid":14,"displayname":"测试用户8-3","imid":"cus13900000803fc5e"}]
     */

    private int result;
    private String message;
    private List<DetaillistEntity> detaillist;

    public void setResult(int result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetaillist(List<DetaillistEntity> detaillist) {
        this.detaillist = detaillist;
    }

    public int getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public List<DetaillistEntity> getDetaillist() {
        return detaillist;
    }

    public static class DetaillistEntity {
        /**
         * sid : 0
         * uid : 14
         * displayname : 测试用户8-3
         * imid : cus13900000803fc5e
         */

        private int sid;
        private int uid;
        private String displayname;
        private String imid;
        private String sex;


        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }

        public void setImid(String imid) {
            this.imid = imid;
        }

        public int getSid() {
            return sid;
        }

        public int getUid() {
            return uid;
        }

        public String getDisplayname() {
            return displayname;
        }

        public String getImid() {
            return imid;
        }
    }
}
