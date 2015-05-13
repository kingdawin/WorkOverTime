package com.hy2014.workovertime.view;

import com.hy2014.workovertime.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;   
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View; 

/**
 *带进度的圆环进度条，线程安全的View，可直接在线程中更新进度
 * 
 * @author Dawin
 * 
 */
public class RoundProgressBar extends View
{
	private Context context;

	/**
	 * 画笔对象的引用
	 */
	private Paint paint;

	/**
	 * 圆环的颜色
	 */
	private int roundColor;

	/**
	 * 圆环进度的颜色
	 */
	private int roundProgressColor;

	/**
	 * 中间进度百分比的字符串的颜色
	 */
	private int textColor;

	/**
	 * 中间进度百分比的字符串的字体
	 */
	private float textSize;

	/**
	 * 圆环的宽度px
	 */
	private float roundWidth;
     /**圆环的宽度dip*/ 
	private float roundWidthDip;
	/**
	 * 最大进度
	 */
	private int max;

	/**
	 * 当前进度
	 */
	private int progress;

	/**
	 * 进度的风格，实心或者空心
	 */
	private int style;
	public static final int STROKE = 0;
	public static final int FILL = 1;

	/**
	 * 工资
	 */
	private String salary = "0.0";
	private int salaryTextColor;
	/**日期段*/
	private String dateRange="12月1-12月31";	
	
	public RoundProgressBar(Context context) {
		this(context, null);
		this.context = context;
	}

	public RoundProgressBar(Context context, AttributeSet attrs) {

		this(context, attrs, 0);
		this.context = context;
	}

