package launch;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;





public class dynamicDependency {
	String transactionid;
	List<String> real_past=new LinkedList<String>();
	String past = "";
	int currentState=0;
	List<String> states = new LinkedList<String>();
	List<String> events = new LinkedList<String>();	
	static Hashtable<String,dynamicDependency> ddes = new Hashtable<String,dynamicDependency>();
	public dynamicDependency(){
		
	}
	//private  dynamicDependency instance; 
	public static dynamicDependency getInstance(String name){
		if(ddes.containsKey(name)){
			return ddes.get(name);
		}
		else
		{
			dynamicDependency instance = new dynamicDependency(name);
			ddes.put(name, instance);
			return instance;
		}		
	}
	public String getFuture(){
		return states.get(currentState);
	}
	public String getPast(){
		return past;
	}
	
	private dynamicDependency(String name){
		transactionid = name;
		System.out.println("The input name" + name);
		File file = new File("e:\\ttp\\"+name+".txt");
		System.out.println("Read File e:\\ttp\\"+name+".txt");
		BufferedReader reader = null;
		try {
		//System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
		reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		int line = 1;
		//һ�ζ���һ�У�ֱ������nullΪ�ļ�����
		while ((tempString = reader.readLine()) != null){
		System.out.println("line " + line + ": " + tempString);	
		//��ʾ�к�
		if(line==1){
			System.out.println(tempString.split(";").length);
			for(int i=0;i<tempString.split(";").length;i++){
				String ei = tempString.split(";")[i];
				if(ei.equals("E"))
				{
					ei="";
				}
				states.add(ei);
				System.out.println(ei);
			}
		}
		else{
			System.out.println(tempString.split(";").length);
			for(int i=0;i<tempString.split(";").length;i++){
				String ei = tempString.split(";")[i];
				if(ei.equals("E"))
				{
					ei="";
				}
				events.add(ei);
				System.out.println(ei);
			}
		}			
		line++;
		}
		reader.close();
		} catch (IOException e) {
		e.printStackTrace();
		} finally {
		if (reader != null){
		try {
		reader.close();
		} catch (IOException e1) {
		}
		}
		}
		
	}
	
	/**
	 * get the real past after the given trigger action dynamically
	 * @param event
	 */
	public void trigger(String id,String event){
		if(event.contains("Start")){
			currentState = 0;
			System.out.println("Transaction  "+id+"  is start!");
			System.out.println("Event=Start"+"Now,state="+currentState+";Future="+states.get(currentState)+";Past="+past);
		}
		else if(event.isEmpty()){
			System.out.println("This transaction is end!");
			ddes.remove(id);			
		}
		else{
		
		if(event.contains("Ejb")){
			String port = event.split("\\.")[1];		
			if(!real_past.contains(port))
			{
				real_past.add(port);
				past = past +","+port;
			}
		}
		
		String eve = events.get(currentState);
		String eveinf[] = eve.split(",");
		for(String e: eveinf){
			if(e.contains(event))
			{
				currentState = Integer.parseInt(e.split("-")[1]);
				System.out.println("Event="+e.split("-")[0]+";Now,state="+currentState+";Future="+states.get(currentState)+";Past="+past);
				return;
			}
			
		}
		System.out.println("The input event is wrong!");
	 }
		
	}
	public static void main(String args[]){
		
		dynamicDependency.getInstance("accessServices").trigger("accessServices", "Start");
		dynamicDependency.getInstance("accessServices").trigger("accessServices", "Ejb.TokenComponent/TokenService.76");
		dynamicDependency.getInstance("accessServices").trigger("accessServices", "Ejb.ProcComponent/ProcService.111");
		dynamicDependency.getInstance("accessServices").trigger("accessServices", "");
/*		dynamicDependency.getInstance("accessServices").trigger("accessServices", "Ejb.c.22");
		dynamicDependency.getInstance("accessServices").trigger("accessServices", "while.F.28");
		dynamicDependency.getInstance("accessServices").trigger("accessServices", "Ejb.a.33");
		dynamicDependency.getInstance("accessServices").trigger("accessServices", "Ejb.b.46");
		dynamicDependency.getInstance("accessServices").trigger("accessServices", "");*/
	}
	

}
