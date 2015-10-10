package com.yingke.shengtai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.UserInforData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.TitleView;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyiheng on 15-8-30.
 */
public class RegistTwoActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CONTEXT_MENU = 88;
    private String phone, code, secreate, name, place;
    private TextView textZhuce, editSex;
    private EditText editName;
    private EditText adress;
    private String add;
    private ProgressDialog dialog;
    private Type type;
    private UserInforData data;

    private String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            Intent intent = getIntent();
            phone = intent.getStringExtra(Constant.DATA_PHONE);
            code = intent.getStringExtra(Constant.DATA_CODE);
            secreate = intent.getStringExtra(Constant.DATA_SECREATE);
            place = intent.getStringExtra(Constant.DATA_ADRESS);
        } else {
            phone = savedInstanceState.getString(Constant.DATA_PHONE);
            code = savedInstanceState.getString(Constant.DATA_CODE);
            secreate = savedInstanceState.getString(Constant.DATA_SECREATE);
            place = savedInstanceState.getString(Constant.DATA_ADRESS);

        }
        setContentView(R.layout.activity_regis_two);
        initUi();
    }

    private void initUi() {
        editSex = (TextView)findViewById(R.id.sex);
        editName = (EditText)findViewById(R.id.name);

        textZhuce = (TextView)findViewById(R.id.zhuce);
        adress = (EditText) findViewById(R.id.adress);
        textZhuce.setOnClickListener(this);
        editSex.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        TitleView titleView = (TitleView) findViewById(R.id.fragment_title);
        titleView.setTitleView("注册");

        type = new TypeToken<UserInforData>() {}.getType();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case Constant.SEX_MAN:
                    sex = "0";
                    editSex.setText("男");
                    break;
                case Constant.SEX_WOMAN:
                    sex = "1";
                    editSex.setText("女");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.DATA_PHONE, phone);
        outState.putString(Constant.DATA_CODE, code);
        outState.putString(Constant.DATA_SECREATE, secreate);
        outState.putString(Constant.DATA_ADRESS, place);
    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        switch (msg.what){
            case ZHUCE_TWO:
                data = JosnUtil.gson.fromJson(json, type);
                if(!TextUtils.equals(data.getResult(), "0")){
                    if(data.getUserdetail() == null && data.getSaledetail() != null){
                        data.setUserdetail(data.getSaledetail());
                    }
                    MyApplication.getInstance().setUserInfor(data);
                    String password = "";
                    if (TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "1") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "2") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "3") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "0")) {
                        password = secreate;
                    } else {
                        password = Constant.NEW_PASSWORD;
                    }
                    EMChatManager.getInstance().login(MyApplication.getInstance().getUserInfor().getUserdetail().getImid(), password, new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    if(dialog != null){
                                        dialog.dismiss();
                                    }
                                    Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_LONG).show();
                                    Intent intent = null;
                                    if (TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "1") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "2") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "3") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "0")) {
                                        intent = new Intent(RegistTwoActivity.this, CustomerMainActivity.class);
                                    } else {
                                        intent = new Intent(RegistTwoActivity.this, SaleMainActivity.class);
                                    }
                                    startActivity(intent);
                                    finish();

                                }
                            });
                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }

                        @Override
                        public void onError(int code, String message) {
//                            Toast.makeText(getApplicationContext(), "登陆聊天服务器失败！", Toast.LENGTH_LONG).show();
                        }
                    });

                    MethodUtils.setString(Constant.SHAREDREFERENCE_CONFIG_USER, Constant.SHAREDREFERENCE_CONFIG_USER, json);


                } else {
                    if(dialog != null){
                        dialog.dismiss();
                    }
                    MethodUtils.showToast(this, data.getMessage(), Toast.LENGTH_SHORT);
                    MethodUtils.setString(Constant.SHAREDREFERENCE_CONFIG_USER, Constant.SHAREDREFERENCE_CONFIG_USER, "");

                }

                break;
            case GET_PHONE_PLACE:
                place = "未知";
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.zhuce:
                name = editName.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    MethodUtils.showToast(this, getResources().getString(R.string.input_your_name), Toast.LENGTH_SHORT);
                    break;
                }
                String sex = editSex.getText().toString().trim();
                if(TextUtils.isEmpty(sex)){
                    MethodUtils.showToast(this, getResources().getString(R.string.input_your_sex), Toast.LENGTH_SHORT);
                    break;

                }
                add = adress.getText().toString().trim();
                if(TextUtils.isEmpty(add)){
                    MethodUtils.showToast(this, "请输入您的地址!", Toast.LENGTH_SHORT);
                    break;

                }
                if(dialog != null){
                    dialog.setMessage(getResources().getString(R.string.loading));
                    dialog.show();
                }

                askData();
                break;
            case R.id.sex:
                startActivityForResult(
                        (new Intent(this, ContextMenu.class)).putExtra("type",
                                0x6688), REQUEST_CODE_CONTEXT_MENU);
                break;
        }
    }

    private void askData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("identcode", code);
        map.put("mobile", phone);
        map.put("name", name);
        map.put("sex", sex);
        map.put("password", secreate);
        map.put("devicenumber", MyApplication.getInstance().getPhoneId());
        map.put("interstchannel", "10,21,22");
        map.put("local", add);
        getData(IApi.NETWORK_METHOD_POST,ZHUCE_TWO,IApi.URL_ZHUCE_TWO,map);
    }
}
