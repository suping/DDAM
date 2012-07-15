package services;

import java.util.List;

import org.oasisopen.sca.annotation.Reference;
import org.oasisopen.sca.annotation.Service;

@Service(PortalService.class)
public class PortalServiceImpl implements PortalService {
	private TokenService tokenService;
	private ProcService procService;

	public TokenService getTokenService() {
		return tokenService;
	}
	@Reference
	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public ProcService getProcService() {
		return procService;
	}
	@Reference
	public void setProcService(ProcService procService) {
		this.procService = procService;
	}

	public String getToken(String cred) {
		return tokenService.getToken(cred);
	}
	@Override
	public List<String> process(String token, String data) {
		return procService.process(token, data);
	}

}
