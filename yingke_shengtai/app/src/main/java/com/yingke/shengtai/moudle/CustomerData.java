package com.yingke.shengtai.moudle;

import java.io.Serializable;

/**
 * Created by yanyiheng on 15-8-24.
 */
public class CustomerData implements Serializable {

    private static final long serialVersionUID = 2322203236950717918L;

    private String name;
    private String phone;
    private String time;

    private String source;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
