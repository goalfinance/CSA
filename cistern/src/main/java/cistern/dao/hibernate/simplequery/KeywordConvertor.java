package cistern.dao.hibernate.simplequery;

/**
 * @project: Cistern
 * @description: �ؼ���ת����
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
