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

	// 使进度条10秒内完成进度
	ProgressBar progressBar;
	int currentProgress = 0;

	public void progress()
	{

		Thread thread = new Thread()
		{
			public void run()
			{
				while (currentProgress < progressBar.getMax())
				{
					currentProgress += progressBar.getMax() / 10;
					progressBar.setProgress(currentProgress);
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			};

		};
		thread.start();

	}
}
