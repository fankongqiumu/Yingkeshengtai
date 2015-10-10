package com.yingke.shengtai.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.BussinessListData;
import com.yingke.shengtai.moudle.CustomerListData;
import com.yingke.shengtai.moudle.ResultSuccessData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyiheng on 15-9-15.
 */
public class TrackWriteActivity extends BaseActivity implements View.OnClickListener{
    private ImageView imagBack;
    private TextView textTtitle, textAdd;
    private EditText edittext;
    private CustomerListData customerData;
    private ProgressDialog dialog;
    private BussinessListData.BusinesslistEntity bussinessData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            bussinessData = (BussinessListData.BusinesslistEntity)getIntent().getSerializableExtra(Constant.DATA_GUIDE_CENTER);
        } else {
            bussinessData = (BussinessListData.BusinesslistEntity)savedInstanceState.getSerializable(Constant.DATA_GUIDE_CENTER);
        }
        setContentView(R.layout.activity_comment);
        initUi();
    }

    private void initUi() {
        imagBack = (ImageView)findViewById(R.id.view_title_back);
        textTtitle = (TextView)findViewById(R.id.view_title_name);
        textAdd = (TextView)findViewById(R.id.textAdd);
        edittext = (EditText)findViewById(R.id.edittext);
        textTtitle.setText("添加记录");

        imagBack.setOnClickListener(this);
        textAdd.setOnClickListener(this);
        edittext.setHint("请输入要添加的记录");

        dialog = new ProgressDialog(this);
    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.equals(getString(R.string.try_agin), json)){
            if(dialog != null){
                dialog.dismiss();
            }
            MethodUtils.showToast(this, getResources().getString(R.string.try_agin), Toast.LENGTH_SHORT);
            return;
        }
        if(msg.what == TAG_ADD_BUSSINESS_WRITE){
            Type type = new TypeToken<ResultSuccessData>(){}.getType();
            ResultSuccessData data = JosnUtil.gson.fromJson(json, type);
            if(TextUtils.equals(data.getResult(), "1")){
                dialog.dismiss();
                MethodUtils.showToast(this, "添加成功", Toast.LENGTH_SHORT);
                finish();
            } else {
                dialog.dismiss();
                MethodUtils.showToast(this, "添加失败", Toast.LENGTH_SHORT);

            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constant.DATA_GUIDE_CENTER, customerData);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textAdd:
                String text = edittext.getText().toString().trim();
                if(TextUtils.isEmpty(text)){
                    break;
                }
                if(dialog != null){
                    dialog.show();
                }
                ;
                askData(text);
                break;
            case R.id.view_title_back:
                finish();
                break;
        }
    }

    private void askData(String text) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sid", MyApplication.getInstance().getUserInfor().getUserdetail().getSid());
        map.put("token", MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        map.put("action", "insert");
        map.put("tid", "0");
        map.put("text", text);
        getData(IApi.NETWORK_METHOD_POST, TAG_ADD_BUSSINESS_WRITE, IApi.URL_TRACKS + bussinessData.getBid(), map);
    }



}