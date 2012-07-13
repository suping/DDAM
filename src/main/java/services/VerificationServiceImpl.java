package services;

import org.oasisopen.sca.annotation.Service;

@Service(VerificationService.class)
public class VerificationServiceImpl implements VerificationService{

	@Override
	public Boolean verify(String token) {
		String[] tokens = token.split(",");
		if(tokens[2].equals("pass"))
			return true;
		else if(tokens[2].equals("fail"))
			return false;
		else
			return false;
	}

}
