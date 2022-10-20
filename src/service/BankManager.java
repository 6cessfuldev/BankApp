package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import dao.AccountDao;
import dao.UserDao;
import domain.Account;

public class BankManager {

	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	UserDao userDao = new UserDao();
	AccountDao accDao = new AccountDao();
	int cnt = 1;
	
	public BankManager() {
		cnt = accDao.readSeq();
	}
	
	public void menu() throws NumberFormatException, IOException {
		
		int choice = 0;
		do {
			System.out.println("-----------------------");
			System.out.println("1. 회원가입");
			System.out.println("2. 계좌 개설");
			System.out.println("3. 계좌 조회");
			System.out.println("4. 입금");
			System.out.println("5. 출금");
			System.out.println("6. 송금");
			System.out.println("7. 종료");
			choice = Integer.parseInt(br.readLine());
			
			switch (choice) {
			case 1 : 
				registerUser(); 
				break;
			case 2 : 
				registerAccount(); 
				break;
			case 3 : 
				readAccount(); 
				break;
			case 4 : 
				deposit(); 
				break;
			case 5 : 
				withdraw(); 
				break;
			case 6 : 
				wire(); 
				break;
			case 7 : 
				System.out.println("종료합니다."); 
				break;
				default : System.out.println("잘못된 입력입니다.");
			}
			
		} while (choice != 7);
	}

	public void registerAccount() throws IOException {
		System.out.println("주민번호를 입력해주세요.(특수문자X)");
		String registerNum = br.readLine();
		if(userDao.readUserByRegNum(registerNum) == null) {
			System.out.println("가입된 회원이 아닙니다.");
			return;
		};
		//user 번호
		int userNum = userDao.readUserByRegNum(registerNum).getNum();
		//계좌 비밀번호
		System.out.println("생성할 계좌의 비밀번호를 입력해주세요.");
		String accPw = br.readLine(); 
		//계좌 번호
		String num = String.format(new DecimalFormat("0000").format(cnt++));
		String accNum = "Bank-" + num;
		//등록
		accDao.insertAccount(accNum, userNum, accPw);
		System.out.println("계좌가 생성되었습니다.");
		System.out.println("계좌 번호는 "+accNum+"입니다.");
	}

	public void registerUser() throws IOException {
		System.out.println("이름을 입력해주세요.");
		String name = br.readLine();
		System.out.println("주민번호를 입력해주세요.(특수문자X)");
		String registerNum = br.readLine();
		
		if(userDao.readUserByRegNum(registerNum) != null) {
			System.out.println("이미 가입된 정보입니다.");
			return;
		};
		
		userDao.insertUser(registerNum, name);
		System.out.println("가입되었습니다.");
	}
	
	public void readAccount() throws IOException {
		System.out.println("계좌번호를 입력해주세요.");
		String accNum = br.readLine();
		
		Account acc = accDao.readAccount(accNum);
		
		if(acc == null) {
			System.out.println("등록된 계좌 번호가 아닙니다.");
			return;
		}
		
		System.out.println("비밀번호를 입력해주세요.");
		String pw = br.readLine();
		if(!acc.getAccPw().equals(pw)) {
			System.out.println("잘못된 비밀번호입니다.");
			return;
		}
		System.out.print("계좌의 잔액은 : " + acc.getBalance()+"원입니다.");
	}
	
	public void deposit() throws IOException {
		
		System.out.println("계좌번호를 입력하세요.");
		String accNum = br.readLine();
		
		Account acc = accDao.readAccount(accNum);
		
		if(acc == null) {
			System.out.println("등록된 계좌 번호가 아닙니다.");
			return;
		}
		
		System.out.println("비밀번호를 입력해주세요.");
		String pw = br.readLine();
		if(!acc.getAccPw().equals(pw)) {
			System.out.println("잘못된 비밀번호입니다.");
			return;
		}
		
		System.out.println("얼마를 입금하시겠습니까?");
		long deposit = Long.parseLong(br.readLine());
		deposit += acc.getBalance();
		accDao.updateAccount(accNum, deposit);
		System.out.println("입금되었습니다.");
	}
	
	public void withdraw() throws IOException {
		
		System.out.println("계좌번호를 입력하세요.");
		String accNum = br.readLine();
		
		Account acc = accDao.readAccount(accNum);
		
		if(acc == null) {
			System.out.println("등록된 계좌 번호가 아닙니다.");
			return;
		}
		
		System.out.println("비밀번호를 입력해주세요.");
		String pw = br.readLine();
		if(!acc.getAccPw().equals(pw)) {
			System.out.println("잘못된 비밀번호입니다.");
			return;
		}
		
		System.out.println("얼마를 출금하시겠습니까?");
		long withdraw = Long.parseLong(br.readLine());
		withdraw = acc.getBalance() - withdraw;
		if(withdraw < 0) {
			System.out.println("잔액이 부족합니다.");
			return;
		}
		accDao.updateAccount(accNum, withdraw);
		System.out.println("출금되었습니다.");
	}
	
	public void wire() throws IOException {
		
		System.out.println("계좌번호를 입력하세요.");
		String accNum = br.readLine();
		
		Account acc = accDao.readAccount(accNum);
		
		if(acc == null) {
			System.out.println("등록된 계좌 번호가 아닙니다.");
			return;
		}
		
		System.out.println("비밀번호를 입력해주세요.");
		String pw = br.readLine();
		if(!acc.getAccPw().equals(pw)) {
			System.out.println("잘못된 비밀번호입니다.");
			return;
		}
		
		System.out.println("얼마를 송금하시겠습니까?");
		long wire = Long.parseLong(br.readLine());
		long giver = acc.getBalance() - wire;
		if(giver < 0) {
			System.out.println("잔액이 부족합니다.");
			return;
		}
		
		System.out.println("송금할 계좌번호를 입력해주세요.");
		String accNum2 = br.readLine();
		Account acc2 = accDao.readAccount(accNum2);
		if(acc2 == null) {
			System.out.println("등록된 계좌 번호가 아닙니다.");
			return;
		}
		long receiver = acc2.getBalance() + wire;
		accDao.updateAccount(accNum, giver);
		accDao.updateAccount(accNum2, receiver);
		System.out.println("송금되었습니다.");
		
	}
}
