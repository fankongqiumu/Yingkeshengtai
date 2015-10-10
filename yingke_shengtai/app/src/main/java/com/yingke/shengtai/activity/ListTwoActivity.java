package com.yingke.shengtai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yingke.shengtai.moudle.GuideListData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-9-13.
 */
public class ListTwoActivity extends Activity {
    private ListView listView;
    private ArrayList<BussinessUpdateActivity.StatusData> statusData;
    private ArrayList<GuideListData> soursData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            if(savedInstanceState.getSerializable("DATA") != null)
                statusData = (ArrayList<BussinessUpdateActivity.StatusData>)savedInstanceState.getSerializable("DATA");
            else
                soursData = (ArrayList<GuideListData>)savedInstanceState.getSerializable("DATA1");
        } else {
            if(getIntent().getSerializableExtra("DATA") != null)
                statusData = (ArrayList<BussinessUpdateActivity.StatusData>)getIntent().getSerializableExtra("DATA");
            else
                soursData = (ArrayList<GuideListData>)getIntent().getSerializableExtra("DATA1");
        }
        setContentView(R.layout.activity_list);
        initUi();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(statusData == null){
            SimpleAdapter2 adapter2 = new SimpleAdapter2();
            listView.setAdapter(adapter2);
        } else {
            SimpleAdapter adapter = new SimpleAdapter();
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle b = new Bundle();
                b.putInt("data", i);
                setResult(RESULT_OK, ListTwoActivity.this.getIntent().putExtra("data", b));
                ListTwoActivity.this.finish();
            }
        });
    }

    private void initUi() {
        listView = (ListView)findViewById(R.id.listView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(statusData != null){
            outState.putSerializable("DATA", statusData);
        } else {
            outState.putSerializable("DATA1", soursData);
        }

    }

    public class SimpleAdapter extends BaseAdapter {

        public SimpleAdapter(){
        }

        @Override
        public int getCount() {
            return statusData.size();
        }

        @Override
        public Object getItem(int i) {
            return statusData.get(i).getStatus();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if(view == null){
                vh = new ViewHolder();
                vh.textView = new TextView(ListTwoActivity.this);
                vh.textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, UiUtil.dip2px(60)));
                vh.textView.setGravity(Gravity.CENTER);
                vh.textView.setTextSize(17);
                vh.textView.setPadding(UiUtil.dip2px(20), 0 , UiUtil.dip2px(20), 0);
                vh.textView.setTextColor(0XFF404040);
                view = vh.textView;
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            vh.textView.setText(statusData.get(i).getStatus());
            return view;
        }

        class ViewHolder{
            TextView textView;
        }
    }

    public class SimpleAdapter2 extends BaseAdapter{

        public SimpleAdapter2(){
        }



        @Override
        public int getCount() {
            return soursData.size();
        }

        @Override
        public Object getItem(int i) {
            return soursData.get(i).getTitle();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if(view == null){
                vh = new ViewHolder();
                vh.textView = new TextView(ListTwoActivity.this);
                vh.textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, UiUtil.dip2px(60)));
                vh.textView.setGravity(Gravity.CENTER);
                vh.textView.setTextSize(17);
                vh.textView.setPadding(UiUtil.dip2px(20), 0 , UiUtil.dip2px(20), 0);
                vh.textView.setTextColor(0XFF404040);
                view = vh.textView;
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }
            vh.textView.setText(soursData.get(i).getTitle());
            return view;
        }

        class ViewHolder{
            TextView textView;
        }
    }
}
