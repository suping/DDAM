package launch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.tuscany.sca.Node;
import org.oasisopen.sca.NoSuchServiceException;

import services.ProcService;
import services.TokenService;

public class ServiceAccessing {
		public void accessServices(Node node) throws Exception {
		    Freeness fn = new Freeness("TokenComponent");
			System.out.println("Please input username and password [userName password]....");
			fn.run();
			ComponentListenerImpl.getInstance().notify("accessServices", "Start");
			fn.run();
			InputStreamReader is = new InputStreamReader(System.in);
			fn.run();
			BufferedReader br = new BufferedReader(is);
			fn.run();
			String info = br.readLine();
			fn.run();

			String[] infos = info.split(" ");
			String name = infos[0];
			String passwd = infos[1];
			fn.run();

			System.out.println("\nTry to access TokenComponent#service-binding(TokenService/TokenService):");
			TokenService ts = node.getService(TokenService.class,
							"TokenComponent#service-binding(TokenService/TokenService)");
			fn.run();
			String cred = name + "," + passwd;
			fn.run();
			String token = ts.getToken(cred);
			ComponentListenerImpl.getInstance().notify("accessServices", "Ejb.TokenComponent/TokenService.122");
			fn.run();
			ts.getToken(cred);
			ComponentListenerImpl.getInstance().notify("accessServices", "Ejb.TokenComponent/TokenService.132");
			fn.run();			
			System.out.println("\t" + "" + token);
			fn.run();

			System.out.println("\nTry to access ProcComponent#service-binding(ProcService/ProcService):");
			ProcService pi = node.getService(ProcService.class,
							"ProcComponent#service-binding(ProcService/ProcService)");
			List<String> result = pi.process(token, "");
			fn.run();
			ComponentListenerImpl.getInstance().notify("accessServices", "Ejb.ProcComponent/ProcService.178");
			fn.run();
			System.out.println("\t" + "" + result);
			fn.run();			
			System.out.println();
			fn.run();
			ComponentListenerImpl.getInstance().notify("accessServices","");
			fn.run();
	}
}
