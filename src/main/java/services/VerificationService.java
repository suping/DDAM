package services;

import org.oasisopen.sca.annotation.Remotable;

@Remotable
public interface VerificationService {
	Boolean verify(String token);
}
