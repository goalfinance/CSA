/**
 * 
 */
package cistern.solutions.acct.dao;

import cistern.dao.GenericDao;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.QueryAccountCond;

/**
 * @project: cistern
 * @description: �˻���Ϣ���ݲ����ӿ�
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
public interface AccountDao extends GenericDao<Account, Long, QueryAccountCond> {

}
