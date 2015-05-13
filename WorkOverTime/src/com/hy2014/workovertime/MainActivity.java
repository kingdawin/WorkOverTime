package com.hy2014.workovertime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.hy2014.workovertime.dao.OverTimeDao;
import com.hy2014.workovertime.utils.LogUtil;
import com.hy2014.workovertime.view.RoundProgressBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//TODO 待增加模块：
//日期选择，选择加班时数，保存

/**
 * 主界面
 * @author KingDawin
 *
 */
@SuppressLint("SimpleDateFormat")
public class MainActivity extends BaseActivity
{
	// 今天
	public static String today;
	private TextView tv_today;

	private ViewPager mViewPager;
	// 页面切换圆点
	private ImageView img1_salary_month_total;
	private ImageView img2_salary_month_overtime;
	private ImageView img3_salary_week;
	private OverTimeDao overTimeDao;
	// 侧边菜单
	private SlideMenu slideMenu;

	private SlidingPaneLayout slidingPaneLayout;
	private int currentIndex = 0;
	// 加班总时数
	private float overtimeHours = 0.0F;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		LogUtil.v("[MainActivity] onCreate");
		setContentView(R.layout.activity_main);
		context = this;
		firstStart = true;
		init();
	}

	@Override
	protected void onResume()
	{

		// TODO 数据更新，要在resume方法
		super.onResume();
		if (firstStart == false)
			drawMoveCircle(currentIndex);
	}

	public static final int WEEK_ADD = 2;
	public static final int MONTH_ADD = 1;
	public static final int TOTAL = 0;

	// 页面切换监听器
	public class MyOnPageChangeListener implements OnPageChangeListener
	{
		@Override
		public void onPageSelected(int index)
		{

			currentIndex = index;
			LogUtil.e("当前分页索引   " + currentIndex);
			switch (index)
			{
			// 本月总收入
			case TOTAL:

				drawMoveCircle(TOTAL);// 实时画圆环
				img1_salary_month_total.setImageResource(R.drawable.page_indicator_focused);
				img2_salary_month_overtime.setImageResource(R.drawable.page_indicator);
				img3_salary_week.setImageResource(R.drawable.page_indicator);
				break;
			// 本月加班总收入
			case MONTH_ADD:
				drawMoveCircle(MONTH_ADD);
				img2_salary_month_overtime.setImageResource(R.drawable.page_indicator_focused);
				img1_salary_month_total.setImageResource(R.drawable.page_indicator);
				img3_salary_week.setImageResource(R.drawable.page_indicator);
				break;
			// 本周加班总收入
			case WEEK_ADD:
				drawMoveCircle(WEEK_ADD);
				img3_salary_week.setImageResource(R.drawable.page_indicator_focused);
				img1_salary_month_total.setImageResource(R.drawable.page_indicator);
				img2_salary_month_overtime.setImageResource(R.drawable.page_indicator);
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}
	}

	private static boolean firstStart;
	private static final String weekDay[] = new String[] { "日", "一", "二", "三", "四", "五", "六" };
	private String dateSave;

	public void setToday()
	{
		Date date = new Date();
		final SimpleDateFormat sdfSave = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
		dateSave = sdfSave.format(date);
		dateType = date.getDay() > 0 && date.getDay() < 6 ? 1 : 2;
		today = new StringBuffer().append(dateToDateStr(date, "MM月dd")).append("周").append(weekDay[date.getDay()])
				.toString();
	}

	public void init()
	{

		overTimeDao = new OverTimeDao(context);
		slideMenu = (SlideMenu) findViewById(R.id.slide_menu);

		LogUtil.w("getDateRange(int dateRangeId)=" + getDateRange(SALARY_MONTH));
		// 将要分页显示的View装入数组中，共3页
		LayoutInflater mLi = LayoutInflater.from(this);
		img1_salary_month_total = (ImageView) findViewById(R.id.page1);
		img2_salary_month_overtime = (ImageView) findViewById(R.id.page2);
		img3_salary_week = (ImageView) findViewById(R.id.page3);

		View view1 = mLi.inflate(R.layout.salary_month_total, null);
		View view2 = mLi.inflate(R.layout.salary_month_overtime, null);
		View view3 = mLi.inflate(R.layout.salary_week, null);

		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);

		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter()
		{

			@Override
			public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == arg1;
			}

			@Override
			public int getCount()
			{
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object)
			{
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position)
			{
				if (firstStart && position == 0)
				{
					drawMoveCircle(0);
					firstStart = false;
				}

				LogUtil.w("初始化ViewPager");
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		mViewPager = (ViewPager) findViewById(R.id.salary_viewpager);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mViewPager.setAdapter(mPagerAdapter);
		// 设置并显示今天日期
		setToday();
		tv_today = (TextView) findViewById(R.id.tv_today);
		tv_today.setText(getToday());

		// 显示日历，日历控件设计
		// 选择日期
		// 记所选日期的加班时间
		// 统计加班总时间 工资
		// 基本设置：平时加班，假日加班工资
		// 工资结算日
		// SQLite数据库设计 保存每日加班时间 查询

	}

	private int dateType;

	public String getToday()
	{
		return today;
	}

	/**
	 * 日期转为制定格式字符串
	 * 
	 * @param date
	 *            待转换的日期
	 * @param dateFormat
	 *            转换后的日期字符串
	 * @return
	 */
	public String dateToDateStr(Date date, String dateFormat)
	{
		String dateStr = null;
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		dateStr = sdf.format(date);
		return dateStr;
	}

	private static final int SALARY_MONTH = 1;
	private static final int SALARY_WEEK = 2;
	// 1 2 3 4 5 6 7 8 9 10 11 12
	/**
	 * 每个月的日总数
	 */
	private int monthTotalDays[] = new int[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * 判断是否是闰年
	 * 
	 * @param year
	 *            年份
	 * @return true是闰年，false不是闰年
	 */
	public boolean isLeapYear(int year)
	{
		return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0) ? true : false;

	}

	private String startDate;
	private String endDate;
	private String dateRange;

	/**
	 * @return 日期段
	 */
	public String getDateRange(int dateRangeId)
	{

		dateRange = "12月15-12月21";
		Date date = new Date();
		// 闰年二月：28 29
		if (isLeapYear(date.getYear() + 1900))
		{
			monthTotalDays[1] = 28;
		}
		switch (dateRangeId)
		{
		// 本月
		// TODO:(用户设置的月结工资日)
		case SALARY_MONTH:
		case 0:
			int year = date.getYear() + 1900;
			String month = oneBitadd0(date.getMonth() + 1);
			startDate = year + month + "01";
			endDate = year + month + monthTotalDays[date.getMonth()];

			LogUtil.e("[getDateRange]  startDate=" + startDate);
			LogUtil.e("[getDateRange]  endDate=" + endDate);

			dateRange = month + "月1-" + month + "月" + monthTotalDays[date.getMonth()];
			break;
		// TODO 本周
		case SALARY_WEEK:
			dateRange = getCurrentWeek();
			break;
		}
		return dateRange;
	}

	/**
	 * 月份是一位数的前面加0
	 */
	public String oneBitadd0(int month)
	{
		return month < 10 ? "0" + month : month + "";
	}

	/**
	 * 当周日期
	 * 
	 * @return
	 */
	public String getCurrentWeek()
	{
		StringBuffer stringBuffer = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MM月dd");
		SimpleDateFormat dfSql = new SimpleDateFormat("yyyyMMdd");
		// 获取本周一的日期
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		stringBuffer.append(df.format(cal.getTime()));
		startDate = dfSql.format(cal.getTime());
		// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		stringBuffer.append("-").append(df.format(cal.getTime()));
		endDate = dfSql.format(cal.getTime());
		return stringBuffer.toString();
	}

	/**
	 * @return 总工时
	 */
	public float getWorkHours()
	{
		// TODO 月工时 周工时
		return 0.00F;
	}

	// 动态画圆
	private RoundProgressBar mRoundProgressBar;
	private int progress = 0;
	// 总进度
	private static final int MAX = 100;
	// 每段长度n
	private static final int N = 1;
	// 绘制一段圆环的时间ms
	private static final int TIME = 10;
	float salaryTv;

	private static final boolean OVER_TIME_SALARY = true;
	private static final boolean NOT_OVERTIME_SALARY = false;

	/**
	 * 实时画圆环
	 * 
	 * @param roundId
	 *            0：本月总收入圆环及相应数据 1：本月加班总收入圆环及相应数据 2：本周加班总收入圆环及相应数据
	 */
	public void drawMoveCircle(final int roundId)
	{

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				LogUtil.w("startDate=" + startDate + " endDate=" + endDate);
				switch (roundId)
				{
				// 本月总收入
				case TOTAL:
					getDateRange(roundId);
					mRoundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar_salary_month_total);
					salaryTv = overTimeDao.calOvertimeSalary(Integer.parseInt(startDate), Integer.parseInt(endDate),
							NOT_OVERTIME_SALARY);
					break;
				// 本月加班总收入
				case MONTH_ADD:
					getDateRange(roundId);
					mRoundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar_salary_month_overtime);
					// 开始结束日期
					salaryTv = overTimeDao.calOvertimeSalary(Integer.parseInt(startDate), Integer.parseInt(endDate),
							OVER_TIME_SALARY);
					break;
				case WEEK_ADD:
					getDateRange(roundId);
					mRoundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar_salary_week);

					salaryTv = overTimeDao.calOvertimeSalary(Integer.parseInt(startDate), Integer.parseInt(endDate),
							OVER_TIME_SALARY);
					break;
				}
				progress = 0;
				// Create:20141226
				if (mRoundProgressBar == null)
					return;

				mRoundProgressBar.setProgress(progress);
				// 设置时间段
				mRoundProgressBar.setDateRange(dateRange);
				// 设置工资
				mRoundProgressBar.setSalary(String.valueOf(salaryTv));
				// 通过线程控制绘制速度
				while (progress <= MAX)
				{
					progress += N;
					mRoundProgressBar.setProgress(progress);

					try
					{
						Thread.sleep(TIME);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	/**
	 * 点击事件
	 * @param v
	 */
	public void clickView(View v)
	{
		Intent intent;
		switch (v.getId())
		{
		// 记加班
		case R.id.btn_record_overtime:
			intent = new Intent(MainActivity.this, SelectDateActivity.class);
			intent.putExtra("date", today);// 显示给用户
			intent.putExtra("dateSave", dateSave);// 保存到数据库
			intent.putExtra("dateType", dateType);
			startActivity(intent);
			break;
		case R.id.btn_work_no:
			break;
		case R.id.btn_left_menu:
			LogUtil.e("clickView btn_left_menu ");
			if (slideMenu.isMainScreenShowing())
			{
				slideMenu.openMenu();
			} else
			{
				slideMenu.closeMenu();
			}
			break;
		// 我的工资条
		case R.id.tv_my_salary:
			Toast.makeText(context, "我的工资条", Toast.LENGTH_LONG).show();
			break;
		// 月工资预估
		case R.id.tv_salary_month_forecast:
			Toast.makeText(context, "月工资预估", Toast.LENGTH_LONG).show();
			break;
		// 日历
		case R.id.btn_calendar:
			intent = new Intent(MainActivity.this, CalendarActivity.class);
			startActivity(intent);
			break;
		// 统计
		case R.id.btn_summary:
			// Toast.makeText(context, "统计", Toast.LENGTH_LONG).show();
			intent = new Intent(MainActivity.this, SummaryActivity.class);
			startActivity(intent);
			break;
		// 设置
		case R.id.btn_setup:
			intent = new Intent(MainActivity.this, SetupActivity.class);
			startActivity(intent);
			break;
		}

	}

	/**
	 * @return the slidingPaneLayout
	 */
	public SlidingPaneLayout getSlidingPaneLayout()
	{
		return slidingPaneLayout;
	}
}