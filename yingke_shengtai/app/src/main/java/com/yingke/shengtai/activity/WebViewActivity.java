package com.yingke.shengtai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yingke.shengtai.moudle.CenterListData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.view.TitleView;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class WebViewActivity extends Activity{
    private WebView webView;
    private TitleView titleView;
    private CenterListData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            data = (CenterListData)getIntent().getSerializableExtra(Constant.DATA_GUIDE_CENTER);
        } else {
            data = (CenterListData)savedInstanceState.getSerializable(Constant.DATA_GUIDE_CENTER);
        }
        setContentView(R.layout.activity_webview);
        initUi();

    }

    private void initUi() {
        titleView = (TitleView)findViewById(R.id.fragment_title);
//        titleView.setTitleView(data.getTitle());
        webView = (WebView)findViewById(R.id.activity_web_view);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                if (TextUtils.equals(client.getLoadingUrl(), view.getUrl()) && newProgress == 100) {
//                    CommonUtil.dismissProgressDialog(ThemeWebViewActivity.this);
//                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        webView.loadUrl("http://www.baidu.com");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constant.DATA_GUIDE_CENTER, data);
    }
}
