package cistern.dao.ql.impl;

/**
 * @description 宏变量格式化器接口定义类 
 * @author seabao
 * @project Cistern
 * @date 2005-9-13
 */
public interface MacroVariableFormatter {
    public String format(String pattern, Object value);
}
