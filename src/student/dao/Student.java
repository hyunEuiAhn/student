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
			System.out.println("����̹� �ε� ����");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("���� ����");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public void menu() {
		
		while(true) {
			System.out.println("****************");
			System.out.println("	����		");
			System.out.println("****************");
			System.out.println("	1. �Է�");
			System.out.println("	2. �˻�");
			System.out.println("	3. ����");
			System.out.println("	4. ����");
			System.out.println("****************");
			System.out.println("��ȣ���� : ");
			int num = scan.nextInt();
			
			if(num==4) {
				System.out.println("���α׷� ����");
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
			System.out.println("	1. �л�");
			System.out.println("	2. ����");
			System.out.println("	3. ������");
			System.out.println("	4. �����޴�");
			System.out.println("****************");
			System.out.println("��ȣ���� : ");
			int num = scan.nextInt();
			String name = null, sub = null;
			int code = 0;
			
			if(num==1) {
				System.out.println("�̸��Է� :");
				name = scan.next();
				System.out.println("�й� :");
				sub = scan.next();
				code = 1;
			}
			else if(num==2) {
				System.out.println("�̸��Է� :");
				name = scan.next();
				System.out.println("���� :");
				sub = scan.next();
				code = 2;
			}

			else if(num==3) {
				System.out.println("�̸��Է� :");
				name = scan.next();
				System.out.println("�μ� :");
				sub = scan.next();
				code = 3;
			}
			
			String sql = "insert into student values(?,?,?)";
			try {
				pstmt = conn.prepareStatement(sql);//����
				pstmt.setString(1,name);
				pstmt.setString(2,sub);
				pstmt.setInt(3, code);
				int su = pstmt.executeUpdate();//����
				//select ���� ��� ddl��� ������
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
			System.out.println("	1. �̸��˻�(1�� ���ڰ� ���Ե� �̸��� ��� �˻�)");
			System.out.println("	2. ��ü�˻�");
			System.out.println("	3. �����޴�");
			System.out.println("****************");
			System.out.println("��ȣ���� : ");
			int num = scan.nextInt();
			
			if(num==1) {

				System.out.println("�˻��� �̸��� �Է��ϼ���.");
				String name=scan.next();
				String sql = "select code, name, value from student where name like"+"'%"+name+"%'";
				
				try {
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();//����
					
					while(rs.next()) {
						if(rs.getInt("code")==1) {
							System.out.println("�̸� : "+rs.getString("name")+"\t"//	rs.getString("1");
											 + "�а� : "+rs.getString("value"));
						}
						if(rs.getInt("code")==2) {
							System.out.println("�̸� : "+rs.getString("name")+"\t"//	rs.getString("1");
											 + "���� : "+rs.getString("value"));
						}
						if(rs.getInt("code")==3) {
							System.out.println("�̸� : "+rs.getString("name")+"\t"//	rs.getString("1");
											 + "�μ� : "+rs.getString("value"));
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
		
		System.out.println("������ �̸��� �Է��ϼ���.(��Ȯ�ϰ� 3���� ���ڰ� ��� �Էµ� ����)");
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
