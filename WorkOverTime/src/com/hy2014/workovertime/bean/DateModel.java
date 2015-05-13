/*
 *@author Dawin,2014-12-24
 *
 *
 *
 */
package com.hy2014.workovertime.bean;

import java.io.Serializable;

public class DateModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 年
	private String year;
	// 月
	private String month;
	// 日
	private String day;
	public String getYear()
	{
		return year;
	}
	public void setYear(String year)
	{
		this.year = year;
	}
	public String getMonth()
	{
		return month;
	}
	public void setMonth(String month)
	{
		this.month = month;
	}
	public String getDay()
	{
		return day;
	}
	public void setDay(String day)
	{
		this.day = day;
	}
}
