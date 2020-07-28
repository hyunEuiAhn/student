package student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "java";
	private String password = "itbank";
	private int sw = 0;
	Scanner scan = new Scanner(System.in);
	
	public void Student() {
		try {
			Class.forName(driver);
			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("접속 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public void menu() {
		
		while(true) {
			System.out.println("****************");
			System.out.println("	관리		");
			System.out.println("****************");
			System.out.println("	1. 입력");
			System.out.println("	2. 검색");
			System.out.println("	3. 삭제");
			System.out.println("	4. 종료");
			System.out.println("****************");
			System.out.println("번호선택 : ");
			int num = scan.nextInt();
			
			if(num==4) {
				System.out.println("프로그램 종료");
				break;
			}
			else if(num==1) {
				this.insertArticle();
			}
			else if(num==2) {
				this.selectArticle();
			}
			else if(num==3) {
				this.deleteArticle();
			}
			
		}
	}
	
	public void insertArticle() {
		Connection conn = getConnection();
		PreparedStatement pstmt=null;
		
		while(true) {
			System.out.println("****************");
			System.out.println("	1. 학생");
			System.out.println("	2. 교수");
			System.out.println("	3. 관리자");
			System.out.println("	4. 이전메뉴");
			System.out.println("****************");
			System.out.println("번호선택 : ");
			int num = scan.nextInt();
			String name = null, sub = null;
			int code = 0;
			
			if(num==1) {
				System.out.println("이름입력 :");
				name = scan.next();
				System.out.println("학번 :");
				sub = scan.next();
				code = 1;
			}
			else if(num==2) {
				System.out.println("이름입력 :");
				name = scan.next();
				System.out.println("과목 :");
				sub = scan.next();
				code = 2;
			}

			else if(num==3) {
				System.out.println("이름입력 :");
				name = scan.next();
				System.out.println("부서 :");
				sub = scan.next();
				code = 3;
			}
			
			String sql = "insert into student values(?,?,?)";
			try {
				pstmt = conn.prepareStatement(sql);//생성
				pstmt.setString(1,name);
				pstmt.setString(2,sub);
				pstmt.setInt(3, code);
				int su = pstmt.executeUpdate();//실행
				//select 빼고 모든 ddl언어 다해줌
				System.out.println(su+"row created");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(num==4) {
				try {
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			break;
			}
		}//while
		
	}
	
	
	public void selectArticle() {
		Connection conn = getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		while(true) {
			System.out.println("****************");
			System.out.println("	1. 이름검색(1개 글자가 포함된 이름은 모두 검색)");
			System.out.println("	2. 전체검색");
			System.out.println("	3. 이전메뉴");
			System.out.println("****************");
			System.out.println("번호선택 : ");
			int num = scan.nextInt();
			
			if(num==1) {

				System.out.println("검색할 이름을 입력하세요.");
				String name=scan.next();
				String sql = "select code, name, value from student where name like"+"'%"+name+"%'";
				
				try {
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();//실행
					
					while(rs.next()) {
						if(rs.getInt("code")==1) {
							System.out.println("이름 : "+rs.getString("name")+"\t"//	rs.getString("1");
											 + "학과 : "+rs.getString("value"));
						}
						if(rs.getInt("code")==2) {
							System.out.println("이름 : "+rs.getString("name")+"\t"//	rs.getString("1");
											 + "과목 : "+rs.getString("value"));
						}
						if(rs.getInt("code")==3) {
							System.out.println("이름 : "+rs.getString("name")+"\t"//	rs.getString("1");
											 + "부서 : "+rs.getString("value"));
						}
						
					}//while
					
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			
			
			if(num==3) {
				try {
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			break;
			}
			
		}//while
		
		
	}
	
	public void deleteArticle() {
		Connection conn = getConnection();
		PreparedStatement pstmt=null;
		
		System.out.println("삭제할 이름을 입력하세요.(정확하게 3개의 글자가 모두 입력된 상태)");
		String name=scan.next();
		
		String sql = "delete from student where name =?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,name);
			int su = pstmt.executeUpdate();
			
			System.out.println(su+"row deleted");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null) conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Student student = new Student();
		student.menu();
	}
}
