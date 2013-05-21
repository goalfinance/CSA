/*
 * File SimpleQueryFactory.java
 * Created on 2004-7-13
 */
package cistern.dao.hibernate.simplequery;

import cistern.dao.hibernate.HQLCondition;
import cistern.dao.hibernate.HQLQuery;


/**
 * SimpleQuery������
 * @author seabao
 * @project Cistern
 * @date 2004-7-13
 */
public interface SimpleQueryFactory {
    /**
     * ͨ����ѯ������������һ��HQLQuery����
     * @param condition
     * @return
     */
    public HQLQuery genHQLQuery(Object condition);
    
    /**
     * ͨ����ѯ������������һ��HQLQuery����
     */
    @SuppressWarnings("unchecked")
    public HQLQuery genHQLQuery(Class clazz, Object condition);
    
    /**
     * ����HQL��������
     * @param condition
     * @return
     */
    @SuppressWarnings("unchecked")
    public HQLCondition genHQLCondition(Class clazz, Object condition);
    
    /**
     * ����HQL��������
     * @param condition
     * @return
     */
    public HQLCondition genHQLCondition(Object condition);
    
    /**
     * ��ò�ѯ���Ӧ������Ԫ����
     */
    @SuppressWarnings("unchecked")
    public ConditionMeta getConditionMeta(Class clazz);
    
    /**
     * ע��������
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    public void registerCondition(Class clazz);
    
    /**
     * ��ȡSQL����
     * @return
     */
    public SimpleQueryDefinition getSimpleQueryDefinition(String clazzName);
}