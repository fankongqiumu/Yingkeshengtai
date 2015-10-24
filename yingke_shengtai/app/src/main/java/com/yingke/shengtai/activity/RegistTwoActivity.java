package com.yingke.shengtai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.AguideListData;
import com.yingke.shengtai.moudle.UserInforData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.utils.UiUtil;
import com.yingke.shengtai.view.TitleView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyiheng on 15-8-30.
 */
public class RegistTwoActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final int REQUEST_CODE_CONTEXT_MENU = 88;
    private String phone, code, secreate, name, place;
    private TextView textZhuce, editSex;
    private EditText editName;
    private ProgressDialog dialog;
    private Type type;
    private UserInforData data;
    private LinearLayout linearlayout;
    private AguideListData soursData;
    private CheckBox[] checkBoxes;
    private ArrayList<CheckBox> list;

    private String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
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
        getData(IApi.NETWORK_METHOD_GET, TAG_SOURCE, IApi.URL_GUIDLIST, null);
    }

    private void initUi() {
        editSex = (TextView) findViewById(R.id.sex);
        editName = (EditText) findViewById(R.id.name);

        textZhuce = (TextView) findViewById(R.id.zhuce);
        linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        textZhuce.setOnClickListener(this);
        editSex.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        TitleView titleView = (TitleView) findViewById(R.id.fragment_title);
        titleView.setTitleView("注册");

        type = new TypeToken<UserInforData>() {
        }.getType();

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
        switch (msg.what) {
            case ZHUCE_TWO:
                data = JosnUtil.gson.fromJson(json, type);
                if (!TextUtils.equals(data.getResult(), "0")) {
                    if (data.getUserdetail() == null && data.getSaledetail() != null) {
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
                                    if (dialog != null) {
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
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    MethodUtils.showToast(this, data.getMessage(), Toast.LENGTH_SHORT);
                    MethodUtils.setString(Constant.SHAREDREFERENCE_CONFIG_USER, Constant.SHAREDREFERENCE_CONFIG_USER, "");

                }

                break;
            case TAG_SOURCE:
                String json1 = msg.getData().getString(Constant.JSON_DATA);
                if (TextUtils.equals(getString(R.string.try_agin), json1)) {
                    return;
                }
                Type type1 = new TypeToken<AguideListData>() {
                }.getType();
                soursData = JosnUtil.gson.fromJson(json1, type1);
                initView();
                break;
        }

    }

    private void initView() {
        if (soursData == null || soursData.getChannellist() == null || soursData.getChannellist().size() == 0) {
            return;
        }
        if(list == null ){
            list = new ArrayList<CheckBox>();
        } else {
            list.clear();
        }

        for (int i = 0; i < soursData.getChannellist().size(); i++) {
            RelativeLayout linearLayout = new RelativeLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UiUtil.dip2px(45));
            linearlayout.addView(linearLayout, params);

            TextView textView = new TextView(this);
            textView.setTextSize(15);
            textView.setTextColor(0xFF9b9b9b);
            textView.setText(soursData.getChannellist().get(i).getShorttitle());
            RelativeLayout.LayoutParams paramsText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsText.addRule(RelativeLayout.CENTER_VERTICAL);
            linearLayout.addView(textView, paramsText);

            checkBoxes = new CheckBox[soursData.getChannellist().size()];
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setButtonDrawable(R.drawable.checkbox);
            list.add(checkBoxes[i]);
            RelativeLayout.LayoutParams paramsCheckBox = new RelativeLayout.LayoutParams(UiUtil.dip2px(30), UiUtil.dip2px(30));
            paramsCheckBox.addRule(RelativeLayout.CENTER_VERTICAL);
            paramsCheckBox.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            linearLayout.addView(checkBoxes[i], paramsCheckBox);

            View view = new View(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
            view.setBackgroundColor(0xFFE0E0E0);
            linearlayout.addView(view);


        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zhuce:
                name = editName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    MethodUtils.showToast(this, getResources().getString(R.string.input_your_name), Toast.LENGTH_SHORT);
                    break;
                }
                String sex = editSex.getText().toString().trim();
                if (TextUtils.isEmpty(sex)) {
                    MethodUtils.showToast(this, getResources().getString(R.string.input_your_sex), Toast.LENGTH_SHORT);
                    break;

                }
                if (dialog != null) {
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
        String interstchannel = "";
        int lenth = list.size();
        for (int i = 0; i < lenth; i++) {
            if (list.get(i).isChecked()) {
                if (i == lenth - 1) {
                    interstchannel = interstchannel + soursData.getChannellist().get(i).getId();
                } else {
                    interstchannel = interstchannel + soursData.getChannellist().get(i).getId() + ",";
                }

            }
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("identcode", code);
        map.put("mobile", phone);
        map.put("name", name);
        map.put("sex", sex);
        map.put("password", secreate);
        map.put("devicenumber", MyApplication.getInstance().getPhoneId());
        map.put("interstchannel", interstchannel);
        map.put("local", place);
        getData(IApi.NETWORK_METHOD_POST, ZHUCE_TWO, IApi.URL_ZHUCE_TWO, map);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
