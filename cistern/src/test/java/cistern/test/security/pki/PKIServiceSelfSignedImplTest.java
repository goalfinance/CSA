/**
 * 
 */
package cistern.test.security.pki;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cistern.security.pki.CertApplyRequest;
import cistern.security.pki.CertApplyResult;
import cistern.security.pki.PKIService;

/**
 * @author panqingrong
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:cistern/test/security/pki/applicationContext.xml"})
public class PKIServiceSelfSignedImplTest {
	
	private PKIService pkiService;
	
	//Testing applying certification process.
	@Test
	public void testCertApplying() throws Exception{
		
		CertApplyRequest req = new CertApplyRequest();
		
		CertApplyResult res =  getPkiService().certApply(req);
	}

	
	public PKIService getPkiService() {
		return pkiService;
	}

	@Resource(name="cistern.security.pki.selfsigned.PKIService")
	public void setPkiService(PKIService pkiService) {
		this.pkiService = pkiService;
	}
	
	
}
