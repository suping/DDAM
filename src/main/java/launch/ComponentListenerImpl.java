package launch;

import org.apache.tuscany.sca.impl.DependencyUtils;

public class ComponentListenerImpl {
	private static ComponentListenerImpl instance = new ComponentListenerImpl();
	
	
	private ComponentListenerImpl(){		
	}
	
	public static ComponentListenerImpl getInstance(){
		return instance;
	}
	
	public boolean notify(String objectId,String event){
		System.out.println(objectId+","+event);
		dynamicDependency.getInstance(objectId).trigger(objectId,event);
		return false;
	}

}
