/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

package launch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;

import org.apache.tuscany.sca.TuscanyRuntime;
import org.apache.tuscany.sca.contribution.processor.ContributionReadException;
import org.apache.tuscany.sca.monitor.ValidationException;
import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.node.ContributionLocationHelper;
import org.apache.tuscany.sca.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.oasisopen.sca.NoSuchServiceException;

import services.ProcService;
import services.TokenService;

import cn.edu.nju.moon.vc.printer.domain.CurrentDomain;

public class LaunchPortal {
	public static void main(String[] args) throws Exception {
		System.out.println("Starting node portal....");

		TuscanyRuntime runtime = TuscanyRuntime.newInstance();
		String domainName = "cn.edu.nju.moon.version-consistency";
        String userIdPsw = "userid=" + domainName + "&password=njuics";
        String domainUri = "uri:" + domainName + "?" + userIdPsw;
        //create Tuscany node
        Node node = runtime.createNode(domainUri);
		String contributionURL = ContributionLocationHelper.getContributionLocation(LaunchPortal.class);
		node.installContribution(contributionURL);
		node.startComposite("vc-policy-portal-node", "portal.composite");

//		CurrentDomain.printDomainAndNodeConf(runtime, node);
//		accessServices(node);
		
//		Timer timer = new Timer();
//		timer.schedule(new Freeness("TokenComponent"), 10, 2000);
		System.out.println("Timer Start!");
		long startTime=System.currentTimeMillis();
		ServiceAccessing accessor = new ServiceAccessing();
		accessor.accessServices(node);
		startTime = System.currentTimeMillis()-startTime;
		System.out.println("Running time of AccessServices is "+ startTime);
//		timer.cancel();
		System.out.println("Timer End!");
		System.out.println("portal.composite ready for big business !!!");
		System.in.read();
		System.out.println("Stopping ...");
		node.stop();
		System.out.println();
	}

//	private static void accessServices(Node node) {
//		try {
//			System.out.println("Please input username and password [userName password]....");
//			InputStreamReader is = new InputStreamReader(System.in);
//			BufferedReader br = new BufferedReader(is);
//			String info = br.readLine();
//
//			String[] infos = info.split(" ");
//			String name = infos[0];
//			String passwd = infos[1];
//
//			System.out.println("\nTry to access TokenComponent#service-binding(TokenService/TokenService):");
//			TokenService ts = node.getService(TokenService.class,
//							"TokenComponent#service-binding(TokenService/TokenService)");
//			String cred = name + "," + passwd;
//			String token = ts.getToken(cred);
//			ComponentListenerImpl.getInstance().notify(this.toString(),"11");
//			System.out.println("\t" + "" + token);
//
//			System.out.println("\nTry to access ProcComponent#service-binding(ProcService/ProcService):");
//			ProcService pi = node.getService(ProcService.class,
//							"ProcComponent#service-binding(ProcService/ProcService)");
//			List<String> result = pi.process(token, "");
//			System.out.println("\t" + "" + result);
//						
//			System.out.println();
//		} catch (NoSuchServiceException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
