/*
 * File ConditionPropertyConvertor.java
 * Created on 2004-7-13
 */
package cistern.dao.hibernate.simplequery;

/**
 * 查询条件属性转换器接口定义类
 * @author seabao
 * @project Cistern
 * @date 2004-7-13
 */
public interface ConditionPropertyConvertor {
    /**
     * 装换查询条件属性
     */
    public Object convert(Object conditionProperty);
}
