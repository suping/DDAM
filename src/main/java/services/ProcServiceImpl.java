package services;

import java.util.List;

import launch.ComponentListenerImpl;

import org.oasisopen.sca.annotation.Reference;
import org.oasisopen.sca.annotation.Service;

@Service(ProcService.class)
public class ProcServiceImpl implements ProcService {
	private VerificationService verify;
	private DBService db;

	public VerificationService getVerify() {
		return verify;
	}
	@Reference
	public void setVerify(VerificationService verify) {
		this.verify = verify;
	}

	public DBService getDb() {
		return db;
	}

	@Reference
	public void setDb(DBService db) {
		this.db = db;
	}

	public List<String> process(String token, String data) {
		ComponentListenerImpl.getInstance().notify("process", "Start");
		Boolean authResult = verify.verify(token);
//		System.out.println("\n\n\n verify result from auth node: " + authResult);
		ComponentListenerImpl.getInstance().notify("process", "Ejb.VerificationComponent/VerificationService.5");
		if (authResult) {
			ComponentListenerImpl.getInstance().notify("process", "if.0.11");
//			System.out.println("db in Proc.java:" + db);
			List<String> dd = db.dbOperation();
			ComponentListenerImpl.getInstance().notify("process", "Ejb.DBComponent/DBService.16");
			ComponentListenerImpl.getInstance().notify("process", "");
			return dd;
		} else{
			ComponentListenerImpl.getInstance().notify("process", "if.1.11-4");
			ComponentListenerImpl.getInstance().notify("process", "");
			return null;
		}
			
	}

}
