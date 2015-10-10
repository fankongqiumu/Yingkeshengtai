package com.yingke.shengtai;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.yingke.shengtai.moudle.UserInforData;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.DemoHXSDKHelper;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class MyApplication extends Application{
    private static final String INSTALLATION = "INSTALLATION";
    public static String imei = "";
    public static String androidId = "";
    public static String deviceId = "";
    public static Context mContext = null;

    public static String currentUserNick = "";
    public static int SCREEN_WIDTH = -1;
    public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();

    private UserInforData userdetail;

    @Override
    public void onCreate() {
        super.onCreate();
        initFresco();
        try {
            deviceId = getDeviceId(mContext);
        } catch (Exception e) {

        }
        initUserInfor();
        hxSDKHelper.onInit(mContext);

    }


    private void initUserInfor() {
        String userJsonString = MethodUtils.getString(Constant.SHAREDREFERENCE_CONFIG_USER, Constant.SHAREDREFERENCE_CONFIG_USER);
        if(!TextUtils.isEmpty(userJsonString) && userJsonString.length() > 20){
            Type type = new TypeToken<UserInforData>() {}.getType();
            UserInforData data = JosnUtil.gson.fromJson(userJsonString, type);
            if(data.getUserdetail() == null && data.getSaledetail() != null){
                data.setUserdetail(data.getSaledetail());
            }
            userdetail = data;
        }

    }

    private void initFresco() {
        mContext = getApplicationContext();
        OkHttpClient okHttpClient = new OkHttpClient();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(getApplicationContext(), okHttpClient)
                .build();
        Fresco.initialize(getApplicationContext(), config);

    }

    public UserInforData getUserInfor(){
        return userdetail;
    }

    public void setUserInfor(UserInforData userdetail){
        this.userdetail = userdetail;
    }

    public String getPhoneId(){
        return deviceId;
    }

    private boolean isWardrobeProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        if(processInfos == null) {
            return true;
        }
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static MyApplication getInstance() {
        return (MyApplication) mContext;
    }

    public static Context getContextFromApplication() {
        return mContext;
    }

    private synchronized String deviceId(Context context) {
        File installation = new File(context.getFilesDir(), INSTALLATION);
        String sID = null;
        try {
            if (!installation.exists()) {
                writeInstallationFile(installation);
            }
            sID = readInstallationFile(installation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sID;
    }

    private String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes, "UTF-8");
    }

    private void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes("UTF-8"));
        out.close();
    }

    private String getDeviceId(Context context) {
        String deviceId = null;
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager != null) {
            deviceId = manager.getDeviceId();
            imei = manager.getDeviceId();
        }
        androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = deviceId(getContextFromApplication());
        }
        return deviceId.replaceAll(",", "");
    }

    public static int getScreenWidth() {
        if(SCREEN_WIDTH <= 0){
            setDisplayMetrics();
        }
        return SCREEN_WIDTH;
    }

    private static void setDisplayMetrics() {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        MyApplication.setScreenWidth(dm.widthPixels);
    }

    public static void setScreenWidth(int width) {
        SCREEN_WIDTH = width;
    }
}
