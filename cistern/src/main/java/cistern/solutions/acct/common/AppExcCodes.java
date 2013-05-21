/**
 * 
 */
package cistern.solutions.acct.common;

/**
 * @project: cistern
 * @description: 异常码枚举类
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
public class AppExcCodes {
	
	/**
	 * Acct.保存会计对象时出错
	 */
	public static final String ACCT_ACCTOBJ_SAVED_ERROR = "B.CISTERN.ACCT.0001";
	
	/**
	 * Acct.复式记账分录借贷不平衡
	 */
	public static final String ACCT_JOURNAL_CD_UNBALANCE = "B.CISTERN.ACCT.0002";
	
	/**
	 * Acct.会计分录审批时出现错误
	 */
	public static final String ACCT_JOURNAL_APPROVED_ERROR = "B.CISTERN.ACCT.0003";
	
	/**
	 * Acct.被操作的会计对象不在当前账期内
	 */
	public static final String ACCT_ACCTOBJ_OPERATED_NOT_IN_ACT_ACCTPERIOD = "B.CISTERN.ACCT.0004";
	
	/**
	 * Acct.找不到指定的会计分录信息
	 */
	public static final String ACCT_JOURNAL_NOT_FOUND = "B.CISTERN.ACCT.0005";
	
	/**
	 * Acct.没有设置当前会计账期
	 */
	public static final String ACCT_ACT_ACCT_PERIOD_DOSE_NOT_EXIST = "B.CISTERN.ACCT.0006";
	
	/**
	 * Acct.对会计分录的操作不被支持
	 */
	public static final String ACCT_JOURNAL_UNSUPPORTED_OPERATION = "B.CISTERN.ACCT.0007";
	
	/**
	 * Acct.账户信息不存在
	 */
	public static final String ACCT_ACCOUNT_DOES_NOT_EXIST = "B.CISTERN.ACCT.0008";
	
	/**
	 * Acct.存在重复的账户信息
	 */
	public static final String ACCT_DUPLICATED_ACCOUNT = "B.CISTERN.ACCT.0009";
	
	/**
	 * Acct.指定账期不存在
	 */
	public static final String ACCT_ACCT_PERIOD_DOSE_NOT_EXIST = "B.CISTERN.ACCT.0010";
	
	/**
	 * Acct.会计对象状态无效
	 */
	public static final String ACCT_ACCTOBJ_STATUS_INVALID = "B.CISTERN.ACCT.0011";
	
	/**
	 * Acct.指定账户没有可用的余额信息
	 */
	public static final String ACCT_ACCOUNT_HAS_NO_ACTIVE_BALANCE = "B.CISTERN.ACCT.0012";

}
