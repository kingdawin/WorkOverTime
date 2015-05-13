/*
 *@author Dawin,2014-12-18
 *
 *����״ͼ����ʾ���ʱ���
 *
 */
package com.hy2014.workovertime.view;

import com.hy2014.workovertime.R;
import com.hy2014.workovertime.utils.LogUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;

public class Circle extends View
{
	private final Paint paint;
	private final Context context;      
	//圆环颜色
	private int circleColor;
	//工资
	private String salary="10000.00";
	//日期段
	private String dateRange="12月1-12月31";	

	public String getDateRange()
	{
		return dateRange;
	}

	// Measure the view and its content to determine the measured width and the
	// measured height.
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	// Called from layout when this view should assign a size and position to
	// each of its children.
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}
	public void setDateRange(String dateRange)
	{
		this.dateRange = dateRange;
	}


	private final int DEFAULT_COLOR=0x324d86cd;
    public Circle(Context context) {  
        this(context, null);  
    }  
   
    public Circle(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        this.context = context; 
        //TypedArray是一个用来存放由context.obtainStyledAttributes获得的属性的数组   
        //在使用完成后，一定要调用recycle方法   
        //属性的名称是styleable中的名称+“_”+属性名称   
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyView);   
         circleColor = array.getColor(R.styleable.MyView_circleColor,DEFAULT_COLOR ); //提供默认值，放置未指定  
        
        this.paint = new Paint();  
        this.paint.setAntiAlias(true); //消除锯齿  
       // this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆   
    }  
    
    @Override  
    protected void onDraw(Canvas canvas) {   
    	LogUtil.w("画圆");
        int centerX = getWidth()>>1; 
        int centerY= getHeight()/2-45;//Y轴向上移动45px,纠偏
        int innerCircle =centerY-20/* dip2px(context, 79)*/; //设置内圆半径  
        int ringWidth = dip2px(context, 8); //设置圆环宽度         
        
        //绘制内圆    
        paint.setColor(0xffffffff);
        this.paint.setStrokeWidth(0);  
        this.paint.setStyle(Paint.Style.FILL); //绘制实心圆   
        canvas.drawCircle(centerX,centerY, innerCircle, this.paint);  
         
        //绘制圆环   
        paint.setColor(circleColor);
        this.paint.setStrokeWidth(ringWidth);  
        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆  
        canvas.drawCircle(centerX,centerY, innerCircle+1+ringWidth/2, this.paint);  
          
        //绘制外圆   
        paint.setColor(circleColor);
        this.paint.setStrokeWidth(0);  
        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆  
        canvas.drawCircle(centerX,centerY, innerCircle+ringWidth, this.paint);  
  
        //绘制工资
        paint.setColor(0xFFf30d0d);
        this.paint.setStrokeWidth(20);  
        this.paint.setStyle(Paint.Style.FILL); //绘制实心 
        paint.setTextSize(50);        
        //金额坐标
  		int xPos = centerX - (int)paint.measureText(salary) / 2;
  		int yPos=centerY+getFontHeight(paint.getTextSize())/2;
        canvas.drawText(salary,xPos, /*centerY*/yPos, this.paint); 
        
        //绘制日期段
        paint.setColor(0xFF000000);
        this.paint.setStrokeWidth(15);  
        this.paint.setStyle(Paint.Style.FILL); //绘制实心 
        paint.setTextSize(20);
        //日期段坐标
  	    int dateRangexPos = centerX - (int)paint.measureText(dateRange) / 2;  	  
  		int dateRangeyPos=centerY+getFontHeight(paint.getTextSize())/2-60;  	 
        canvas.drawText(dateRange,dateRangexPos, dateRangeyPos, this.paint);  
        
        super.onDraw(canvas);  
    }  
      
    public int getFontHeight(float fontSize)  
    {  
        Paint paint = new Paint();  
        paint.setTextSize(fontSize);  
        FontMetrics fm = paint.getFontMetrics();  
        return (int) Math.ceil(fm.descent - fm.top) + 2;  
    }  
    
      
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  

	public String getSalary()
	{
		return salary;
	}

	public void setSalary(String salary)
	{
		this.salary = salary;
	}
	//12月/年
	/*
	完整的社保包括五险一金,即养老,医疗,工伤,生育,失业保险和住房公积金.

	以单位买全社保为准，计算如下所示： 
	医疗保险：个人承担2%，单位承担8%； 
	养老保险：个人为8%，单位承担12%； 
	失业保险：个人为1%，单位承担2%； 
	工伤保险:个人无，单位1%； 
	生育保险:个人无，单位1%； 
	公积金：个人3.5%,单位3.5%。 
	而工伤和生育保险的费用均由单位承担。 

	以工资为6000元/每月进行计算，那么其需要交纳的保费为： 
	医疗保险：6000*2%=120元，单位6000*8%=480元； 
	养老保险：6000*8%=480元，单位6000*12%=720元；
	失业保险：6000*1%=60元，单位6000*2%=120元； 
	工伤保险：个人无，单位6000*1%=60元 
	生育保险：个人无，单位6000*1%=60元 
	公积金：6000*3.5%=210元，6000*3.5%=210元。 
	因此个人总计为：870元，单位总计为：1650元。 
	计算的数据可能会与实际的有出入，但计算的方式是这样的.
	*/
	//8000*0.145 1165+600
    //5000工资
    //	14.5-3.5(550)   27.5(1375)
}
