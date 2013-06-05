/**
 * 
 */
package cistern.security.pki.selfsigned;

import org.springframework.stereotype.Component;

import cistern.common.AppBizException;
import cistern.security.pki.CertApplyRequest;
import cistern.security.pki.CertApplyResult;
import cistern.security.pki.PKIService;

/**
 * @author panqingrong
 *
 */
@Component(value="cistern.security.pki.selfsigned.PKIService")
public class PKIServiceSelfSignedImpl implements PKIService {

	/* (non-Javadoc)
	 * @see cistern.security.pki.PKIService#certApply(cistern.security.pki.CertApplyRequest)
	 */
	@Override
	public CertApplyResult certApply(CertApplyRequest req)
			throws AppBizException {
		// TODO Auto-generated method stub
		return null;
	}

}
