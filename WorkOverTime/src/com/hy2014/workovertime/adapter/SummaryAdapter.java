/*
 *@author Dawin,2014-12-25
 *
 *
 *
 */
package com.hy2014.workovertime.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.hy2014.workovertime.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SummaryAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<HashMap<String, Object>> overtimeDatas;

	public SummaryAdapter(Context context, ArrayList<HashMap<String, Object>> overtimeDatas)
	{
		this.context = context;
		this.overtimeDatas = overtimeDatas;
	}

	@Override
	public int getCount()
	{
		return overtimeDatas.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.item_summary, null);
			viewHolder = new ViewHolder();
			viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
			viewHolder.tvOvertimeHous = (TextView) convertView.findViewById(R.id.tv_overtime_hours);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvDate.setText("" + overtimeDatas.get(position).get("date"));
		viewHolder.tvOvertimeHous.setText("" + overtimeDatas.get(position).get("hours"));
		return convertView;
	}

	final class ViewHolder
	{
		TextView tvOvertimeHous;
		TextView tvDate;
	}
}
