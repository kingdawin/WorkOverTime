/*
 *@author Dawin,2014-12-30
 *
 *
 *
 */
package com.hy2014.workovertime.dao;

import android.test.AndroidTestCase;

/**
 * 所有面试题目的测试
 * @author RD47
 *
 */
public class FaceExame extends AndroidTestCase
{
	//1 字符数组以字符串形式输出
	char c1[] = { 'a', 'b', 'c' };

	public void testString()
	{
		FaceExame faceExame = new FaceExame();
		System.out.println(faceExame.c1);
	}
	//2NDK开发步骤
	
	/*
	 * 1下载安装Cygwin
	 * 2下载NDK
	 * 3在NDK项目，进行JNI接口设计
	 * 4c/c++实现本地方法
	 * 5生成动态链接库.so文件
	 * 6将文件放到java项目，放到libs\armeabi
	 * 7运行java项目
	 * */
	
	public void testRoundFloor()
	{
		//4舍五入，但负数的5是舍去的。
		Math.round(2.5);//3
		Math.round(-2.5);//2
		//负数入，正数舍去
		Math.floor(2.3);//2
		Math.floor(-2.3);//-3
	}
	
	//3android7个生命周期
	/*
	 * onCreate onStart onRestart onResume onPause onStop onDestroy
	 *
	 * 4Android系统层次结构：应用程序层-框架层-C/C++库-linux内核
	 * 
	 * 5sqlite创建数据库，更新数据库
	 * public class DBHelper extends SQLiteOpenHelper
	 * {
	 *   static final DB_NAME="xx.db";
	 *   public DBHelper(Contexnt context,String dbName)
	 *   {
	 *   super.();
	 *   }
	 * }
	 * */
	
}
