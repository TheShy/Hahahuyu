package com.touyuanren.hahahuyu.utils;

import com.touyuanren.hahahuyu.bean.DateText;

import java.util.Comparator;

public class DateComparator implements Comparator<DateText> {
	/**
	 * 如果lhs小于rhs,返回一个负数;如果o1大于o2，返回一个正数;如果他们相等，则返回0;
	 */
	@Override
	public int compare(DateText lhs, DateText rhs) {
		return rhs.getDate().compareTo(lhs.getDate());
	}

}
