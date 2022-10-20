package domain;

public class Account {

	private String accNum;
	private int userNum;
	private long balance;
	private String accPw;
	public String getAccNum() {
		return accNum;
	}
	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public String getAccPw() {
		return accPw;
	}
	public void setAccPw(String accPw) {
		this.accPw = accPw;
	}
	
	public Account(String accNum, int userNum, long balance, String accPw) {
		super();
		this.accNum = accNum;
		this.userNum = userNum;
		this.balance = balance;
		this.accPw = accPw;
	}	
}
