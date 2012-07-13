package services;

import java.util.Random;

import org.oasisopen.sca.annotation.Service;

@Service(TokenService.class)
public class TokenServiceImpl implements TokenService {

	@Override
	public String getToken(String cred) {
		String[] creds = cred.split(",");
		if("nju".equals(creds[0]) && "cs".equals(creds[1])){
			StringBuilder sb = new StringBuilder(cred);
			sb.append(",pass") ;
			return sb.toString();
		}
		StringBuilder tmp = new StringBuilder(cred);
		tmp.append(",fail") ;
		return tmp.toString();
	}
	@Override
	public int getToken() {
		Random rand = new Random();
		return rand.nextInt(13);

	}

}
