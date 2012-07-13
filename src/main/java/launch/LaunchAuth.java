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


import org.apache.tuscany.sca.TuscanyRuntime;
import org.apache.tuscany.sca.node.ContributionLocationHelper;
import org.apache.tuscany.sca.Node;

import cn.edu.nju.moon.vc.printer.domain.CurrentDomain;


public class LaunchAuth {
	public static void main(String[] args) throws Exception {
        System.out.println("Starting auth node ....");

        TuscanyRuntime runtime = TuscanyRuntime.newInstance();
        String domainName = "cn.edu.nju.moon.version-consistency";
        String userIdPsw = "userid=" + domainName + "&password=njuics";
        String domainUri = "uri:" + domainName + "?" + userIdPsw;
        //create Tuscany node
        Node node = runtime.createNode(domainUri);
        String contributionURL = ContributionLocationHelper.getContributionLocation(LaunchAuth.class);
        node.installContribution(contributionURL);
        node.startComposite("vc-policy-auth-node", "auth.composite");
        
//        CurrentDomain.printDomainAndNodeConf(runtime, node);
        
        System.out.println("auth.composite ready for big business !!!");
        System.in.read();
        System.out.println("Stopping ...");
//        node.stopCompositeAndUninstallUnused("vc-policy-auth-node", "auth.composite");
        node.stop();
        System.out.println();
    }
	
}
