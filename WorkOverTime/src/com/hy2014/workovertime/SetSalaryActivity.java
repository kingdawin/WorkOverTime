/*
 *@author Dawin,2014-12-29
 *
 *
 *
 */
package com.hy2014.workovertime;

import com.hy2014.workovertime.dao.SettingDao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetSalaryActivity extends BaseActivity
{
	private EditText etNormalSalary;
	private EditText etWeekSalary;
	private EditText etHolidaySalary;
	private SettingDao settingDao;
	private float salary[] = new float[3];

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_salary);
		settingDao = new SettingDao(context);
		Intent intent = getIntent();
		salary[0] = intent.getFloatExtra("normalSalary", 0.0F);
		salary[1] = intent.getFloatExtra("weekSalary", 0.0F);
		salary[2] = intent.getFloatExtra("holidaySalary", 0.0F);

		etNormalSalary = (EditText) findViewById(R.id.et_normal_salary);
		etWeekSalary = (EditText) findViewById(R.id.et_week_salary);
		etHolidaySalary = (EditText) findViewById(R.id.et_holiday_salary);

		etNormalSalary.setText(salary[0] + "");
		etWeekSalary.setText(salary[1] + "");
		etHolidaySalary.setText(salary[2] + "");
	}

	public void clickView(View view)
	{

		float salary[] = new float[3];
		// 输入的数值
		try
		{
			salary[0] = Float.parseFloat(etNormalSalary.getText().toString().trim());
		} catch (Exception e)
		{
			Toast.makeText(context, "etNormalSalary输入有误", 1).show();
			return;
		}
		try
		{
			salary[1] = Float.parseFloat(etWeekSalary.getText().toString().trim());
		} catch (Exception e)
		{
			Toast.makeText(context, "etWeekSalary输入有误", 1).show();
			return;
		}
		try
		{
			salary[2] = Float.parseFloat(etHolidaySalary.getText().toString().trim());
		} catch (Exception e)
		{
			Toast.makeText(context, "etHolidaySalary输入有误", 1).show();
			return;
		}

		switch (view.getId())
		{
		// 保存
		case R.id.btn_confirm:
			// settingDao.updateBaseSalary(salary);
			settingDao.updateNormalSalary(salary[0]);
			settingDao.updateWeekSalary(salary[1]);
			settingDao.updateHolidaySalary(salary[2]);
			Toast.makeText(context, "成功设置", 1).show();
			finish();
			break;
		// 取消
		case R.id.btn_cancle:
			finish();
			break;
		}

	}
}
