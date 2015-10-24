package com.yingke.shengtai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.AguideListData;
import com.yingke.shengtai.moudle.BussinessDetailData;
import com.yingke.shengtai.moudle.GuideListData;
import com.yingke.shengtai.moudle.ResultSuccessData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyiheng on 15-9-9.
 */
public class BussinessUpdateActivity extends BaseActivity implements View.OnClickListener {
    private TextView textTitle, textAdd, textStatus, textSouce;
    private EditText editName, editPhone, editNumber, editTitle, editRecommentName, editRecommentPhone, editTotalMoney,
            editSaleMoney, editSaleTotalMoney, editText;
    private BussinessDetailData currentData;
    private RelativeLayout relStatus, relsoucre;
    private static final int REQUEST_STATUS = 0X8888;
    private static final int REQUEST_SOURCE = 0X6666;
    private String flag;
    private ArrayList<StatusData> statusData;
    private AguideListData soursData;
    private int position, position2;
    private ProgressDialog dialog;
    private boolean bol1 = false;
    private boolean bol2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null && getIntent() != null) {
            currentData = (BussinessDetailData) getIntent().getSerializableExtra("DATA");
            flag = getIntent().getStringExtra("FLAG");
        } else {
            if(savedInstanceState != null){
                currentData = (BussinessDetailData) savedInstanceState.getSerializable("DATA");
            }
            flag = savedInstanceState.getString("FLAG");
        }
        setContentView(R.layout.activity_bussiness_add);
        initView();
    }

    private void initView() {
        textTitle = (TextView) findViewById(R.id.view_title_name);
        textAdd = (TextView) findViewById(R.id.textAdd);
        textStatus = (TextView) findViewById(R.id.textStatus);
        textSouce = (TextView) findViewById(R.id.textSouce);

        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editNumber = (EditText) findViewById(R.id.editNumber);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editRecommentName = (EditText) findViewById(R.id.editRecommentName);
        editRecommentPhone = (EditText) findViewById(R.id.editRecommentPhone);
        editTotalMoney = (EditText) findViewById(R.id.editTotalMoney);
        editSaleMoney = (EditText) findViewById(R.id.editSaleMoney);
        editSaleTotalMoney = (EditText) findViewById(R.id.editSaleTotalMoney);
        editNumber = (EditText) findViewById(R.id.editNumber);
        editText = (EditText) findViewById(R.id.editText);


        relStatus = (RelativeLayout) findViewById(R.id.relStatus);
        relsoucre = (RelativeLayout) findViewById(R.id.relsoucre);
        relStatus.setOnClickListener(this);
        relsoucre.setOnClickListener(this);
        textAdd.setOnClickListener(this);
        findViewById(R.id.view_title_back).setOnClickListener(this);

        if (TextUtils.equals("update", flag)) {
            textTitle.setText("修改合同");
            textAdd.setText("修改");
            setData();
        } else {
            textTitle.setText("添加合同");
            textAdd.setText("添加");
        }

        dialog = new ProgressDialog(this);

    }

    private void setData() {
        if(!TextUtils.isEmpty(currentData.getDetail().getCusname())){
            editName.setText(currentData.getDetail().getCusname());
        }
        editPhone.setText(currentData.getDetail().getRefereemobile());
        editTitle.setText(currentData.getDetail().getTitle());
        if(!TextUtils.isEmpty(currentData.getDetail().getRefereename())){
            editRecommentName.setText(currentData.getDetail().getRefereename());
        }

        if(!TextUtils.isEmpty(currentData.getDetail().getRefereeamount())){
            editSaleTotalMoney.setText(new DecimalFormat("0.00").format(Double.valueOf(currentData.getDetail().getRefereeamount())));
        }
        editNumber.setText(currentData.getDetail().getContractid());
        editText.setText(currentData.getDetail().getText());
        if(!TextUtils.isEmpty(currentData.getDetail().getSaleamount())){
            editSaleMoney.setText(new DecimalFormat("0.00").format(Double.valueOf(currentData.getDetail().getSaleamount())));
        }
        if(!TextUtils.isEmpty(currentData.getDetail().getAmount())){
            editTotalMoney.setText(new DecimalFormat("0.00").format(Double.valueOf(currentData.getDetail().getAmount())));
        }
        textStatus.setText(currentData.getDetail().getStatus());
        editRecommentPhone.setText(currentData.getDetail().getRefereemobile());
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData(IApi.NETWORK_METHOD_GET, TAG_STATUS, IApi.URL_STATUS, null);
        getData(IApi.NETWORK_METHOD_GET, TAG_SOURCE, IApi.URL_GUIDLIST, null);
    }

    @Override
    public void handleMsg(Message msg) {

        switch (msg.what) {
            case TAG_SOURCE:
                String json1 = msg.getData().getString(Constant.JSON_DATA);
                if (TextUtils.equals(getString(R.string.try_agin), json1)) {
                    return;
                }
                Type type1 = new TypeToken<AguideListData>() {}.getType();
                soursData = JosnUtil.gson.fromJson(json1, type1);
                break;
            case TAG_STATUS:
                String json2 = msg.getData().getString(Constant.JSON_DATA);
                if (TextUtils.equals(getString(R.string.try_agin), json2)) {
                    return;
                }
                Type type2 = new TypeToken<ArrayList<StatusData>>(){}.getType();
                statusData = JosnUtil.gson.fromJson(json2, type2);
                break;

            case TAG_ADD_BUSSINESS_LIST_FRAGMENT:
                String json3 = msg.getData().getString(Constant.JSON_DATA);
                if (TextUtils.equals(getString(R.string.try_agin), json3)) {
                    return;
                }
                if(dialog != null){
                    dialog.dismiss();
                }
                Type type3 = new TypeToken<ResultSuccessData>(){}.getType();
                ResultSuccessData data3 = JosnUtil.gson.fromJson(json3, type3);
                if(TextUtils.equals(data3.getResult(), "1")){
                    MethodUtils.showToast(this, "添加成功", Toast.LENGTH_SHORT);
                    this.finish();
                } else {
                    MethodUtils.showToast(this, "添加失败,请重试！", Toast.LENGTH_SHORT);
                }
                break;
            case TAG_UPDATE_BUSSINESS_CUSTOMER:
                String json4 = msg.getData().getString(Constant.JSON_DATA);
                if (TextUtils.equals(getString(R.string.try_agin), json4)) {
                    return;
                }
                if(dialog != null){
                    dialog.dismiss();
                }
                Type type4 = new TypeToken<ResultSuccessData>(){}.getType();
                ResultSuccessData data4 = JosnUtil.gson.fromJson(json4, type4);
                if(TextUtils.equals(data4.getResult(), "1")){
                    MethodUtils.showToast(this, "修改成功", Toast.LENGTH_SHORT);
                    this.finish();
                } else {
                    MethodUtils.showToast(this, "修改失败,请重试！", Toast.LENGTH_SHORT);
                }
                break;

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(currentData != null){
            outState.putSerializable("DATA", currentData);
        }
        outState.putSerializable("FLAG", flag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_STATUS:
                    position = intent.getBundleExtra("data").getInt("data");
                    textStatus.setText(statusData.get(position).getStatus());
                    bol1 = true;
                    break;
                case REQUEST_SOURCE:
                    position2 = intent.getBundleExtra("data").getInt("data");
                    textSouce.setText(soursData.getChannellist().get(position2).getTitle());
                    bol2 = true;
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.relStatus:
                if(statusData == null || statusData.size() == 0){
                    MethodUtils.showToast(this, "数据请求中,请稍后...", Toast.LENGTH_SHORT);
                    getData(IApi.NETWORK_METHOD_GET, TAG_STATUS, IApi.URL_STATUS, null);
                    break;
                }
                intent = new Intent(this, ListTwoActivity.class);
                intent.putExtra("DATA", statusData);
                startActivityForResult(intent, REQUEST_STATUS);
                break;
            case R.id.relsoucre:
                if(soursData == null || soursData.getChannellist()== null || soursData.getChannellist().size() == 0){
                    MethodUtils.showToast(this, "数据请求中,请稍后...", Toast.LENGTH_SHORT);
                    getData(IApi.NETWORK_METHOD_GET, TAG_SOURCE, IApi.URL_GUIDLIST, null);
                    break;
                }
                intent = new Intent(this, ListTwoActivity.class);
                intent.putExtra("DATA1", soursData);
                startActivityForResult(intent, REQUEST_SOURCE);
                break;
            case R.id.view_title_back:
                finish();
                break;
            case R.id.textAdd:
                String name = editName.getText().toString();
                if(TextUtils.isEmpty(name)){
                    MethodUtils.showToast(this, "姓名不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                String phone = editPhone.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    MethodUtils.showToast(this, "手机号不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                String contractId = editNumber.getText().toString();
                if(TextUtils.isEmpty(contractId)){
                    MethodUtils.showToast(this, "合同编号号不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                String title = editTitle.getText().toString();
                if(TextUtils.isEmpty(title)){
                    MethodUtils.showToast(this, "合同标题号不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                String status = textStatus.getText().toString();
                if(TextUtils.isEmpty(status)){
                    MethodUtils.showToast(this, "状态不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                String refereeName = editRecommentName.getText().toString();
                String refereePhone = editRecommentPhone.getText().toString();
                String amount = editTotalMoney.getText().toString();
                if(TextUtils.isEmpty(amount)){
                    MethodUtils.showToast(this, "总价格不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                String saleMoney = editSaleMoney.getText().toString();
                if(TextUtils.isEmpty(saleMoney)){
                    MethodUtils.showToast(this, "销售提成不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                String refereeMoney = editSaleTotalMoney.getText().toString();
//                String channeId = textSouce.getText().toString();
//                if(TextUtils.isEmpty(channeId)){
//                    MethodUtils.showToast(this, "来源板块不能为空", Toast.LENGTH_SHORT);
//                    break;
//                }
                String textContent = editText.getText().toString();
                if(TextUtils.isEmpty(textContent)){
                    MethodUtils.showToast(this, "合同内容不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                if(dialog != null){
                    dialog.show();
                }
                sendData(name, phone, contractId, title,
                        status, amount, saleMoney, refereeMoney,
                        textContent, "", refereeName, refereePhone);
                break;

        }
    }

    private void sendData(String name, String phone, String contractid,
                          String contractTitle, String statusId, String amount,
                          String saleAmount, String refrecontractideeamount, String text,
                          String channelid, String refereeName, String refreephone) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sid", MyApplication.getInstance().getUserInfor().getUserdetail().getSid());
        map.put("token",MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        map.put("uid", currentData.getDetail().getUid());
        map.put("cusname", name);
        map.put("mobile", phone);
        map.put("contractid", contractid);
        map.put("title", contractTitle);

        if(bol1 && TextUtils.equals("update", flag)){
            map.put("statusid", statusData.get(position).getStatusid());
        } else {
            map.put("statusid", currentData.getDetail().getStatusid());
        }

        if(!TextUtils.isEmpty(refreephone)){
            map.put("refereemobile", refreephone);
        }
        if(!TextUtils.isEmpty(refereeName)){
            map.put("refereename", refereeName);
        }
        if(!TextUtils.isEmpty(refrecontractideeamount)){
            map.put("refereeamount", refrecontractideeamount);
        }
        map.put("amount", amount);
        map.put("saleamount", saleAmount);
        map.put("text", text);
//        String chaId = currentData.getSaledetail().getChannelid();
//        if(bol2){
//            chaId  = soursData.getChannellist().get(position2).getId() + "";
//        }
//        map.put("channelid", chaId);

        if (TextUtils.equals("update", flag)){
            map.put("action","update");
            getData(IApi.NETWORK_METHOD_POST, TAG_UPDATE_BUSSINESS_CUSTOMER, IApi.URL_SALE_BUSSINESS_LIST + "/" + currentData.getDetail().getBid(), map);
        }


    }

    public class StatusData implements Serializable {

        private static final long serialVersionUID = -5966663395723003992L;

        private String statusid;
        private String status;
        private String value;

        public String getStatusid() {
            return statusid;
        }

        public void setStatusid(String statusid) {
            this.statusid = statusid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }
}