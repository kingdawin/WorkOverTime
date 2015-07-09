/*
 *@author Dawin,2014-12-24
 *
 *
 *
 */
package com.hy2014.workovertime.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.hy2014.workovertime.utils.LogUtil;

import android.R.integer;
import android.content.Context;
import android.test.AndroidTestCase;

public class OverTimeDaoTest extends AndroidTestCase
{
	Context context;
	//加班时数记录表
	public void testOverTimeDao()
	{
		OverTimeDao overTimeDao=new OverTimeDao(getContext());
		//overTimeDao.add(new String[]{String.valueOf(20141224),String.valueOf(1),String.valueOf(12.0),String.valueOf(0),String.valueOf(0)});
		//12月
	   // LogUtil.e("calOvertimeSalary="+overTimeDao.calOvertimeSalary(20141201, 20141231,true));
		// LogUtil.e("20141224="+overTimeDao.isExist(String.valueOf(20141224)));		
		//删除所有数据
		//overTimeDao.delete("20141224");
		
	    //overtimeDatas
		ArrayList<HashMap<String, Object>> overtimeDatas = new ArrayList<HashMap<String,Object>>();
		overtimeDatas = overTimeDao.selectOvertimeDatas();
		for (int i = 0; i < overtimeDatas.size(); i++)
			{
				LogUtil.e("date  " + overtimeDatas.get(i).get("date"));
				LogUtil.e("hours " + overtimeDatas.get(i).get("hours"));
			}	  
	}
	//基本设置表测试
	public void testSettingDao()
	{
		SettingDao settingDao=new SettingDao(getContext());
		settingDao.add(new String[]{String.valueOf(11.0),String.valueOf(15.0),String.valueOf(30.0),String.valueOf(5000.0)});
	//	LogUtil.e("settingDao.getSalary(1)平时工资---->"+settingDao.getSalary(1));
	//	LogUtil.e("settingDao.getSalary(2)周末工资---->"+settingDao.getSalary(2));
	//	LogUtil.e("settingDao.getSalary(3)假日工资---->"+settingDao.getSalary(3));
	//	LogUtil.e("settingDao.getSalary(0)基本工资---->"+settingDao.getSalary(0));		
	}
}
