/*
 *@author Dawin,2014-12-20
 *
 *
 *
 */
package com.hy2014.workovertime;

import com.hy2014.workovertime.dao.OverTimeDao;
import com.hy2014.workovertime.utils.LogUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 加班数选择
 * 
 * @author Dawin
 *
 */
public class SelectDateActivity extends Activity
{
	private Context context;
	private TextView tvToday;
	private TextView tvOverTime;
	private TextView tvRequestRest;
	/**data： 1日期 2日期类型 3加班时数 4加班开始时间 5加班结束时间*/ 
	private String data[] = new String[5];
	private float selectNumber = 0.0F;
	private int currentId;
	private int oldId = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 去标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_overtime_time);
		context = this;
		tvToday = (TextView) findViewById(R.id.tv_today);
		tvOverTime = (TextView) findViewById(R.id.tv_overtime);
		tvRequestRest = (TextView) findViewById(R.id.tv_request_rest);
		// 默认为今天
		tvToday.setText(getIntent().getStringExtra("date") + "");

		tvOverTime.setTextColor(0xff3fb9ed);
		// tvOverTime.setBackgroundColor(0xffffffff);
		tvOverTime.setBackgroundResource(R.drawable.checkbox);

		tvRequestRest.setTextColor(0xff969899);
		tvRequestRest.setBackgroundColor(0xffc3cad0);
	}

	public void clickView(View v)
	{
		switch (v.getId())
		{

		// 加班
		case R.id.tv_overtime:
			LogUtil.e("R.id.tv_overtime");
			tvOverTime.setTextColor(0xff3fb9ed);
			// tvOverTime.setBackgroundColor(0xffffffff);
			tvOverTime.setBackgroundResource(R.drawable.checkbox);

			tvRequestRest.setTextColor(0xff969899);
			tvRequestRest.setBackgroundColor(0xffc3cad0);
			break;
		// 休假
		case R.id.tv_request_rest:
			LogUtil.e("R.id.tv_request_rest");
			tvRequestRest.setTextColor(0xff3fb9ed);
			// tvRequestRest.setBackgroundColor(0xffffffff);
			tvRequestRest.setBackgroundResource(R.drawable.checkbox);

			tvOverTime.setTextColor(0xff969899);
			tvOverTime.setBackgroundColor(0xffc3cad0);

			break;

		case R.id.select_type:
			LogUtil.e("R.id.select_type");
			Toast.makeText(context, "开发中", 1).show();
			break;
		// 取消
		case R.id.btnCancle:
			finish();
			break;
			//保存加班时数到数据
		case R.id.confirm:

			OverTimeDao overTimeDao = new OverTimeDao(context);
			// data： 1日期 2日期类型 3加班时数 4加班开始时间 5加班结束时间
			data[0] = getIntent().getStringExtra("dateSave");
			data[1] = String.valueOf(getIntent().getIntExtra("dateType", -1));
			data[2] = String.valueOf(selectNumber);
			data[3] = "";
			data[4] = "";
			for (int i = 0; i < 5; i++)
			{
				LogUtil.e("data[" + i + "] " + data[i]);
			}
			overTimeDao.add(data);
			
			finish();
			break;
		}
	}

	// 选择时间，改变当前id和当前按钮背景蓝色。之前被选中的按钮设为未选中
	public void pickNumber(View view)
	{
		switch (view.getId())
		{
		// 0-5
		case R.id.time0:
			selectNumber = 0F;
			currentId = R.id.time0;
			break;
		case R.id.time1:
			selectNumber = 1F;
			currentId = R.id.time1;
			break;
		case R.id.time2:
			selectNumber = 2F;
			currentId = R.id.time2;
			break;
		case R.id.time3:
			selectNumber = 3F;
			currentId = R.id.time3;
			break;
		case R.id.time4:
			selectNumber = 4F;
			currentId = R.id.time4;
			break;
		case R.id.time5:
			selectNumber = 5F;
			currentId = R.id.time5;
			break;
		// 0.5-5.5
		case R.id.time0_5:
			selectNumber = 0.5F;
			currentId = R.id.time0_5;
			break;

		case R.id.time1_5:
			selectNumber = 1.5F;
			currentId = R.id.time1_5;
			break;
		case R.id.time2_5:
			selectNumber = 2.5F;
			currentId = R.id.time2_5;
			break;
		case R.id.time3_5:
			selectNumber = 3.5F;
			currentId = R.id.time3_5;
			break;
		case R.id.time4_5:
			selectNumber = 4.5F;
			currentId = R.id.time4_5;
			break;
		case R.id.time5_5:
			selectNumber = 5.5F;
			currentId = R.id.time5_5;
			break;
		// 6-11
		case R.id.time6:
			selectNumber = 6F;
			currentId = R.id.time6;
			break;

		case R.id.time7:
			selectNumber = 7F;
			currentId = R.id.time7;
			break;
		case R.id.time8:
			selectNumber = 8F;
			currentId = R.id.time8;
			break;
		case R.id.time9:
			selectNumber = 9F;
			currentId = R.id.time9;
			break;
		case R.id.time10:
			selectNumber = 10F;
			currentId = R.id.time10;
			break;
		case R.id.time11:
			selectNumber = 11F;
			currentId = R.id.time11;
			break;
		// 6.5-11.5
		case R.id.time6_5:
			selectNumber = 6.5F;
			currentId = R.id.time6_5;
			break;

		case R.id.time7_5:
			selectNumber = 7.5F;
			currentId = R.id.time7_5;
			break;
		case R.id.time8_5:
			selectNumber = 8.5F;
			currentId = R.id.time8_5;
			break;
		case R.id.time9_5:
			selectNumber = 9.5F;
			currentId = R.id.time9_5;
			break;
		case R.id.time10_5:
			selectNumber = 10.5F;
			currentId = R.id.time10_5;
			break;
		case R.id.time11_5:
			selectNumber = 11.5F;
			currentId = R.id.time11_5;
			break;
		// 12-17
		case R.id.time12:
			selectNumber = 12.5F;
			currentId = R.id.time12;
			break;

		case R.id.time13:
			selectNumber = 13.5F;
			currentId = R.id.time13;
			break;
		case R.id.time14:
			selectNumber = 14.5F;
			currentId = R.id.time14;
			break;
		case R.id.time15:
			selectNumber = 15.5F;
			currentId = R.id.time15;
			break;
		case R.id.time16:
			selectNumber = 16.5F;
			currentId = R.id.time16;
			break;
		case R.id.time17:
			selectNumber = 17.5F;
			currentId = R.id.time17;
			break;
		// 12.5-17.5
		case R.id.time12_5:
			selectNumber = 12.5F;
			currentId = R.id.time12_5;
			break;

		case R.id.time13_5:
			selectNumber = 13.5F;
			currentId = R.id.time13_5;
			break;
		case R.id.time14_5:
			selectNumber = 14.5F;
			currentId = R.id.time14_5;
			break;
		case R.id.time15_5:
			selectNumber = 15.5F;
			currentId = R.id.time15_5;
			break;
		case R.id.time16_5:
			selectNumber = 16.5F;
			currentId = R.id.time16_5;
			break;
		case R.id.time17_5:
			selectNumber = 17.5F;
			currentId = R.id.time17_5;
			break;

		// 18-23
		case R.id.time18:
			selectNumber = 18F;
			currentId = R.id.time18;
			break;

		case R.id.time19:
			selectNumber = 19F;
			currentId = R.id.time19;
			break;
		case R.id.time20:
			selectNumber = 20F;
			currentId = R.id.time20;
			break;
		case R.id.time21:
			selectNumber = 21F;
			currentId = R.id.time21;
			break;
		case R.id.time22:
			selectNumber = 22F;
			currentId = R.id.time22;
			break;
		case R.id.time23:
			selectNumber = 23F;
			currentId = R.id.time23;
			break;
		// 18.5-23.5
		case R.id.time18_5:
			selectNumber = 18.5F;
			currentId = R.id.time18_5;
			break;

		case R.id.time19_5:
			selectNumber = 19.5F;
			currentId = R.id.time19_5;
			break;
		case R.id.time20_5:
			selectNumber = 20.5F;
			currentId = R.id.time20_5;
			break;
		case R.id.time21_5:
			selectNumber = 21.5F;
			currentId = R.id.time21_5;
			break;
		case R.id.time22_5:
			selectNumber = 22.5F;
			currentId = R.id.time22_5;
			break;
		case R.id.time23_5:
			selectNumber = 23.5F;
			currentId = R.id.time23_5;
			break;
		}
		((Button) findViewById(currentId)).setBackgroundColor(0xff17b4fb);
		
		((Button) findViewById(currentId)).setTextColor(0xffffffff);
		if (oldId != -1 && oldId != currentId)
		{
			((Button) findViewById(oldId)).setBackgroundResource(R.drawable.checkbox);
			((Button) findViewById(oldId)).setTextColor(0xff000000);
		}
		oldId = currentId;
	}
}
