/*
 *@author Dawin,2014-12-24
 *
 *
 *
 */
package com.hy2014.workovertime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author KingDawin
 *git@github.com:kingdawin/WorkOverTime.git
 *
 *(http://github.com/kingdawin/WorkOverTime/raw/master/images-folder/xxx.png)
 *
 */
public class CalendarActivity extends BaseActivity
{
	private CalendarPickerView calendar;
	private String selectDate;
	// 平时日期
	private final int NORMAL_DATE = 1;
	// 周末日期
	private final int WEEK_DATE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		// 下一年
		final Calendar minDate = Calendar.getInstance();
		// 上一年
		final Calendar maxDate = Calendar.getInstance();
		minDate.add(Calendar.MONTH, -1);
		maxDate.add(Calendar.MONTH, 1);
		calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
		// 初始化日历范围
		calendar.init(minDate.getTime(), maxDate.getTime())
		// 单选日历
				.inMode(SelectionMode.SINGLE)
				// 默认选择今天
				.withSelectedDate(new Date());

		calendar.setOnDateSelectedListener(new OnDateSelectedListener()
		{
			// 取消日期
			@Override
			public void onDateUnselected(Date date)
			{
				System.out.println("onDateUnselected");
			}

			// 选择日期
			@Override
			public void onDateSelected(Date date)
			{
				System.out.println("onDateSelected");
				// 保存到数据库的格式
				final SimpleDateFormat sdfSave = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
				// 显示给用户的格式
				final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				String selectDateSave = sdfSave.format(calendar.getSelectedDate());
				// LogUtil.i("选择了日期---->" + selectDateSave);
				selectDate = sdf.format(calendar.getSelectedDate());
				// LogUtil.i("选择了日期---->" + selectDate);
				Intent intent = new Intent(CalendarActivity.this, SelectDateActivity.class);
				intent.putExtra("date", selectDate);
				intent.putExtra("dateSave", selectDateSave);
				try
				{
					intent.putExtra("dateType", dateType());
				} catch (ParseException e)
				{
					e.printStackTrace();
				}
				startActivity(intent);
			}
		});
	}

	public int dateType() throws ParseException
	{
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date date = sdf.parse(selectDate);
		// getDay一周的第几天。[周日,周六][0,6]
		return date.getDay() > 0 && date.getDay() < 6 ? NORMAL_DATE : WEEK_DATE;
	}

}
