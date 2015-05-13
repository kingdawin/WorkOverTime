/*
 *@author Dawin,2014-12-24
 *
 *
 *
 */
package com.hy2014.workovertime;

import com.hy2014.workovertime.dao.SettingDao;
import com.hy2014.workovertime.utils.LogUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Element.DataType;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 基本设置
 * 
 * @author Dawin 设置基本工资、平时加班费、周末加班费、假日加班费
 * 
 */
public class SetupActivity extends BaseActivity
{
	private SettingDao settingDao;
	private TextView tvNormalSalary;
	private TextView tvWeekSalary;
	private TextView tvHolidaySalary;
	private TextView tvMonthBaseSalary;
	private float[] salarys;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup);

		settingDao = new SettingDao(context);

		tvNormalSalary = (TextView) findViewById(R.id.tv_normal_salary);
		tvWeekSalary = (TextView) findViewById(R.id.tv_week_salary);
		tvHolidaySalary = (TextView) findViewById(R.id.tv_holiday_salary);
		tvMonthBaseSalary = (TextView) findViewById(R.id.tv_month_base_salary);

	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		salarys = settingDao.getSalarys();
		// 0:基本工资 1：平时工资 2周末工资 3假日工资
		tvMonthBaseSalary.setText("月基本工资 " + salarys[0] + "元");
		tvNormalSalary.setText(salarys[1] + "\n元/时");
		tvWeekSalary.setText(salarys[2] + "\n元/时");
		tvHolidaySalary.setText(salarys[3] + "\n元/时");
	}

	private EditText edtMonthSalary;

	// 设置基本工资
	public void clickView(View view)
	{

		Intent intent = null;
		switch (view.getId())
		{
		case R.id.tv_month_base_salary:
			LogUtil.e("click tv_month_base_salary");
			edtMonthSalary = new EditText(context);
			edtMonthSalary.setInputType(android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setView(edtMonthSalary);
			builder.setTitle("输入月基本工资");
			builder.setPositiveButton("保存", new  DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Float baseSalary=Float.parseFloat(edtMonthSalary.getText().toString().trim());	
					try
						{
							settingDao.updateBaseSalary(baseSalary);
						
							tvMonthBaseSalary.setText(	"月基本工资 " + baseSalary + "元");
						} catch (Exception e)
						{
							// TODO: handle exception
						}
					
					
				}
				
			});
			builder.setNegativeButton("取消",null);
			AlertDialog dialog = builder.create();
			dialog.show();
			
			break;
		// 工资设定
		case R.id.relLay_setup_salary:
			intent = new Intent(SetupActivity.this, SetupSalaryActivity.class);
			startActivity(intent);
			break;
		}
	}
}
