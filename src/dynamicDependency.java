import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



public class dynamicDependency {
	List<String> real_past=new LinkedList<String>();
	String past = "";
	int currentState=0;
	List<String> states = new LinkedList<String>();
	List<String> events = new LinkedList<String>();
	public dynamicDependency(String name){
		
		File file = new File("e:\\ttp\\"+name+".txt");
		BufferedReader reader = null;
		try {
		//System.out.println("以行为单位读取文件内容，一次读一整行：");
		reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		int line = 1;
		//一次读入一行，直到读入null为文件结束
		while ((tempString = reader.readLine()) != null){
		System.out.println("line " + line + ": " + tempString);	
		//显示行号
		if(line==1){
			for(int i=0;i<tempString.split(";").length;i++){
				states.add(tempString.split(";")[i]);
				System.out.println(tempString.split(";")[i]);
			}
		}
		else{
			for(int i=0;i<tempString.split(";").length;i++){
				events.add(tempString.split(";")[i]);
				System.out.println(tempString.split(";")[i]);
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
			System.out.println("future:"+states.get(currentState)+";past:"+past);
		}
		else if(event.isEmpty()){
			System.out.println("This transaction is end!");
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
				System.out.println("future:"+states.get(currentState)+";past:"+past);
				return;
			}
			
		}
	 }
		
	}
	public static void main(String args[]){
		dynamicDependency dd =new dynamicDependency("Tranquillity");
	}
	

}
