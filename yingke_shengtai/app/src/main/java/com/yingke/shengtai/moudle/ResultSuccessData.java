package com.yingke.shengtai.moudle;

import java.io.Serializable;

/**
 * Created by yanyiheng on 15-9-5.
 */
public class ResultSuccessData implements Serializable {

    /**
     * result : 1
     * message :
     * text : Terms and Conditions
     */

    private String result;
    private String message;
    private String text;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getText() {
        return text;
    }
}
