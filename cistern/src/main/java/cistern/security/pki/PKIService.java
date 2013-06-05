/**
 * 
 */
package cistern.security.pki;

import org.springframework.stereotype.Component;

import cistern.common.AppBizException;

/**
 * 
 * The Public-Key-Infrastructure service.
 * @author panqingrong
 *
 */
public interface PKIService {
	
	/**
	 * Applying certification.
	 * @param req	Applying request.
	 * @return	The result of this applying request.
	 * @throws AppBizException	Throwing it when applying process meets error.
	 */
	public CertApplyResult certApply(CertApplyRequest req) throws AppBizException;
	
	
	
	
	

}
