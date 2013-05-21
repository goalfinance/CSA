package cistern.dao.hibernate.simplequery;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: Cistern</p>
 * <p>Description: 基于Map实现的宏变量容器类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intensoft Corp</p>
 * @author seabao
 * @version 1.01
 * @date 2003-9-18
 */
public class MapMacroVariableContainer implements MacroVariableContainer {
    private Map<String, Object> macroVariables = new HashMap<String, Object>();

    public MapMacroVariableContainer() {
    }

    public MapMacroVariableContainer(Map<String, Object> values) {
        this.macroVariables = values;
    }

    public void setMacroVariables(Map<String, Object> values) {
        if (values == null) {
            return;
        }

        macroVariables.putAll(values);
    }

    public void clear() {
        macroVariables.clear();
    }

    public void setMacroVariable(String name, Object value) {
        macroVariables.put(name, value);
    }

    public void removeMacroVariable(String name) {
        macroVariables.remove(name);
    }

    public Object getMacroVariable(String name) {
        return macroVariables.get(name);
    }
}