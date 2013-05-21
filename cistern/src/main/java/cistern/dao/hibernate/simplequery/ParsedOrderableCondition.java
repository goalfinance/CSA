/*
 * File MultiColumnsOrderableCondition.java
 * Created on 2004-6-25
 *
 */
package cistern.dao.hibernate.simplequery;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @description 多列可排序条件抽象类
 * @author seabao
 * @project Cistern
 * @date 2004-6-25
 */
public class ParsedOrderableCondition {
	private List<OrderItem> orderProperties = new LinkedList<OrderItem>();

	public ParsedOrderableCondition(String orders) {
		if ( orders == null ) {
			return ;
		}
		
		StringTokenizer st = new StringTokenizer(orders, ",");
		while (st.hasMoreTokens()) {
			String str = st.nextToken().trim();

			OrderItem orderItem = new OrderItem();

			if (str.endsWith("-")) {
				orderItem.setOrderDesc(true);
				str = str.substring(0, str.length() - 1);
			} else if (str.endsWith("+")) {
				str = str.substring(0, str.length() - 1);
			}

			orderItem.setOrderByPropName(str);

			orderProperty(orderItem);
		}
	}

	private void orderProperty(OrderItem orderItem) {
		orderProperties.add(orderItem);
	}

	public String genOrderClause(String defaultPersistenceAlias) {
		if (needOrder() == false) {
			return null;
		}

		StringBuffer clause = new StringBuffer();
		for (Iterator<OrderItem> it = orderProperties.iterator(); it.hasNext();) {
			OrderItem orderItem = (OrderItem) it.next();
			if (clause.length() > 0) {
				clause.append(", ");
			}

			if (orderItem.getOrderByPropName().indexOf(".") < 0) {
				clause.append(defaultPersistenceAlias);
				clause.append(".");
				clause.append(orderItem.getOrderByPropName());
			} else {
				String c = orderItem.getOrderByPropName();
				MacroVariableContainer mvc = new MapMacroVariableContainer();
				mvc.setMacroVariable("persistence-alias", defaultPersistenceAlias);
				try {
					clause.append(MacroVariableInterpreter.interpret(c, "${", "}", mvc));
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}

			if (orderItem.isOrderDesc()) {
				clause.append(" desc");
			}
		}

		return clause.toString();
	}

	public String genSelectClause(String defaultPersistenceAlias) {
		if (needOrder() == false) {
			return null;
		}

		StringBuffer clause = new StringBuffer();
		for (Iterator<OrderItem> it = orderProperties.iterator(); it.hasNext();) {
			OrderItem orderItem = (OrderItem) it.next();
			if (clause.length() > 0) {
				clause.append(", ");
			}

			if (orderItem.getOrderByPropName().indexOf(".") < 0) {
				clause.append(defaultPersistenceAlias);
				clause.append(".");
				clause.append(orderItem.getOrderByPropName());
			} else {
				String c = orderItem.getOrderByPropName();
				MacroVariableContainer mvc = new MapMacroVariableContainer();
				mvc.setMacroVariable("persistence-alias", defaultPersistenceAlias);
				try {
					clause.append(MacroVariableInterpreter.interpret(c, "${", "}", mvc));
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}

		return clause.toString();
	}

	public boolean needOrder() {
		if (orderProperties.size() == 0) {
			return false;
		}

		return true;
	}

	public Iterator<OrderItem> iterateOrderItem() {
		return orderProperties.iterator();
	}
}