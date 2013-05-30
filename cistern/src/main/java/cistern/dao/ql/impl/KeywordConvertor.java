package cistern.dao.ql.impl;

/**
 * @project: Cistern
 * @description: ¹Ø¼ü×Ö×ª»»Æ÷
 * @author: seabao
 * @create_time: 2007-3-3
 * @modify_time: 2007-3-3
 */
public class KeywordConvertor implements ConditionPropertyConvertor {

	public Object convert(Object conditionProperty) {
		StringBuffer str = new StringBuffer();
		str.append("%");
		str.append((String )conditionProperty);
		str.append("%");
	
		return str.toString();
	}

}
