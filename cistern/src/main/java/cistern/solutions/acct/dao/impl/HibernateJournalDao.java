/**
 * 
 */
package cistern.solutions.acct.dao.impl;

import cistern.dao.hibernate.hibernate3.HibernateGenericDao;
import cistern.solutions.acct.dao.JournalDao;
import cistern.solutions.acct.domain.Journal;
import cistern.solutions.acct.domain.QueryJournalCond;

/**
 * @project: cistern
 * @description: 
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
public class HibernateJournalDao extends
		HibernateGenericDao<Journal, Long, QueryJournalCond> implements
		JournalDao {
}
