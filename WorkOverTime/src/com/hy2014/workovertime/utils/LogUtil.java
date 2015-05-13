package com.hy2014.workovertime.utils;

import android.util.Log;

/**
 * 日志工具类
 * 
 * @author Dawin 
 * DEBUG:控制日志是否打印
 */
public class LogUtil
{
	// true:打印日志，false:关闭日志
	private static final boolean DEBUG = true;
	private static final String TAG = "WorkOverTime";

	public static void v(String msg)
	{
		if (DEBUG)
			Log.v(TAG, msg);
	}

	public static void w(String msg)
	{
		if (DEBUG)
			Log.w(TAG, msg);
	}

	public static void e(String msg)
	{
		if (DEBUG)
			Log.e(TAG, msg);

	}

	public static void v(String tag, String msg)
	{
		if (DEBUG)
			Log.v(TAG, msg);
	}

	public static void i(String msg)
	{
		if (DEBUG)
			Log.i(TAG, msg);
	}

	public static void d(String msg)
	{
		if (DEBUG)
			Log.d(TAG, msg);

	}

}
