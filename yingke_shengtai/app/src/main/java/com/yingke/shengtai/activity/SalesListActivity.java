package com.yingke.shengtai.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.adapter.SaleListAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.TitleView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-9-24.
 */
public class SalesListActivity extends BaseActivity implements  WaveSwipeRefreshLayout.OnRefreshListener{
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private TitleView titleView;
    private SaleListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_guide);
        initUI();
        askData();
        mWaveSwipeRefreshLayout.setRefreshing(true);
    }

    private void askData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sid", MyApplication.getInstance().getUserInfor().getUserdetail().getSid());
//        map.put("sid","30");
        map.put("token", MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        getData(IApi.NETWORK_METHOD_POST, TAG_SALE_LIST, IApi.URL_SALE_LIST, map);
    }

    private void initUI() {
        titleView = (TitleView)findViewById(R.id.fragment_title);
        titleView.setTitleView("业务员列表");
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) findViewById(R.id.fragment_listView);
    }

    @Override
    public void handleMsg(Message msg) {
        setRefreFalse();
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.equals(getString(R.string.try_agin), json)){
            MethodUtils.showToast(this, getResources().getString(R.string.try_agin), Toast.LENGTH_SHORT);
            return;
        }
        if(msg.what == TAG_SALE_LIST){
            SalesData data = JosnUtil.gson.fromJson(json, new TypeToken<SalesData>(){}.getType());
            if(data == null || TextUtils.equals(data.getResult(), "0") || data.getSaleslist() == null || data.getSaleslist().size() <= 0){
                return;
            }
            if(adapter == null){
                adapter = new SaleListAdapter(this, (ArrayList)data.getSaleslist(), listView);
                listView.setAdapter(adapter);
            } else {
                adapter.setData((ArrayList)data.getSaleslist());
            }
        }
    }


    @Override
    public void onRefresh() {
askData();
    }

    private void setRefreFalse(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }

    public class SalesData implements Serializable{

        /**
         * result : 1
         * message :
         * saleslist : [{"sid":"44","usertype":"8","name":"业务员24-6","displayname":"财税法律业务员23-6","sex":"0","mobile":"18600000115","devicenumber":"","channelid":"24","channelname":"财税法律专业委员会","rate":"0.1","location":"","imid":"bes186000001155407","token":"","managerid":"0","userids":"22,19","status":"offline"}]
         */

        private String result;
        private String message;
        private List<SaleslistEntity> saleslist;

        public void setResult(String result) {
            this.result = result;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setSaleslist(List<SaleslistEntity> saleslist) {
            this.saleslist = saleslist;
        }

        public String getResult() {
            return result;
        }

        public String getMessage() {
            return message;
        }

        public List<SaleslistEntity> getSaleslist() {
            return saleslist;
        }

        public  class SaleslistEntity implements Serializable{
            /**
             * sid : 44
             * usertype : 8
             * name : 业务员24-6
             * displayname : 财税法律业务员23-6
             * sex : 0
             * mobile : 18600000115
             * devicenumber :
             * channelid : 24
             * channelname : 财税法律专业委员会
             * rate : 0.1
             * location :
             * imid : bes186000001155407
             * token :
             * managerid : 0
             * userids : 22,19
             * status : offline
             */

            private String sid;
            private String usertype;
            private String name;
            private String displayname;
            private String sex;
            private String mobile;
            private String devicenumber;
            private String channelid;
            private String channelname;
            private String rate;
            private String location;
            private String imid;
            private String token;
            private String managerid;
            private String userids;
            private String status;

            public void setSid(String sid) {
                this.sid = sid;
            }

            public void setUsertype(String usertype) {
                this.usertype = usertype;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setDisplayname(String displayname) {
                this.displayname = displayname;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public void setDevicenumber(String devicenumber) {
                this.devicenumber = devicenumber;
            }

            public void setChannelid(String channelid) {
                this.channelid = channelid;
            }

            public void setChannelname(String channelname) {
                this.channelname = channelname;
            }

            public void setRate(String rate) {
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

            public void setManagerid(String managerid) {
                this.managerid = managerid;
            }

            public void setUserids(String userids) {
                this.userids = userids;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSid() {
                return sid;
            }

            public String getUsertype() {
                return usertype;
            }

            public String getName() {
                return name;
            }

            public String getDisplayname() {
                return displayname;
            }

            public String getSex() {
                return sex;
            }

            public String getMobile() {
                return mobile;
            }

            public String getDevicenumber() {
                return devicenumber;
            }

            public String getChannelid() {
                return channelid;
            }

            public String getChannelname() {
                return channelname;
            }

            public String getRate() {
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

            public String getManagerid() {
                return managerid;
            }

            public String getUserids() {
                return userids;
            }

            public String getStatus() {
                return status;
            }
        }
    }
}
