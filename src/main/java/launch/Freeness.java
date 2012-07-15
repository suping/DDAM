package launch;
import java.util.TimerTask;
public class Freeness //extends java.util.TimerTask
{

	String comname;
	int count=0;
    public Freeness(String name){
	comname = name;
    }
    public void run(){
    	count++;
    	String processF = dynamicDependency.getInstance("process").getFuture();
    	String processP = dynamicDependency.getInstance("process").getPast();
    	String accessF = dynamicDependency.getInstance("accessServices").getFuture();
    	String accessP = dynamicDependency.getInstance("accessServices").getPast();
    	System.out.println(count+":" +processF+";" +processP+";" +accessF+";" +accessP+";");
    	System.out.println(comname+"whether tranquillity " + isTranquillity(processF,processP,accessF,accessP));
    	System.out.println(comname+"whether version-consistency " + isVersionConsistency(processF,processP,accessF,accessP));
    }
    public boolean isTranquillity(String processF,String processP,String accessF,String accessP){
    	
    	if((processF.contains(comname)&&processP.contains(comname)) || (accessF.contains(comname) && accessP.contains(comname))){
    		return false;
    	}
    	return true;
    	
    }
    public boolean isVersionConsistency(String processF,String processP,String accessF,String accessP){
    	if(isTranquillity(processF,processP,accessF,accessP)){
    		if((processF.contains(comname)&&accessP.contains(comname))||(processP.contains(comname)&&accessF.contains(comname)))
    		{
    			return false;
    		}
    		else
    			return true;
    	}
    	else
    		return false;
    }
}
