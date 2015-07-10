/*
 *@author Dawin,2014-12-24
 *
 *
 *
 */
package com.hy2014.workovertime;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
/**Activity基类
 * ![](https://github.com/kingdawin/WorkOverTime/raw/master/scrennshot.png)
 * */
public class BaseActivity extends Activity
{
	public Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context = this;
	}

	public void return0(View v)
	{
		finish();
	}
}
