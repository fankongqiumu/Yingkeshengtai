package com.yingke.shengtai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.ResultSuccessData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.TitleView;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyiheng on 15-8-30.
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener{
    private TextView textYanzhengCode, textZhuce, textXieyi;
    private EditText editPhone, editYanzhengCode, editSecreate;
    private TitleView titleView;
    private Type type;
    private CountDownTimer time;
    private String cityAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitry_register_one);
        initUi();
    }

    private void initUi() {
        titleView = (TitleView) findViewById(R.id.fragment_title);
        titleView.setTitleView(R.string.zhuce);

        textYanzhengCode = (TextView)findViewById(R.id.reciver);
        textZhuce = (TextView)findViewById(R.id.zhuce);
        textXieyi = (TextView)findViewById(R.id.xieyi);

        editPhone = (EditText)findViewById(R.id.editPhone);
        editYanzhengCode = (EditText)findViewById(R.id.write_yanzhengma);
        editSecreate = (EditText)findViewById(R.id.editsecreste);

        textYanzhengCode.setOnClickListener(this);
        textZhuce.setOnClickListener(this);
        textXieyi.setOnClickListener(this);

        type = new TypeToken<ResultSuccessData>(){}.getType();
        time = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long second) {
                textYanzhengCode.setText(second / 1000 + getString(R.string.send_send));
            }

            @Override
            public void onFinish() {
                textYanzhengCode.setBackgroundResource(R.drawable.green_bg);
                textYanzhengCode.setText(getString(R.string.receive_yanzheng_code));
            }
        };
    }

    @Override
    public void handleMsg(Message msg) {

        switch (msg.what){
            case ZHUCE_RECIVED_CODE:
                String json = msg.getData().getString(Constant.JSON_DATA);
                if(TextUtils.equals(json, getString(R.string.try_agin))){
                    return;
                }
                ResultSuccessData data = JosnUtil.gson.fromJson(json, type);
                if(TextUtils.equals(data.getResult(), "0")){
                    MethodUtils.showToast(this, data.getMessage(), Toast.LENGTH_SHORT);
                    break;
                }
                MethodUtils.showToast(this, getString(R.string.succes_send), Toast.LENGTH_SHORT);
                break;
            case TAG_PHONE_ADRESS:
                String jsons = msg.getData().getString(Constant.JSON_DATA);
                if(TextUtils.equals(jsons, getString(R.string.try_agin))){
                    break;
                }
                CityData data1 = JosnUtil.gson.fromJson(jsons.substring(jsons.indexOf("{"), jsons.indexOf("}") + 1), new TypeToken<CityData>(){}.getType());
                if(TextUtils.isEmpty(data1.getProvince()) && TextUtils.isEmpty(data1.getCityname())){
                    break;
                }
                cityAdress = data1.getProvince() + " " + data1.getCityname();
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reciver:
                String phone = editPhone.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    MethodUtils.showToast(this, getResources().getString(R.string.phone_cant_null), Toast.LENGTH_SHORT);
                    break;
                }
                time.start();
                getData(IApi.NETWORK_METHOD_GET, TAG_PHONE_ADRESS, IApi.URL_GET_PLACE + phone, null);
                askData(phone);
                textYanzhengCode.setBackgroundResource(R.mipmap.gray_bg);
                break;
            case R.id.zhuce:
                String phones = editPhone.getText().toString().trim();
                if(TextUtils.isEmpty(phones) || !phones.startsWith("1") || phones.length() != 11){
                    MethodUtils.showToast(this, getResources().getString(R.string.phone_cant_null), Toast.LENGTH_SHORT);
                    break;
                }
                String code = editYanzhengCode.getText().toString().trim();
                if(TextUtils.isEmpty(code)){
                    MethodUtils.showToast(this, getResources().getString(R.string.code_cant_null), Toast.LENGTH_SHORT);
                    break;

                }
                String secreate = editSecreate.getText().toString().trim();
                if(TextUtils.isEmpty(secreate)){
                    MethodUtils.showToast(this, getResources().getString(R.string.secreate_cant_null), Toast.LENGTH_SHORT);
                    break;
                }
                Intent inten = new Intent(this, RegistTwoActivity.class);
                inten.putExtra(Constant.DATA_PHONE, phones);
                inten.putExtra(Constant.DATA_CODE, code);
                inten.putExtra(Constant.DATA_SECREATE, secreate);
                if(TextUtils.isEmpty(cityAdress)){
                    cityAdress = "未知";
                }
                inten.putExtra(Constant.DATA_ADRESS, cityAdress);
                startActivity(inten);
                break;
            case R.id.xieyi:
                Intent intent = new Intent(this, TermsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void askData(String phone) {
        String number = "";
        for (int i = 0; i < 4; i++) {
            int a = (int)(Math.random() * 10);
            if(a == 10){
                a = (int)(Math.random() * 10);
            }
            number = number + a;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", phone);
        map.put("identcode", number);
        getData(IApi.NETWORK_METHOD_POST, ZHUCE_RECIVED_CODE, IApi.URL_ZHUCE_GTECODE, map);
    }

    public class CityData implements Serializable{

        private static final long serialVersionUID = 1918996002573943015L;
        /**
         * mobile : 15850781443
         * province : 江苏
         * isp : 中国移动
         * stock : 1
         * amount : 10000
         * maxprice : 0
         * minprice : 0
         * cityname : 南京
         */

        private String mobile;
        private String province;
        private String isp;
        private String stock;
        private String amount;
        private String maxprice;
        private String minprice;
        private String cityname;

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setIsp(String isp) {
            this.isp = isp;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public void setMaxprice(String maxprice) {
            this.maxprice = maxprice;
        }

        public void setMinprice(String minprice) {
            this.minprice = minprice;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getMobile() {
            return mobile;
        }

        public String getProvince() {
            return province;
        }

        public String getIsp() {
            return isp;
        }

        public String getStock() {
            return stock;
        }

        public String getAmount() {
            return amount;
        }

        public String getMaxprice() {
            return maxprice;
        }

        public String getMinprice() {
            return minprice;
        }

        public String getCityname() {
            return cityname;
        }
    }
}