	public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		paint = new Paint();
		paint.setAntiAlias(true); // 消除锯齿
		//TypedArray是一个用来存放由context.obtainStyledAttributes获得的属性的数组   
        //在使用完成后，一定要调用recycle方法   
        //属性的名称是styleable中的名称+“_”+属性名称   
		// 获取自定义属性和默认值
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);		
		roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
		roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
		salaryTextColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.BLACK);
		Log.i("TEST", Integer.toString(textColor));
		textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize0, 5);
		roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);//px值
		roundWidthDip = px2dip(context, roundWidth);//dp值
		max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
		//圆环样式：实心或空心
		style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
		mTypedArray.recycle();
	}	

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		int centerX = getWidth() >> 1; // 圆心X值
		int centerY = getHeight() / 2 - 20;// 圆心Y值.向上移动45px,纠偏
		int outRadius = centerY - 20; // 外圆半径：20指顶部文字所占的px高

		/**
		 * 画最外层的大圆环
		 */
		// int centre = getWidth()/2; //获取圆心的x坐标
		// int radius = (int) (centre - roundWidth/2); //圆环的半径
		paint.setColor(roundColor); // 设置圆环的颜色
		paint.setStyle(Paint.Style.STROKE); // 设置空心
		paint.setStrokeWidth(roundWidth); // 设置圆环的宽度
		
		canvas.drawCircle(centerX, centerY, outRadius/* radius */, paint); // 画出圆环

		/**
		 * 绘制内圆
		 * */
		paint.setColor(0xffffffff);
		this.paint.setStrokeWidth(0);
		this.paint.setStyle(Paint.Style.FILL); // 绘制实心圆
		canvas.drawCircle(centerX, centerY, outRadius - roundWidthDip, this.paint);

		/**
		 * 绘制工资
		 */
		paint.setStrokeWidth(0);
		paint.setColor(salaryTextColor);
		paint.setTextSize(50);
		paint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体
		float textWidth = paint.measureText(salary + "元"); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
		canvas.drawText(salary + "元", centerX - textWidth / 2, centerY + textSize / 2, paint);

		/**
		 * 绘制工时
		 */
		paint.setStrokeWidth(0);
		paint.setColor(0xff000000);
		paint.setTextSize(35);
		//paint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体
		textWidth = paint.measureText("100.0小时"); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
		canvas.drawText("100.0小时", centerX - textWidth / 2, centerY + textSize / 2 + 50, paint);

		/**
		 * 绘制日期段
		 * */
		paint.setColor(0xff000000);
		paint.setTypeface(null);
		paint.setTextSize(40);
		// 日期段坐标
		int dateRangexPos = centerX - (int) paint.measureText(dateRange) / 2;
		int dateRangeyPos = centerY + getFontHeight(paint.getTextSize()) / 2 - 95;
		canvas.drawText(dateRange, dateRangexPos, dateRangeyPos, this.paint);

		/**
		 * 绘制圆弧 
		 */

		// 设置进度是实心还是空心
		paint.setStrokeWidth(roundWidth); // 设置圆环的宽度
		paint.setColor(roundProgressColor); // 设置进度的颜色
		// 用于定义的圆弧的形状和大小的界限
		// 左上角坐标(centerX-innerCircle, centerY-innerCircle)
		// 右下角坐标(centerX+innerCircle , centerY+innerCircle)
		RectF oval = new RectF(centerX - outRadius, centerY - outRadius, centerX + outRadius, centerY + outRadius);

		switch (style)
		{
		case STROKE:
		{
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval, 0, 360 * progress / max, false, paint); // 根据进度画圆弧
			break;
		}
		case FILL:
		{
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			if (progress != 0)
				canvas.drawArc(oval, 0, 360 * progress / max, true, paint); // 根据进度画圆弧
			break;
		}
		}

	}
	 public int getFontHeight(float fontSize)  
	    {  
	        Paint paint = new Paint();  
	        paint.setTextSize(fontSize);  
	        FontMetrics fm = paint.getFontMetrics();  
	        return (int) Math.ceil(fm.descent - fm.top) + 2;  
	    } 
	public synchronized int getMax()
	{
		return max;
	}

	/**
	 * 设置进度的最大值
	 * 
	 * @param max
	 */
	public synchronized void setMax(int max)
	{
		if (max < 0)
			{
				throw new IllegalArgumentException("max not less than 0");
			}
		this.max = max;
	}

	/**
	 * 获取进度.需要同步
	 * 
	 * @return
	 */
	public synchronized int getProgress()
	{
		return progress;
	}

	/**
	 * 设置进度，此为线程安全控件，由于考虑多线程的问题，需要同步 刷新界面调用postInvalidate()能在非UI线程刷新
	 * 
	 * @param progress
	 */
	public synchronized void setProgress(int progress)
	{
		if (progress < 0)
			{
				throw new IllegalArgumentException("progress not less than 0");
			}
		if (progress > max)
			{
				progress = max;
			}
		if (progress <= max)
			{
				this.progress = progress;
				//使用多线程方式重绘View
				postInvalidate();
				//invalidate();
			}
	}
	//android view的刷新
	public synchronized String getSalary()
	{
		return salary;
	}

	public synchronized void setSalary(String salary)
	{
		this.salary = salary;
	}

	public int getCricleColor()
	{
		return roundColor;
	}

	public void setCricleColor(int cricleColor)
	{
		this.roundColor = cricleColor;
	}

	public int getCricleProgressColor()
	{
		return roundProgressColor;
	}

	public void setCricleProgressColor(int cricleProgressColor)
	{
		this.roundProgressColor = cricleProgressColor;
	}

	public int getTextColor()
	{
		return textColor;
	}

	public void setTextColor(int textColor)
	{
		this.textColor = textColor;
	}

	public float getTextSize()
	{
		return textSize;
	}

	public void setTextSize(float textSize)
	{
		this.textSize = textSize;
	}

	public float getRoundWidth()
	{
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth)
	{
		this.roundWidth = roundWidth;
	}
	public String getDateRange()
	{
		return dateRange;
	}

	public void setDateRange(String dateRange)
	{
		this.dateRange = dateRange;
	}
}
