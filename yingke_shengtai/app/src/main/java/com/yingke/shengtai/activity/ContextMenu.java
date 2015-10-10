/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yingke.shengtai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.easemob.chat.EMMessage;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;

public class ContextMenu extends Activity {

	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int type = getIntent().getIntExtra("type", -1);
		if (type == EMMessage.Type.TXT.ordinal()) {
		    setContentView(R.layout.context_menu_for_text);
		} else if(type == 0x8866){
			setContentView(R.layout.activity_contextmenu_picker);
		} else if(type == 0x6688){
			setContentView(R.layout.dialog_sex);
		} else if(type == 0x7788){
			setContentView(R.layout.layout_star);
		}

		position = getIntent().getIntExtra("position", -1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void copy(View view){
		setResult(ChatActivity.RESULT_CODE_COPY, new Intent().putExtra("position", position));
		finish();
	}
	public void photo(View view){
		setResult(ChatActivity.RESULT_CODE_PICTURE, new Intent());
		finish();
	}
	public void takePhoto(View view){
		setResult(ChatActivity.RESULT_CODE_TAKE_PICTURE, new Intent());
		finish();
	}
	public void manSexClick(View view){
		setResult(Constant.SEX_MAN, new Intent());
		finish();
	}

	public void womanClick(View view){
		setResult(Constant.SEX_WOMAN, new Intent());
		finish();
	}

	public void oneStar(View view){
		setResult(SearchDetailActivity.ONE, new Intent());
		finish();
	}

	public void twoStar(View view){
		setResult(SearchDetailActivity.TWO, new Intent());
		finish();
	}
	public void threeStar(View view){
		setResult(SearchDetailActivity.THREE, new Intent());
		finish();
	}
	public void fourStar(View view){
		setResult(SearchDetailActivity.FOUR, new Intent());
		finish();
	}
	public void fiveStar(View view){
		setResult(SearchDetailActivity.FIVE, new Intent());
		finish();
	}
}
