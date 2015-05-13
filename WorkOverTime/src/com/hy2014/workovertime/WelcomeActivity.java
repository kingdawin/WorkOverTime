package com.hy2014.workovertime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcome();
    }
    
    public void welcome()
    {
        Thread thread=new Thread()
        {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {              
                    e.printStackTrace();
                }               
            }
        };
        thread.start();
    }
}
/*//one day two hundred
 * force close app
 * 基础学差不多之后，可以自己做些项目。
项目选择有几种方法：
1. 做在线音乐播放器，课参照Jamendo，网上有分析的文章。
   好处：涉及到的Android开发知识较多，是一个很好的练手的项目
2. 如果有目标公司或目标行业，可以做一个类似他们产品的客户端。
   好处：如果有机会去了这样的公司，面试问的技术你可以回答得比较好。
3. 选择有视频或分析文档的项目去开发
   好处：项目开发中比较耗时的就是不知道某个功能怎么做，或者遇到问题难以解决。如果有参考，你可以节省很多时间。
 * 
 * 安卓系统结构：
 * 应用层
 * 框架层
 * c/c++库
 * linux内核
 * */
