package com.yingke.shengtai.moudle;


import java.io.Serializable;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class SearchDetailData implements Serializable {
    private static final long serialVersionUID = -2576411525860979310L;

    private String yewuNumber;
    private String hetongNumber;
    private String writeNumber;
    private String writePeople;
    private String source;

    public String getYewuNumber() {
        return yewuNumber;
    }

    public void setYewuNumber(String yewuNumber) {
        this.yewuNumber = yewuNumber;
    }

    public String getHetongNumber() {
        return hetongNumber;
    }

    public void setHetongNumber(String hetongNumber) {
        this.hetongNumber = hetongNumber;
    }

    public String getWriteNumber() {
        return writeNumber;
    }

    public void setWriteNumber(String writeNumber) {
        this.writeNumber = writeNumber;
    }

    public String getWritePeople() {
        return writePeople;
    }

    public void setWritePeople(String writePeople) {
        this.writePeople = writePeople;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
