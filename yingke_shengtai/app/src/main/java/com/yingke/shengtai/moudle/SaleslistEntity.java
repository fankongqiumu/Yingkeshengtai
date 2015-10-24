package com.yingke.shengtai.moudle;

import java.io.Serializable;

/**
 * Created by yanyiheng on 15-10-17.
 */
public class SaleslistEntity implements Serializable{
    private static final long serialVersionUID = -6670941693302621182L;
    /**
     * sid : 32
     * usertype : 16
     * name : 业务员16
     * displayname : 男
     * sex : 0
     * mobile : 18600000103
     * devicenumber : 865982027519803
     * channelid : 16
     * channelname : 金融法律事务
     * rate : 0.12
     * location : 北京
     * imid : bes186000001034b26
     * token :
     * managerid : 0
     * userids : 12,13,14,20
     * status : online
     */

    private int sid;
    private int usertype;
    private String name;
    private String displayname;
    private int sex;
    private String mobile;
    private String devicenumber;
    private int channelid;
    private String channelname;
    private double rate;
    private String location;
    private String imid;
    private String token;
    private int managerid;
    private String userids;
    private String status;

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setDevicenumber(String devicenumber) {
        this.devicenumber = devicenumber;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public void setRate(double rate) {
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

    public void setManagerid(int managerid) {
        this.managerid = managerid;
    }

    public void setUserids(String userids) {
        this.userids = userids;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSid() {
        return sid;
    }

    public int getUsertype() {
        return usertype;
    }

    public String getName() {
        return name;
    }

    public String getDisplayname() {
        return displayname;
    }

    public int getSex() {
        return sex;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDevicenumber() {
        return devicenumber;
    }

    public int getChannelid() {
        return channelid;
    }

    public String getChannelname() {
        return channelname;
    }

    public double getRate() {
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

    public int getManagerid() {
        return managerid;
    }

    public String getUserids() {
        return userids;
    }

    public String getStatus() {
        return status;
    }
}
