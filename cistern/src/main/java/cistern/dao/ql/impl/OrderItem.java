/*
 * File OrderItem.java
 * Created on 2004-6-25
 *
 */
package cistern.dao.ql.impl;

/**
 * @description OrderItem类 
 * @author seabao
 * @project Cistern
 * @date 2004-6-25
 */
public class OrderItem {
	/**
	 * 排序属性名字
	 */
	private String orderByPropName;

	/**
	 * 倒序排序标志
	 */
	private boolean orderDesc = false;

	public OrderItem() {	
	}
	
	public OrderItem(String orderByPropName, boolean orderDesc) {
		this.orderByPropName = orderByPropName;
		this.orderDesc = orderDesc;
	}
	
	public static OrderItem asc(String propertyName) {
		return new OrderItem(propertyName, false);
	}
	
	public static OrderItem desc(String propertyName) {
		return new OrderItem(propertyName, true);
	}
	
	public String getOrderByPropName() {
		return orderByPropName;
	}

	public boolean isOrderDesc() {
		return orderDesc;
	}

	public void setOrderByPropName(String string) {
		orderByPropName = string;
	}

	public void setOrderDesc(boolean b) {
		orderDesc = b;
	}

}