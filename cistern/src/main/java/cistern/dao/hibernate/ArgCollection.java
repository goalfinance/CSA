package cistern.dao.hibernate;

import java.util.Collection;

/**
 * @project: Cistern
 * @description: 指令列表类
 * @author: seabao
 * @create_time: 2008-10-11
 * @modify_time: 2008-10-11
 */
public class ArgCollection {
	private Collection<?> args;

	public ArgCollection(Collection<?> args) {
		this.args = args;
	}

	public Collection<?> getArgs() {
		return args;
	}

	public void setArgs(Collection<?> args) {
		this.args = args;
	}
}
