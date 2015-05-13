/**   
 * Copyright © 2014 All rights reserved.
 * 
 * @Title: SlidingPaneMenuFragment.java 
 * @Prject: SlidingPane
 * @Package: com.example.slidingpane 
 * @Description: TODO
 * @author: raot  719055805@qq.com
 * @date: 2014年9月5日 上午10:42:07 
 * @version: V1.0   
 */
package com.hy2014.workovertime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * @ClassName: SlidingPaneMenuFragment
 * @Description: TODO
 * @author: raot 719055805@qq.com
 * @date: 2014年9月5日 上午10:42:07
 */
public class MenuFragment extends Fragment implements View.OnClickListener
{

	private View currentView;
	private Button bt1, bt2, bt3, bt4, bt5, bt6;

	public View getCurrentView()
	{
		return currentView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		currentView = inflater.inflate(R.layout.layout_menu, container, false);
		return currentView;
	}

	@Override
	public void onClick(View v)
	{
		((MainActivity) getActivity()).getSlidingPaneLayout().closePane();
	}
}
