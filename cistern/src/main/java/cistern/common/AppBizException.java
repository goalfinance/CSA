package cistern.common;

/**   
 * @project: Cistern	
 * @description: 应用业务异常类
 */
public class AppBizException extends Exception {
	public static final long serialVersionUID = 0x01;

	/**
	 * 应用异常码
	 */
	private String code;

	/**
	 * 应用异常描述参数
	 */
	private Object[] args;

	/**
	 * 文本消息
	 */
	private String textMessage;

	public AppBizException() {
	}

	public AppBizException(String code, String msg) {
		super(code + ": " + msg);
		this.code = code;
	}

	public AppBizException(String code, String msg, Throwable cause) {
		super(code + ": " + msg, cause);
		this.code = code;
	}

	public AppBizException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String string) {
		code = string;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] objects) {
		args = objects;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

}