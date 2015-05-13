/*
*@author Dawin,2014-12-23
*
*
*
*/
package com.hy2014.workovertime.dao;

import java.util.List;

public interface BaseDao
{
	public boolean add(String... s);

	public boolean delete(String...s);

	public boolean update();

	public List<Object> select(String... s);// 一个String类型的可变长度的数组.固定长度的数组是String[]
										// str={};这样写,可变的就String... str
	public boolean isExist(String data);
	
}
