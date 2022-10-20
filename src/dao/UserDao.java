package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.User;

public class UserDao {
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/bank?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
	String user = "root";
	String password = "ezen";
	
	public int insertUser(String registerNum, String name) {
		
		int result = 0;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			String sql = "insert into user(user_register_num, user_name) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, registerNum);
			pstmt.setString(2, name);
			
			result = pstmt.executeUpdate();
			System.out.println("입력 완료");
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public User readUserByRegNum(String registerNum) {	
		
		User tmp = null;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "select * from user where user_register_num =" + registerNum;
			rs = stmt.executeQuery(sql);
			
			
			while(rs.next()) {
			    int userNum = rs.getInt("user_num"); // 컬럼명 (!!!!주의!!!! DTO의 이름과 DB의 컬럼명이 다름 )
			    String userRegisterNum = rs.getString("user_register_num");
			    String name = rs.getString("user_name");
			    String since = rs.getString("user_since");
			    
			    tmp = new User(userNum, userRegisterNum, name, since);
			    
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tmp;
	}
}