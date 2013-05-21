/**
 * 
 */
package cistern.solutions.acct.dao;

import cistern.dao.GenericDao;
import cistern.solutions.acct.domain.Journal;
import cistern.solutions.acct.domain.QueryJournalCond;

/**
 * @project: cistern
 * @description: 
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
public interface JournalDao extends GenericDao<Journal, Long, QueryJournalCond> {

}
