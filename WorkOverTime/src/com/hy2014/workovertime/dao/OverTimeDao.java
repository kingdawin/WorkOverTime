/*
 *@author Dawin,2014-12-24
 *
 *
 *
 */
package com.hy2014.workovertime.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hy2014.workovertime.utils.LogUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OverTimeDao implements BaseDao
{
	private DBHelper helper;
	private SQLiteDatabase db;
	private Context context;

	public OverTimeDao(Context context)
	{
		this.context = context;
		helper = new DBHelper(context);
	}

	/**
	 * 计算工资
	 * 
	 * @param fromDate
	 *            开始日期 8位整数
	 * @param toDate
	 *            结束日期 8位整数
	 * @param isOvertimeSalary
	 *            true计算加班工资,false计算总工资
	 * @return  加班工资总额
	 */
	public float[][] calOvertimeSalary(int fromDate, int toDate, boolean isOvertimeSalary)
	{
		float[][] salaryAndHours=new float[2][1];
		// String sql =
		// "select hours from overTimeTb where date>=  ? and date<=? and dateType=";
		String sql = "select hours from overTimeTb where date between  ? and ? and dateType=";
		float normalHours = 0;
		float weekHours = 0;
		float holidayHours = 0;
		db = helper.getWritableDatabase();

		SettingDao settingDao = new SettingDao(context);
		// settingDao.add(new
		// String[]{String.valueOf(11.0),String.valueOf(15.0),String.valueOf(30.0),String.valueOf(5000.0)});
		float salarys[] = settingDao.getSalarys();
		final float normalSalry = salarys[1];
		// LogUtil.e("平时工资---->"+normalSalry);
		final float weekSalry = salarys[2];
		// LogUtil.e("周末工资---->"+weekSalry);
		final float holidaySalry = salarys[3];
		// LogUtil.e("假日工资---->"+holidaySalry);
		final float baseSlary = salarys[0];
		// LogUtil.e("基本工资---->"+baseSlary);
		Cursor cursor = null;
		// big bang boom
		try
		{
			cursor = db.rawQuery(sql + "1", new String[] { String.valueOf(fromDate), String.valueOf(toDate) });
			//求工时总数
			while (cursor.moveToNext())
			{
				normalHours += cursor.getFloat(0);
			}
			LogUtil.i("normalHours=" + normalHours);
			cursor = db.rawQuery(sql + "2", new String[] { String.valueOf(fromDate), String.valueOf(toDate) });
			while (cursor.moveToNext())
			{
				weekHours += cursor.getFloat(0);
			}
			LogUtil.i("weekHours=" + weekHours);
			cursor = db.rawQuery(sql + "3", new String[] { String.valueOf(fromDate), String.valueOf(toDate) });
			while (cursor.moveToNext())
			{
				holidayHours += cursor.getFloat(0);
			}
			LogUtil.i("holidayHours=" + normalHours);
	     	if(isOvertimeSalary){
				salaryAndHours[0][0]=normalHours * normalSalry + weekHours * weekSalry + holidayHours * holidaySalry;
				salaryAndHours[1][0]=normalHours+weekHours+holidayHours;
			}else {
				salaryAndHours[0][0]=normalHours * normalSalry + weekHours * weekSalry + holidayHours * holidaySalry + baseSlary;
				salaryAndHours[1][0]=normalHours+weekHours+holidayHours+baseSlary;
			}
		/*	return isOvertimeSalary ? (normalHours * normalSalry + weekHours * weekSalry + holidayHours * holidaySalry)
					: (normalHours * normalSalry + weekHours * weekSalry + holidayHours * holidaySalry + baseSlary);*/

		} catch (Exception e)
		{
			LogUtil.e("calOvertimeSalary:error  " + e.getMessage());
		} finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
			db.close();
		}

		return salaryAndHours/*0.0F*/;
	}

	/**
	 * 查加班日期和时数 Create:20141226
	 * 
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> selectOvertimeDatas()
	{
		ArrayList<HashMap<String, Object>> overtimeDatas = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> datas;
		Cursor cursor = null;
		String sql = "select date,hours from overTimeTb";
		try
		{
			db = helper.getReadableDatabase();
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext())
			{
				datas = new HashMap<String, Object>();
				if (cursor.getInt(1) != 0)
				{
					// 日期
					datas.put("date", cursor.getInt(0));
					// 加班时数
					datas.put("hours", cursor.getInt(1));
					overtimeDatas.add(datas);
				}
			}
			return overtimeDatas;
		} catch (Exception e)
		{
			LogUtil.e("[selectOvertimeDatas]  error:" + e.getMessage());
		} finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
			db.close();
		}
		return overtimeDatas;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.hy2014.workovertime.dao.BaseDao#add(java.lang.String[])
	 *  data[]：     1日期 2日期类型 3加班时数 4加班开始时间 5加班结束时间
	 */
	@Override
	public boolean add(String[] data)
	{
		String sql = "insert into overTimeTb(" + "date," + "dateType," + "hours," + "fromTime," + "ToTime) "
				+ "values(?,?,?,?,?)";
		db = helper.getWritableDatabase();
		try
		{
			// 保证唯一
			if (isExist(data[0]))
			{
				// 更新
				String sqlUpdate = "update overTimeTb set hours=" + data[2] + " where date=" + data[0];
				// 不能加第二个参数null，加了不能更新
				db.execSQL(sqlUpdate);
			} else
			{
				LogUtil.i("插入加班工时记录");
				// 插入
				db.execSQL(sql, data);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		} finally
		{
			db.close();
		}
		return false;
	}

	@Override
	public boolean delete(String[] data)
	{
		String sql = "delete from overTimeTb";
		db = helper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
		return false;
	}

	@Override
	public boolean update()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> select(String... s)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExist(String data)
	{

		String sql = "select date from overTimeTb where date=?";
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, new String[] { data });
		while (cursor.moveToNext())
		{
			LogUtil.e("overTimeTb Exist data");
			return true;
		}
		return false;
	}
}
