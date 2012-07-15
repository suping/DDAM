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

import java.util.List;
import java.util.Map;

import org.apache.tuscany.sca.TuscanyRuntime;
import org.apache.tuscany.sca.assembly.Endpoint;
import org.apache.tuscany.sca.assembly.EndpointReference;
import org.apache.tuscany.sca.impl.NodeImpl;
import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.node.ContributionLocationHelper;
import org.apache.tuscany.sca.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.apache.tuscany.sca.runtime.DomainRegistry;
import org.oasisopen.sca.NoSuchServiceException;

import cn.edu.nju.moon.vc.printer.domain.CurrentDomain;

import services.DBService;
import services.ProcService;
import services.TokenService;
import services.VerificationService;



public class LaunchProc {
	/**
	 * distributed.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
        System.out.println("Starting node Proc....");

        TuscanyRuntime runtime = TuscanyRuntime.newInstance();
        String domainName = "cn.edu.nju.moon.version-consistency";
        String userIdPsw = "userid=" + domainName + "&password=njuics";
        String domainUri = "uri:" + domainName + "?" + userIdPsw;
        //create Tuscany node
        Node node = runtime.createNode(domainUri);
        String contributionURL = ContributionLocationHelper.getContributionLocation(LaunchProc.class);
        node.installContribution(contributionURL);
        node.startComposite("vc-policy-proc-node", "proc.composite");
        
//        CurrentDomain.printDomainAndNodeConf(runtime, node);
//        accessServices(node);
        
        System.out.println("proc.composite ready for big business !!!");
        System.in.read();
        System.out.println("Stopping ...");
        node.stop();
        System.out.println();
    }
	
	private static void accessServices(Node node) {
		try {
			System.out.println("\nTry to access ProcComponent#service-binding(ProcService/ProcService):");
			ProcService pi = node.getService(ProcService.class, "ProcComponent#service-binding(ProcService/ProcService)");
			System.out.println("\t" + "" + pi.process("nju,cs,pass", ""));
//			
//			System.out.println("\nTry to access TokenComponent#service-binding(TokenService/TokenService):");
//			TokenService ts = node.getService(TokenService.class, "TokenComponent#service-binding(TokenService/TokenService)");
//			String token = ts.getToken("nju,cs");
//			System.out.println("\t" + "" + token);
//			
//			System.out.println("\nTry to access VerificationComponent#service-binding(VerificationService/VerificationService):");
//			VerificationService vs = node.getService(VerificationService.class, "VerificationComponent#service-binding(VerificationService/VerificationService)");
//			System.out.println("\t" + "" + vs.verify(token));
//			
//			System.out.println("\nTry to access DBComponent#service-binding(DBService/DBService):");
//			DBService db = node.getService(DBService.class, "DBComponent#service-binding(DBService/DBService)");
//			System.out.println("\t" + "" + db.dbOperation());
			
		} catch (NoSuchServiceException e) {
			e.printStackTrace();
		}
	}

}
