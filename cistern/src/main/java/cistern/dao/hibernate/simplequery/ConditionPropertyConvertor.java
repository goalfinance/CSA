/*
 * File ConditionPropertyConvertor.java
 * Created on 2004-7-13
 */
package cistern.dao.hibernate.simplequery;

/**
 * ��ѯ��������ת�����ӿڶ�����
 * @author seabao
 * @project Cistern
 * @date 2004-7-13
 */
public interface ConditionPropertyConvertor {
    /**
     * װ����ѯ��������
     */
    public Object convert(Object conditionProperty);
}
