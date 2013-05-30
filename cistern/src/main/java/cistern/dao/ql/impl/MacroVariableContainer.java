package cistern.dao.ql.impl;

/**
 * <p>Title: Cistern</p>
 * <p>Description: 宏变量容器接口定义类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intensoft Corp</p>
 * @author seabao
 * @version 1.01
 * @date 2003-9-18
 */
public interface MacroVariableContainer {
	public void setMacroVariable(String name, Object value);

	public void removeMacroVariable(String name);

	public Object getMacroVariable(String name);
}
