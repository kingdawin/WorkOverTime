/*
 *@author Dawin,2014-12-19
 *
 *
 *
 */
package com.hy2014.workovertime.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper/*对数据库表进行管理*/
{   
	// 版本号
	private static final int VERSION = 1;
	// 数据库名
	private static final String DB_NAME = "workOverTimeDb.db";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}
	//基本信息表
	private static final String createBaseInfoTB=new StringBuffer()
	.append("CREATE TABLE [baseInfo] ")
	.append("([baseInfoId] integer primary key, ")//index=0 
	.append("[normalSalary] float,")//平时加班小时工资
	.append("[weekSalary] float, ")//周末加班小时工资
	.append("[holidaySalary] float, ")//假日加班小时工资	
	.append("[baseSalary] float,")//基本工资
	.append("[add1] nvarchar(50),")//备用
	.append("[add2] nvarchar(50),")//备用
	.append("[add3] nvarchar(50))").toString();//备用
	//加班表
	private static final String createOverTimeTB="create table [overTimeTb]" +
			"([overTimeId] integer primary key autoincrement," +/*add autoincrement*/
			"[date] integer," +
			"[dateType] integer," +
			"[hours] float," +
			"[fromTime] varchar(10)," +
			"[ToTime] varchar(10)," +
			"[add1] varchar(50)," +
			"[add2] varchar(50)," +
			"[add3] varchar(50)" +
			")";
    private static final String initBaseInfo="insert into baseInfo(" + "normalSalary," + "weekSalary," + "holidaySalary," + "baseSalary) values(0,0,0,0)";
	//删除旧版本，建新版本数据库
    private String updateDB="drop table if exists ifexists baseInfo";
    //主键并自增长primary key autoincrement
    @Override
	public void onCreate(SQLiteDatabase db)
	{
		// 创建表
		db.execSQL(createBaseInfoTB);
		db.execSQL(initBaseInfo);
	    db.execSQL(createOverTimeTB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{		
	}
/*-----------------------------------------------------记加班数据库设计：---------------------------------------------------*/
	//日期                                                   类型                                                    加班总时
	//varChar 20141212         int 平时1，周末2，假日3       int
    //查询日期范围加班工时
    //select hours from overTimeTb where date Between '20121212' and '20121212' and dateType=0;
    //select * from tblName where rDate Between '2008-06-10' and  '2008-06-12'   
    //计算总工资 sum+=cursor.getInt();
    
/*
 * 日期格式统一用 yyyy-mm-dd
 * 每日工时
 * 日期加班表 以年+月+日+作为主键:加班数，日期类型，
 * 基本设置表：平时加班小时工资，假日加班小时工资，周末加班小时工资
 * 判断日期类型：平时日1，假日2，周末3
 * 月加班工资计算公式：该月 平时日加班时数+假日加班时数+周末加班时数
 * 周工资计算:
 * 月总收入：基本工资+月加班工资
 * */
}
