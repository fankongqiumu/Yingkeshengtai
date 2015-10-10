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

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.api.RequestListener;
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
 * Created by yanyiheng on 15-9-3.
 */
public class ChangePassWordActivity extends BaseActivity implements View.OnClickListener,RequestListener {
    private EditText editOrigin, editNew, editSure;
    private TextView textConfirm;
    private ProgressDialog dialog;
    private TitleView titleView;

    private MessageData data;
    private Type type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initUi();
    }

    private void initUi() {
        titleView = (TitleView)findViewById(R.id.fragment_title);
        titleView.setTitleView(R.string.update_password);
        titleView.getImageBack().setImageResource(R.mipmap.nav_close_icon);
        editOrigin = (EditText)findViewById(R.id.origin_password);
        editNew = (EditText)findViewById(R.id.new_password);
        editSure = (EditText)findViewById(R.id.sure_password);
        textConfirm = (TextView)findViewById(R.id.sure);
        textConfirm.setOnClickListener(this);
        dialog = new ProgressDialog(this);


        type = new TypeToken<MessageData>() {}.getType();
    }

    @Override
    public void handleMsg(Message msg) {
        /**{"result":0,"message":"密码错误"}*/
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.equals(getString(R.string.try_agin), json)){
            if(dialog != null && dialog.isShowing()){
                MethodUtils.showToast(this, getString(R.string.try_agin_agin), Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
            return;
        }
        switch (msg.what){
            case TAG_UPDATE_PASSWORD:
                data = JosnUtil.gson.fromJson(json, type);
                if(dialog != null){
                    dialog.dismiss();
                }
                if(TextUtils.equals(data.getResult(), "0")){
                    MethodUtils.showToast(this, data.getMessages(), Toast.LENGTH_SHORT);
                } else {
                    MethodUtils.setString(Constant.SHAREDREFERENCE_CONFIG_USER, Constant.SHAREDREFERENCE_CONFIG_USER, "");
                    MethodUtils.showToast(this, getString(R.string.success), Toast.LENGTH_SHORT);
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    this.finish();
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sure:
                String origin = editOrigin.getText().toString().trim();
                if(TextUtils.isEmpty(origin)){
                    MethodUtils.showToast(this, getString(R.string.secreate_cant_null), Toast.LENGTH_SHORT);
                    break;
                }
                String news = editNew.getText().toString().trim();
                if(TextUtils.isEmpty(news)){
                    MethodUtils.showToast(this, getString(R.string.secreate_cant_null), Toast.LENGTH_SHORT);
                    break;
                }
                String sure = editSure.getText().toString().trim();
                if(TextUtils.isEmpty(sure)){
                    MethodUtils.showToast(this, getString(R.string.secreate_cant_null), Toast.LENGTH_SHORT);
                    break;
                }
                if(!TextUtils.equals(news, sure)){
                    MethodUtils.showToast(this, getString(R.string.no_password), Toast.LENGTH_SHORT);
                    break;
                }


                sendData(origin, news);
                if(dialog != null && !dialog.isShowing()){
                    dialog.show();
                }
                break;
        }
    }

    private void sendData(String origin, String news) {
        Map map = new HashMap<String, String>();
        map.put("id", MyApplication.getInstance().getUserInfor().getUserdetail().getUid());
        map.put("token",MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        map.put("usertype",MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype());
        map.put("action","change");
        map.put("oldpassword",origin);
        map.put("newpassword",news);
        getData(IApi.NETWORK_METHOD_POST, TAG_UPDATE_PASSWORD, IApi.URL_UPDATE_PASSWORD, map);

    }

    public class MessageData implements Serializable{
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
