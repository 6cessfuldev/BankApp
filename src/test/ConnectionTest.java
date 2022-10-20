package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String db_url = "jdbc:mysql://localhost:3306/bank?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
			String db_id = "root";
			String db_pw = "ezen";
			conn = DriverManager.getConnection(db_url, db_id, db_pw);
			
			System.out.println(conn);
			
			stmt = conn.createStatement();
			String sql = "select * from user";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
			    String num = rs.getString("user_num"); // 컬럼명 (!!!!주의!!!! DTO의 이름과 DB의 컬럼명이 다름 )
			    String registerNum = rs.getString("user_register_num");
			    String name = rs.getString("user_name");
			    String since = rs.getString("user_since");
			    
			    System.out.println(num + registerNum + name + since);
			}
		    
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				} 
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}		
	}
}
