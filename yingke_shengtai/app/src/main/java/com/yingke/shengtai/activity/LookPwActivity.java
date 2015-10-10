package com.yingke.shengtai.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.api.IApi;
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
 * Created by yanyiheng on 15-9-19.
 */
public class LookPwActivity extends BaseActivity {
    private EditText  editText;
    private TextView textConfirm;
    private TitleView titleView;
    private ProgressDialog dialog;
    private MessageData data;
    private Type type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_pw);

        editText = (EditText) findViewById(R.id.origin_password);
        titleView = (TitleView)findViewById(R.id.fragment_title);
        titleView.setTitleView("忘记密码");
        titleView.getImageBack().setImageResource(R.mipmap.nav_close_icon);
        textConfirm = (TextView)findViewById(R.id.sure);
        dialog = new ProgressDialog(this);
        dialog.setMessage("请稍候...");
        type = new TypeToken<MessageData>() {}.getType();
        textConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = editText.getText().toString().trim();
                if(TextUtils.isEmpty(phone) || !phone.startsWith("1") || phone.length() != 11){
                    MethodUtils.showToast(LookPwActivity.this, getResources().getString(R.string.phone_cant_null), Toast.LENGTH_SHORT);
                    return;
                }
                sendData(phone);
            }
        });
    }

    public void sendData(String phone){
        if(dialog != null) {
            dialog.show();
        }
        Map map = new HashMap<String, String>();
        map.put("action","forgot");
        map.put("mobile",phone);
        getData(IApi.NETWORK_METHOD_POST, FORGET_PASSWORD, IApi.URL_UPDATE_PASSWORD, map);
    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.equals(getString(R.string.try_agin), json)){
            if(dialog != null && dialog.isShowing()){
                MethodUtils.showToast(this, getString(R.string.try_agin_agin), Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
            return;
        }
        if(msg.what == FORGET_PASSWORD){
            data = JosnUtil.gson.fromJson(json, type);
            if(dialog != null){
                dialog.dismiss();
            }
            if(TextUtils.equals(data.getResult(), "0")){
                MethodUtils.showToast(this, data.getMessages(), Toast.LENGTH_SHORT);
            } else {
                MethodUtils.showToast(this, "密码已通过短信发送,请注意查看！", Toast.LENGTH_SHORT);
                this.finish();
            }
        }
    }

    public class MessageData implements Serializable {
        private static final long serialVersionUID = -4762800245199177220L;
        String result;
        String message;

        public String getResult() {
            return result;
        }

        public String getMessages() {
            return message;
        }
    }
}
