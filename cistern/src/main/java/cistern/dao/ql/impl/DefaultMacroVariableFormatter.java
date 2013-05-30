package cistern.dao.ql.impl;

import cistern.utils.StringFormat;


/**
 * @description 缺省宏变量格式化器实现类 
 * @author seabao
 * @project Cistern
 * @date 2005-9-13
 */
public class DefaultMacroVariableFormatter implements MacroVariableFormatter {
    public String format(String pattern, Object value) {
        return StringFormat.toString(pattern, value);
    }

}
