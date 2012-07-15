package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.oasisopen.sca.annotation.Service;

import com.mysql.jdbc.Statement;


@Service(DBService.class)
public class DBServiceImpl implements DBService {

	@Override
	public List<String> dbOperation() {
		String sql = "select * from info";

		String driver = "com.mysql.jdbc.Driver"; 
        String url = "jdbc:mysql://114.212.84.235:3306/user";
        String user = "root"; 
        String password = "artemis"; 
        Connection conn;
        Statement stmt;
        ResultSet rs;
        List<String> result = new ArrayList<String>();
        
		try {
			Class.forName(driver); 
			conn = DriverManager.getConnection(url, user, password);
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			
			while(rs.next()){
				String str = "id: " + rs .getString("id")+ " name: " + rs.getString("name") + " passwd: "+ rs.getString("passwd");
				result.add(str);
				
				System.out.println("user" + rs .getString("id") + " " + str);
			}
			rs.close();
			stmt.close();
			
			if(!conn.isClosed()) 
				conn.close(); 
				//System.out.println("connect success��"); 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
