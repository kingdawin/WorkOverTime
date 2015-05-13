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
	private ContentValues xxxx;
	/* handler进制的原理：

	答： android提供了handler和looper来满足线程间的通信。Handler先进先出原则。looper用来管理特定线程内对象之间的消息交换（message Exchange）.

	   1)looper:一个线程可以产生一个looper对象，由它来管理此线程里的message queue(消息队列)

	   2)handler:你可以构造一个handler对象来与looper沟通，以便push新消息到messagequeue里；或者接收looper（从messagequeue里取出）所送来的消息。

	   3)messagequeue:用来存放线程放入的消息。

	   4)线程：UI thread 通常就是main thread,而android启动程序时会为它建立一个message queue.
	   ANR
	*/
	/*参与过多媒体应用开发，传感器编程，蓝牙编程，网络编程，有独立开发音乐应用、蓝牙通信、网络通信项目的经验。
	 *熟悉Android4大组件，包括Activity的生命周期，服务的绑定和数据通信，广播的种类和注册方式，ContenProvider的使用步骤	
	 *熟悉UI控件的使用。能够根据特殊需求，自定义View
	 *了解handler机制原理，NDK开发步骤，开发遵循面向对象设计思想，注重代码的重用，有良好的代码编程规范，灵活运用单元测试、日志和DDMS调试程序，解决项目出现的bug。
	 *合理的利用论坛和文档解决新需求使用的新技术
	 *业余时间喜欢研究
	 *
	 */
	
	private Handler handler;
	private Looper looper;
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
/* 
 * HR@julanling.com 记加班HR招聘邮箱
 * UI 自定义控件 View 主要方法 onDraw onMeasure onLayout 计算 画图 定位

 * 57. 横竖屏切换时候activity的生命周期? 
 * 1、不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次 
 * 2、设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
 * 3、设置Activity的android:configChanges="orientation|keyboardHidden"时，切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法 
 * 横竖屏切换时候Activity的生命周期* 
 * 
 * MessageQueue简介：
 *这是一个包含message列表的底层类。Looper负责分发这些message。Messages并不是直接加到一个MessageQueue中，而是通过MessageQueue.IdleHandler关联到Looper。
 *你可以通过Looper.myQueue()从当前线程中获取MessageQueue。
 *
   Looper简介：
 *Looper类被用来执行一个线程中的message循环。默认情况，没有一个消息循环关联到线程。在线程中调用prepare()创建一个Looper，然后用loop()来处理messages，直到循环终止。
 * */
