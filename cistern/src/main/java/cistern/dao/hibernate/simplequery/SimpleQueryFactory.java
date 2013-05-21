/*
 * File SimpleQueryFactory.java
 * Created on 2004-7-13
 */
package cistern.dao.hibernate.simplequery;

import cistern.dao.hibernate.HQLCondition;
import cistern.dao.hibernate.HQLQuery;


/**
 * SimpleQuery工厂类
 * @author seabao
 * @project Cistern
 * @date 2004-7-13
 */
public interface SimpleQueryFactory {
    /**
     * 通过查询条件对象生成一个HQLQuery对象
     * @param condition
     * @return
     */
    public HQLQuery genHQLQuery(Object condition);
    
    /**
     * 通过查询条件对象生成一个HQLQuery对象
     */
    @SuppressWarnings("unchecked")
    public HQLQuery genHQLQuery(Class clazz, Object condition);
    
    /**
     * 生成HQL条件对象
     * @param condition
     * @return
     */
    @SuppressWarnings("unchecked")
    public HQLCondition genHQLCondition(Class clazz, Object condition);
    
    /**
     * 生成HQL条件对象
     * @param condition
     * @return
     */
    public HQLCondition genHQLCondition(Object condition);
    
    /**
     * 获得查询类对应的条件元对象
     */
    @SuppressWarnings("unchecked")
    public ConditionMeta getConditionMeta(Class clazz);
    
    /**
     * 注册条件类
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    public void registerCondition(Class clazz);
    
    /**
     * 获取SQL定义
     * @return
     */
    public SimpleQueryDefinition getSimpleQueryDefinition(String clazzName);
}