package com.yingke.shengtai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.adapter.CenterDetailAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.db.User;
import com.yingke.shengtai.moudle.GudiDetail2Data.SaleslistEntity;
import com.yingke.shengtai.moudle.GuideListData;
import com.yingke.shengtai.moudle.UserDao;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.FootView;
import com.yingke.shengtai.view.TitleView;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.StringTokenizer;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class GuideListDetailActivity2 extends BaseActivity implements WaveSwipeRefreshLayout.OnRefreshListener,View.OnClickListener, FootView.LoadingMoreListener{
    private TitleView titleView;
    private TextView textHeadTitle;
    private ListView listView;
    private View headView;
    private ImageView arrow;
    private ArrayList<SaleslistEntity> list;
    private GuideListData data;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private ProgressDialog dialog;
    private CenterDetailAdapter adapter;


    private Type type;

    private String title, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            title = getIntent().getStringExtra(Constant.DATA_GUIDE_TITLE);
            id = getIntent().getStringExtra(Constant.DATA_GUIDE_ID);

        } else {
            title = savedInstanceState.getString(Constant.DATA_GUIDE_TITLE);
            id = savedInstanceState.getString(Constant.DATA_GUIDE_ID);
        }
        setContentView(R.layout.activty_guide_detail2);
        initUi();
    }

    private void initUi() {
        titleView = (TitleView)findViewById(R.id.fragment_title);
        titleView.setTitleView(title);
        headView = LayoutInflater.from(this).inflate(R.layout.activity_detail_two, null);
        arrow = (ImageView) headView.findViewById(R.id.arrow);
        headView.findViewById(R.id.line).setOnClickListener(this);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        textHeadTitle = (TextView)headView.findViewById(R.id.detail_two_title);
        findViewById(R.id.tochat).setOnClickListener(this);

        listView = (ListView) findViewById(R.id.fragment_listView);
        listView.addHeaderView(headView);
        listView.setDividerHeight(0);

        askData();


        type = new TypeToken<GuideListData>() {}.getType();
        dialog = new ProgressDialog(this);
        dialog.setMessage("连接中,请稍后...");

    }

    private void askData() {
        getData(IApi.NETWORK_METHOD_GET, TAG_GUIDE_THREE, IApi.URL_GUIDLIST_TWO + id, null);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.DATA_GUIDE_ID, id);
        outState.putString(Constant.DATA_GUIDE_TITLE, title);
    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.isEmpty(json) || json.toString().length() < 30){
            setRefreFalse();
            return;
        }

        switch (msg.what){
            case TAG_GUIDE_THREE:
                data = JosnUtil.gson.fromJson(json, type);
                textHeadTitle.setText(data.getTitle());
                StringTokenizer tokenizer = new StringTokenizer(data.getText(), "{}");
                ArrayList<String> list = new ArrayList<String>();
                while (tokenizer.hasMoreTokens()){
                    list.add(tokenizer.nextToken());
                }
                if(adapter == null){
                    adapter = new CenterDetailAdapter(this, list, data.getMedialist(), true, true);
                    listView.setAdapter(adapter);
                } else {
                    adapter.setData(list, data.getMedialist());
                }
                setRefreFalse();
                break;
            case TAG_GUIDE_FOUR:
                MessageData message = JosnUtil.gson.fromJson(json, new TypeToken<MessageData>(){}.getType());
                if(dialog != null){
                    dialog.dismiss();
                }
                if(TextUtils.equals("0", message.getResult())){
                    MethodUtils.showToast(this, message.getMessage(), Toast.LENGTH_SHORT);
                    break;
                }
                User user = new User();
                user.setNick(message.getSaledetail().getDisplayname());
                user.setUsername(message.getSaledetail().getImid());
                new UserDao(this).saveContactsss(user);
                Intent intent = new Intent(GuideListDetailActivity2.this, ChatActivity.class);
                intent.putExtra("userId", message.getSaledetail().getImid());
                intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
                intent.putExtra("name", message.getSaledetail().getDisplayname());
                startActivity(intent);

                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    private void setRefreFalse(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新が終了したらインジケータ非表示
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 300);
    }

    @Override
    public void loadingMore() {
        askData();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tochat){
           if(dialog != null){
               dialog.show();
           }
            getData(IApi.NETWORK_METHOD_GET, TAG_GUIDE_FOUR, IApi.URL_GUIDLIST_TWO + id + "?action=im", null);

        } else if(view.getId() == R.id.line){
            if(adapter != null && adapter.getCount() != 0){
                if(adapter.isShouSuo()){
                    arrow.setBackgroundResource(R.mipmap.arrow_up);
                    adapter.setIsShouSuo(false);
                    adapter.notifyDataSetChanged();
                } else {
                    arrow.setBackgroundResource(R.mipmap.arrow_down);
                    adapter.setIsShouSuo(true);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        askData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(data == null){
            mWaveSwipeRefreshLayout.setRefreshing(true);
        }
    }

    public class MessageData implements Serializable{


        /**
         * result : 1
         * message : 成功
         * saledetail : {"sid":"34","usertype":"16","name":"业务员18","displayname":"房地产与建筑工程业务员18","sex":"0","mobile":"18600000105","devicenumber":"","channelid":"18","channelname":"房地产与建筑工程法律事务","rate":"0.12","location":"","imid":"bes18600000105ec6a","token":"","managerid":"0","userids":"12,13,14"}
         */

        private String result;
        private String message;

        private SaledetailEntity saledetail;

        public void setResult(String result) {
            this.result = result;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setSaledetail(SaledetailEntity saledetail) {
            this.saledetail = saledetail;
        }

        public String getResult() {
            return result;
        }

        public String getMessage() {
            return message;
        }

        public SaledetailEntity getSaledetail() {
            return saledetail;
        }


        public class SaledetailEntity {
            /**
             * sid : 34
             * usertype : 16
             * name : 业务员18
             * displayname : 房地产与建筑工程业务员18
             * sex : 0
             * mobile : 18600000105
             * devicenumber :
             * channelid : 18
             * channelname : 房地产与建筑工程法律事务
             * rate : 0.12
             * location :
             * imid : bes18600000105ec6a
             * token :
             * managerid : 0
             * userids : 12,13,14
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
        }
    }
}
