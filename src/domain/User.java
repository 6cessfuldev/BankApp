package domain;

public class User {

	private int num;
	private String registerNum;
	private String name;
	private String since;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getRegisterNum() {
		return registerNum;
	}
	public void setRegisterNum(String registerNum) {
		this.registerNum = registerNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSince() {
		return since;
	}
	public void setSince(String since) {
		this.since = since;
	}
	public User(int num, String registerNum, String name, String since) {
		super();
		this.num = num;
		this.registerNum = registerNum;
		this.name = name;
		this.since = since;
	}
	
	
	
}
