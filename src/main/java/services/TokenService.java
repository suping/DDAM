package services;

import org.oasisopen.sca.annotation.Remotable;

@Remotable
public interface TokenService {
	String getToken(String cred);
}
