# WorkOverTime
record work overtime 记加班安卓App

依赖库:
日历控件https://github.com/square/android-times-square

功能：

1 实时画圆环控件RoundProgressBar，继承view重写onDraw方法：外圆空心，内圆实心，圆环通过线程控制速度。
控件属性的自定义和使用：values文件夹下创建attrs文件

2获取当天日期，setToday()

3获取本周开始和结束日期

4圆角按钮：样式文件在drawalbe/button_shape.xml

5ViewPager的使用:装填了3个圆环控件，每次切换都会重绘当前的圆环，注册页面切换监听器OnPageChangeListener，实现页面下标的改变

6底部导航栏界面的设计：由3个button构成，权重相等，利用android:drawableTop属性设置图片在按钮顶部

7滑动切换侧边栏菜单
头像以圆形显示

8自定义View显示圆形头像CustomImageView

9统计模块：查询日期的加班时数。查询加班表，显示加班时数不为0的记录

10日历模块：选择日历的日期记加班

11设置模块:设置基本工资，平时工资，周末工资，假日工资。刷新界面数据

