/*
 * File OrderableCondition.java
 * Created on 2004-6-24
 *
 */
package cistern.dao.ql.impl;

import java.io.Serializable;

import cistern.dao.ql.annotation.Ignore;


/**
 * @description 可排序条件接口类
 * @author seabao
 * @project Cistern
 * @date 2004-6-24
 */
public class OrderableCondition implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 排序因子
	 */
	private String orders;

	public void setOrders(String orders) {
		this.orders = orders;
	}

	@Ignore
	public String getOrders() {
		return this.orders;
	}
}