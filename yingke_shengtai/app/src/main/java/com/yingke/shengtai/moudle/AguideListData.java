package com.yingke.shengtai.moudle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanyiheng on 15-10-11.
 */
public class AguideListData implements Serializable {


    private static final long serialVersionUID = 7645467146351844512L;
    /**
     * count : 12
     * channellist : [{"id":15,"level":2,"parentid":1,"shorttitle":"公司法律","title":"公司法律事务","brief":"公司法律事务","x":0,"y":0,"bgcolor":"#72cffd","iconurl":"http://218.249.159.112:8080/images/icon01.png","width":244,"height":159,"iconwidth":64,"iconheight":64,"fontsize":18},{"id":14,"level":2,"parentid":1,"shorttitle":"知识产权","title":"知识产权法律事务","brief":"知识产权法律事务","x":245,"y":0,"bgcolor":"#019fee","iconurl":"http://218.249.159.112:8080/images/icon07.png","width":488,"height":636,"iconwidth":256,"iconheight":256,"fontsize":32},{"id":16,"level":2,"parentid":1,"shorttitle":"金融法律","title":"金融法律事务","brief":"金融法律事务","x":0,"y":-160,"bgcolor":"#72cffd","iconurl":"http://218.249.159.112:8080/images/icon01.png","width":244,"height":159,"iconwidth":64,"iconheight":64,"fontsize":18},{"id":17,"level":2,"parentid":1,"shorttitle":"资本市场","title":"资本市场法律事务","brief":"资本市场法律事务","x":0,"y":-320,"bgcolor":"#72cffd","iconurl":"http://218.249.159.112:8080/images/icon01.png","width":244,"height":159,"iconwidth":64,"iconheight":64,"fontsize":18},{"id":18,"level":2,"parentid":1,"shorttitle":"房地产","title":"房地产与建筑工程法律事务","brief":"房地产与建筑工程法律事务","x":0,"y":-480,"bgcolor":"#72cffd","iconurl":"http://218.249.159.112:8080/images/icon01.png","width":244,"height":159,"iconwidth":64,"iconheight":64,"fontsize":18},{"id":19,"level":2,"parentid":1,"shorttitle":"政府法律","title":"政府法律事务","brief":"政府法律事务","x":0,"y":-640,"bgcolor":"#72cffd","iconurl":"http://218.249.159.112:8080/images/icon02.png","width":488,"height":318,"iconwidth":128,"iconheight":128,"fontsize":26},{"id":20,"level":2,"parentid":1,"shorttitle":"民事法律","title":"民事法律事务","brief":"民事法律事务","x":490,"y":-640,"bgcolor":"#2bb5fa","iconurl":"http://218.249.159.112:8080/images/icon12.png","width":244,"height":318,"iconwidth":128,"iconheight":128,"fontsize":22},{"id":21,"level":2,"parentid":1,"shorttitle":"诉讼仲裁","title":"诉讼仲裁法律事务","brief":"诉讼仲裁法律事务","x":0,"y":-960,"bgcolor":"#2bb5fa","iconurl":"http://218.249.159.112:8080/images/icon03.png","width":244,"height":318,"iconwidth":128,"iconheight":128,"fontsize":22},{"id":22,"level":2,"parentid":1,"shorttitle":"国际法律","title":"国际法律事务","brief":"国际法律事务","x":245,"y":-960,"bgcolor":"#72cffd","iconurl":"http://218.249.159.112:8080/images/icon01.png","width":244,"height":159,"iconwidth":64,"iconheight":64,"fontsize":18},{"id":58,"level":2,"parentid":1,"shorttitle":"反垄断","title":"反垄断与反不正当竞争专业委员会","brief":"反垄断与反不正当竞争专业委员会","x":490,"y":-960,"bgcolor":"#72cffd","iconurl":"http://218.249.159.112:8080/images/icon09.png","width":244,"height":159,"iconwidth":64,"iconheight":64,"fontsize":18},{"id":59,"level":2,"parentid":1,"shorttitle":"国际投资","title":"国际投资与贸易专业委员会","brief":"国际投资与贸易专业委员会","x":245,"y":-1120,"bgcolor":"#72cffd","iconurl":"http://218.249.159.112:8080/images/icon10.png","width":244,"height":159,"iconwidth":64,"iconheight":64,"fontsize":18},{"id":60,"level":2,"parentid":1,"shorttitle":"日本法律","title":"日本法律事业部","brief":"日本法律事业部","x":490,"y":-1120,"bgcolor":"#72cffd","iconurl":"http://218.249.159.112:8080/images/icon08.png","width":244,"height":159,"iconwidth":64,"iconheight":64,"fontsize":18}]
     */

    private int count;
    private List<ChannellistEntity> channellist;

    public void setCount(int count) {
        this.count = count;
    }

    public void setChannellist(List<ChannellistEntity> channellist) {
        this.channellist = channellist;
    }

    public int getCount() {
        return count;
    }

    public List<ChannellistEntity> getChannellist() {
        return channellist;
    }

    public static class ChannellistEntity implements Serializable{
        private static final long serialVersionUID = 7001821597261795075L;
        /**
         * id : 15
         * level : 2
         * parentid : 1
         * shorttitle : 公司法律
         * title : 公司法律事务
         * brief : 公司法律事务
         * x : 0
         * y : 0
         * bgcolor : #72cffd
         * iconurl : http://218.249.159.112:8080/images/icon01.png
         * width : 244
         * height : 159
         * iconwidth : 64
         * iconheight : 64
         * fontsize : 18
         */

        private int id;
        private int level;
        private int parentid;
        private String shorttitle;
        private String title;
        private String brief;
        private int x;
        private int y;
        private String bgcolor;
        private String iconurl;
        private int width;
        private int height;
        private int iconwidth;
        private int iconheight;
        private int fontsize;

        public void setId(int id) {
            this.id = id;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public void setShorttitle(String shorttitle) {
            this.shorttitle = shorttitle;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setBgcolor(String bgcolor) {
            this.bgcolor = bgcolor;
        }

        public void setIconurl(String iconurl) {
            this.iconurl = iconurl;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setIconwidth(int iconwidth) {
            this.iconwidth = iconwidth;
        }

        public void setIconheight(int iconheight) {
            this.iconheight = iconheight;
        }

        public void setFontsize(int fontsize) {
            this.fontsize = fontsize;
        }

        public int getId() {
            return id;
        }

        public int getLevel() {
            return level;
        }

        public int getParentid() {
            return parentid;
        }

        public String getShorttitle() {
            return shorttitle;
        }

        public String getTitle() {
            return title;
        }

        public String getBrief() {
            return brief;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public String getBgcolor() {
            return bgcolor;
        }

        public String getIconurl() {
            return iconurl;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getIconwidth() {
            return iconwidth;
        }

        public int getIconheight() {
            return iconheight;
        }

        public int getFontsize() {
            return fontsize;
        }
    }
}
