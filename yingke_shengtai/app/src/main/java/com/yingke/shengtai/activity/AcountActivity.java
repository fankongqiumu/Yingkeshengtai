package com.yingke.shengtai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.api.RequestListener;
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
 * Created by yanyiheng on 15-9-3.
 */
public class AcountActivity extends BaseActivity implements View.OnClickListener,RequestListener {
    private EditText eidtName, editDisplayname, editPlace;
    private static final int REQUEST_CODE_CONTEXT_MENU = 88;
    private LinearLayout lineDisPlayName;
    private View lineDisplayLine;
    private TextView textConfirm, editSex;
    private ProgressDialog dialog;
    private TitleView titleView;

    private UserInforData data;
    private Type type;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount);
        initUi();
    }

    private void initUi() {
        titleView = (TitleView)findViewById(R.id.fragment_title);
        titleView.setTitleView(R.string.update_yout_acount);
        titleView.getImageBack().setImageResource(R.mipmap.nav_close_icon);
        eidtName = (EditText)findViewById(R.id.name);
        editDisplayname = (EditText)findViewById(R.id.diplayname);
        editPlace = (EditText)findViewById(R.id.place);
        editSex = (TextView)findViewById(R.id.sex);
        lineDisPlayName = (LinearLayout)findViewById(R.id.layout0);
        lineDisplayLine = findViewById(R.id.line0);
        textConfirm = (TextView)findViewById(R.id.sure);
        textConfirm.setOnClickListener(this);
        dialog = new ProgressDialog(this);

        eidtName.setText(MyApplication.getInstance().getUserInfor().getUserdetail().getName());
        editPlace.setText(MyApplication.getInstance().getUserInfor().getUserdetail().getLocation());
        sex = MyApplication.getInstance().getUserInfor().getUserdetail().getSex();
        editSex.setText(TextUtils.equals("1", sex) ? "女":"男");
        editDisplayname.setText(MyApplication.getInstance().getUserInfor().getUserdetail().getName());

        if(!TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "1")){
            lineDisPlayName.setVisibility(View.VISIBLE);
            lineDisplayLine.setVisibility(View.VISIBLE);
            editDisplayname.setText(MyApplication.getInstance().getUserInfor().getUserdetail().getDisplayname());
        }
        editSex.setOnClickListener(this);
        type = new TypeToken<UserInforData>() {}.getType();
    }

    @Override
    public void handleMsg(Message msg) {
    String json = msg.getData().getString(Constant.JSON_DATA);
    if(json != null && json.length() <= 10){
            if(dialog != null && dialog.isShowing()){
                MethodUtils.showToast(this, getString(R.string.try_agin_agin), Toast.LENGTH_SHORT);
                dialog.dismiss();;
            }
            return;
        }
       switch (msg.what){
           case TAG_UPDATE_INFOR:
               data = JosnUtil.gson.fromJson(json, type);
               if(dialog != null){
                   dialog.dismiss();
               }
               if(TextUtils.equals(data.getResult(), "1")){
                   MethodUtils.setString(Constant.SHAREDREFERENCE_CONFIG_USER, Constant.SHAREDREFERENCE_CONFIG_USER, json);
                   MyApplication.getInstance().setUserInfor(data);
                   MethodUtils.showToast(this, getString(R.string.success), Toast.LENGTH_SHORT);
               } else {
                   MethodUtils.showToast(this, getResources().getString(R.string.try_agin_agin), Toast.LENGTH_SHORT);
               }
               break;
       }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sure:
                String name = eidtName.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    MethodUtils.showToast(this, getString(R.string.input_your_name), Toast.LENGTH_SHORT);
                    break;
                }
                String sexs = editSex.getText().toString().trim();
                if(TextUtils.isEmpty(sexs)){
                    MethodUtils.showToast(this, getString(R.string.input_your_sex), Toast.LENGTH_SHORT);
                    break;
                }
                String place = editPlace.getText().toString().trim();
                if(TextUtils.isEmpty(place)){
                    MethodUtils.showToast(this, getString(R.string.input_your_adress), Toast.LENGTH_SHORT);
                    break;
                }

                String displayname = null;
                if(!TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "1")){
                    displayname = editSex.getText().toString().trim();
                    if(TextUtils.isEmpty(displayname)){
                        MethodUtils.showToast(this, getString(R.string.input_your_diplayname), Toast.LENGTH_SHORT);
                        break;
                    }
                }
                sendData(name, sex, place, displayname);
                if(dialog != null && !dialog.isShowing()){
                    dialog.show();
                }
                break;
            case R.id.sex:
                startActivityForResult(
                        (new Intent(this, ContextMenu.class)).putExtra("type",
                                0x6688), REQUEST_CODE_CONTEXT_MENU);
                break;
        }
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

    private void sendData(String name, String sex, String place, String displayname) {
        Map map = new HashMap<String, String>();
        String Id = MyApplication.getInstance().getUserInfor().getUserdetail().getSid();
        if(TextUtils.isEmpty(Id)){
            Id = MyApplication.getInstance().getUserInfor().getUserdetail().getUid();
        }
        map.put("id", Id);
        map.put("token",MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        map.put("usertype",MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype());
        map.put("action","update");
        map.put("name",name);
        map.put("sex",sex);
        map.put("location", place);
        if (!(TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "1") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "2") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "3") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "0"))){
            map.put("displayname", displayname);
        }
        getData(IApi.NETWORK_METHOD_POST, TAG_UPDATE_INFOR, IApi.URL_UPDATE_INFOR, map);

    }
}
