package com.ushaqi.zhuishushenqi.event;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * 事件总线
 * 
 * @author Shaojie
 * @Date 2013-9-25 下午7:46:27
 */
public class BusProvider {
	
	private static final Bus BUS = new Bus(ThreadEnforcer.ANY);

	public static Bus getInstance() {
		return BUS;
	}
}
