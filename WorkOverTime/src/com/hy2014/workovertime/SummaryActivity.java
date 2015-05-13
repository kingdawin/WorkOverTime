/*
 *@author Dawin,2014-12-25
 *
 *
 *
 */
package com.hy2014.workovertime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hy2014.workovertime.adapter.SummaryAdapter;
import com.hy2014.workovertime.dao.OverTimeDao;
import com.hy2014.workovertime.utils.LogUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

/**
 * 统计
 * 
 * @author Dawin
 * 
 */
public class SummaryActivity extends BaseActivity
{
	private OverTimeDao overTimeDao;
	private ArrayList<HashMap<String, Object>>  overtimeDatas;
	private SummaryAdapter summaryAdapter;
	private ListView lvSummaryData;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//savedInstanceState Bundle contains the data it most recently supplied in onSaveInstanceState.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		overTimeDao=new OverTimeDao(context);	
		overtimeDatas = new ArrayList<HashMap<String,Object>>();
		overtimeDatas = overTimeDao.selectOvertimeDatas();
		summaryAdapter=new SummaryAdapter(context, overtimeDatas);
		
		lvSummaryData=(ListView)findViewById(R.id.lvSummaryData);
		lvSummaryData.setAdapter(summaryAdapter);
	}
	//保存数据
	@Override  
	protected void onSaveInstanceState(Bundle outState) {    
	    Log.v("WorkOverTime", "onSaveInstanceState");  
	    super.onSaveInstanceState(outState);  
	}  
	
}
