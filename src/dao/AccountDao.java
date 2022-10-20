package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Account;

public class AccountDao {

	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/bank?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
	String user = "root";
	String password = "ezen";
	
	public int insertAccount(String accNum, int userNum, String accPw) {
		
		int result = 0;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			String sql = "insert into account(ac_num, ac_user_num, ac_password, ac_balance) values(?,?,?,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, accNum );
			pstmt.setInt(2, userNum );
			pstmt.setString(3, accPw);
			
			result = pstmt.executeUpdate();
			System.out.println("입력 완료");
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public int readSeq() {
		
		int result = 0;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "SELECT ac_seq FROM account ORDER BY ac_date DESC LIMIT 1";
			rs = stmt.executeQuery(sql);
			rs.next();
			int seq = rs.getInt("ac_seq");
			result = seq;
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}	finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Account readAccount(String accNum) {
		
		Account result = null;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "SELECT ac_user_num, ac_balance, ac_password FROM account";
			rs = stmt.executeQuery(sql);
			rs.next();
			int userNum = rs.getInt("ac_user_num");
			long balance = rs.getLong("ac_balance");
			String accPw = rs.getString("ac_password");
			
			result = new Account(accNum, userNum, balance, accPw);
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		return result;
	}
	
	public int updateAccount(String accNum, long deposit) {
		
		int result = 0;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			String sql = "update account set ac_balance = ? where ac_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, deposit );
			pstmt.setString(2, accNum);
			
			result = pstmt.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
}
