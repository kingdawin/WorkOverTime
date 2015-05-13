/*
 *@author Dawin,2014-12-23
 *
 *
 *
 */
package com.hy2014.workovertime.dao;

import java.util.List;

import com.hy2014.workovertime.utils.LogUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 基本设置
 * 
 * @author Dawin
 * 
 */
public class SettingDao implements BaseDao
{
	private DBHelper helper;
	private SQLiteDatabase db;

	public SettingDao(Context context) {
		helper = new DBHelper(context);
	}
	
	/**基本工资*/ 
	public boolean updateBaseSalary(float baseSalary)
	{
		
		if (!isExistData())
			return false;
		
		String sql = "update [baseInfo] set  baseSalary="+baseSalary;
		db = helper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
		LogUtil.i("[updateBaseSalary] success");
		return true;
	}
	/**平时加班工资*/
	public boolean updateNormalSalary(float normalSalary)
	{
		if (!isExistData())
			return false;
		
		String sql ="update [baseInfo] set  normalSalary="+normalSalary;
		db = helper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
		LogUtil.i("[updateNormalSalary] success");
		return true;
	}
	/**周末加班工资*/
	public boolean updateWeekSalary(float weekSalary)
	{
		if (!isExistData())
			return false;
		
		String sql = "update [baseInfo] set  weekSalary="+weekSalary;
		db = helper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
		LogUtil.i("[updateWeekSalary] success");
		return true;
	}
	/**假日加班工资*/
	public boolean updateHolidaySalary(float holidaySalary)
	{
		if (!isExistData())
			return false;
		
		String sql ="update [baseInfo] set  holidaySalary="+holidaySalary;
		db = helper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
		LogUtil.i("[updateHolidaySalary] success");
		return true;
	}
	
	/** (non-Javadoc)
	 * @see com.hy2014.workovertime.dao.BaseDao#add(java.lang.String[])
	 * 存在记录则更新数据，不存在则插入数据
	 * data 4
	 * 1 normalSalary 2 weekSalary 3 holidaySalary 4 baseSalary
	 */
	@Override
	public boolean add(String[]data)
	{
		String sql = "insert into baseInfo(" + "normalSalary," 
									    	 + "weekSalary,"
									 		 + "holidaySalary," 
											 + "baseSalary,) "
											 + "values(?,?,?,?)";
		
		if (isExistData())
			{
				db = helper.getWritableDatabase();
				String sqlUpdate = "update baseInfo set normalSalary=?, weekSalary=?, holidaySalary=? , baseSalary=? ";
				db.execSQL(sqlUpdate, data);
			} else
			{
				db = helper.getWritableDatabase();
				db.execSQL(sql, data);
			}	
	
		db.close();
		return true;
	}

	/**
	 * 查询薪资信息
	 * @return
	 * 0:基本工资
	 * 1：平时工资
	 * 2周末工资
	 * 3假日工资
	 */
	public float[] getSalarys()
	{
	float[]	salarys=new float[4];
		String sql = "select * from baseInfo";
		db = helper.getWritableDatabase();		
		Cursor cursor=null;
		cursor=db.rawQuery(sql, null);
		while (cursor.moveToNext())
			{
				salarys[0] = cursor.getFloat(cursor.getColumnIndex("baseSalary"));
				salarys[1] = cursor.getFloat(cursor.getColumnIndex("normalSalary"));
				salarys[2] = cursor.getFloat(cursor.getColumnIndex("weekSalary"));
				salarys[3] = cursor.getFloat(cursor.getColumnIndex("holidaySalary"));
			}		
		cursor.close();
		db.close();
		return salarys;
	}
	@Override
	public boolean delete(String[]data)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update()
	{
		//xxx涉嫌严重违纪行为，正在接收党组织调查   
		//
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> select(String... s)
	{		
		return null;
	}
	
	
	
	public float selectSalary(String columnName)
	{
		String sql="select "+columnName+" from baseInfo ";
		Cursor cursor=null;
		try
			{
				db=helper.getWritableDatabase();
				cursor=db.rawQuery(sql, null);
				while (cursor.moveToNext())
					{						
						return cursor.getFloat(cursor.getColumnIndex(columnName));
					}	
			} catch (Exception e)
			{				
			}finally
			{
				cursor.close();
				db.close();
			}
		
		return 0;
	}
	@Override
	public boolean isExist(String data)
	{		
		return false;
	}

	public boolean isExistData()
	{
		String sql = "select * from baseInfo";
		Cursor cursor = null;
		try
			{
				db = helper.getWritableDatabase();
				cursor = db.rawQuery(sql, null);
				LogUtil.e("Setting 的数据记录共："+cursor.getCount()+" 条");
				while (cursor.moveToNext())
					{
						LogUtil.i("[baseInfo] isExistData");
						return true;
					}
			} catch (Exception e)
			{
				// TODO: handle exception
			} finally
			{
				cursor.close();
				db.close();
			}
		LogUtil.i("[baseInfo] isNotExist");
		return false;
	}
}
