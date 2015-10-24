package com.yingke.shengtai.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.R;
import com.yingke.shengtai.adapter.SaleListAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.SaleslistEntity;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.AnimatedExpandableListView;
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
    private AnimatedExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_yewu_list);
        initUI();
        askData();
        mWaveSwipeRefreshLayout.setRefreshing(true);
    }

    private void askData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sid", MyApplication.getInstance().getUserInfor().getUserdetail().getSid());
        map.put("token", MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        getData(IApi.NETWORK_METHOD_POST, TAG_SALE_LIST, IApi.URL_SALE_LIST, map);
    }

    private void initUI() {
        titleView = (TitleView)findViewById(R.id.fragment_title);
        titleView.setTitleView("业务员列表");
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (AnimatedExpandableListView) findViewById(R.id.fragment_listView);
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
            if(data == null || TextUtils.equals(data.getResult() + "", "0") || data.getTranslist() == null || data.getTranslist().size() <= 0){
                return;
            }
            if(adapter == null){
                adapter = new SaleListAdapter(this, (ArrayList)data.getTranslist(), listView);
                listView.setAdapter(adapter);
                listView.expandGroupWithAnimation(0);
            } else {
                adapter.setData((ArrayList)data.getTranslist());
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

    public class SalesData implements Serializable {

        private static final long serialVersionUID = -5815536264328561832L;
        /**
         * result : 1
         * message :
         * translist : [{"channelid":16,"channelname":"金融法律","saleslist":[{"sid":32,"usertype":16,"name":"业务员16","displayname":"男","sex":0,"mobile":"18600000103","devicenumber":"865982027519803","channelid":16,"channelname":"金融法律事务","rate":0.12,"location":"北京","imid":"bes186000001034b26","token":"","managerid":0,"userids":"12,13,14,20","status":"online"}]}]
         */

        private int result;
        private String message;
        private List<TranslistEntity> translist;

        public void setResult(int result) {
            this.result = result;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setTranslist(List<TranslistEntity> translist) {
            this.translist = translist;
        }

        public int getResult() {
            return result;
        }

        public String getMessage() {
            return message;
        }

        public List<TranslistEntity> getTranslist() {
            return translist;
        }

        public  class TranslistEntity implements Serializable {
            private static final long serialVersionUID = -4585888781721306355L;
            /**
             * channelid : 16
             * channelname : 金融法律
             * saleslist : [{"sid":32,"usertype":16,"name":"业务员16","displayname":"男","sex":0,"mobile":"18600000103","devicenumber":"865982027519803","channelid":16,"channelname":"金融法律事务","rate":0.12,"location":"北京","imid":"bes186000001034b26","token":"","managerid":0,"userids":"12,13,14,20","status":"online"}]
             */

            private int channelid;
            private String channelname;
            private List<SaleslistEntity> saleslist;

            public void setChannelid(int channelid) {
                this.channelid = channelid;
            }

            public void setChannelname(String channelname) {
                this.channelname = channelname;
            }

            public void setSaleslist(List<SaleslistEntity> saleslist) {
                this.saleslist = saleslist;
            }

            public int getChannelid() {
                return channelid;
            }

            public String getChannelname() {
                return channelname;
            }

            public List<SaleslistEntity> getSaleslist() {
                return saleslist;
            }



        }

    }
}
