package launch;

import org.apache.tuscany.sca.TuscanyRuntime;
import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.node.ContributionLocationHelper;
import org.apache.tuscany.sca.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.oasisopen.sca.NoSuchServiceException;

import cn.edu.nju.moon.vc.printer.domain.CurrentDomain;


public class LaunchDB {
	/**
	 * distributed.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
        System.out.println("Starting node DB....");

        TuscanyRuntime runtime = TuscanyRuntime.newInstance();
        String domainName = "cn.edu.nju.moon.version-consistency";
        String userIdPsw = "userid=" + domainName + "&password=njuics";
        String domainUri = "uri:" + domainName + "?" + userIdPsw;
        //create Tuscany node
        Node node = runtime.createNode(domainUri);
        String contributionURL = ContributionLocationHelper.getContributionLocation(LaunchDB.class);
        node.installContribution(contributionURL);
        node.startComposite("vc-policy-db-node", "db.composite");
        
//        CurrentDomain.printDomainAndNodeConf(runtime, node);
        
        System.out.println("db.composite ready for big business !!!");
        System.in.read();
        System.out.println("Stopping ...");
        node.stop();
        System.out.println();
    }
	
}
