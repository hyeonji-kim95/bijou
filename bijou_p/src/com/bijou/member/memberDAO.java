package com.bijou.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class memberDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";	


	private Connection getConnection() throws Exception {
		Context init = new InitialContext();

		DataSource ds = 
				(DataSource)init.lookup("java:comp/env/jdbc/bijou");

		con = ds.getConnection();
		System.out.println("DB연결 성공");
		return con;
	}
	
	
	
	public void CloseDB() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
			System.out.println("자원해제 성공");
		} catch (SQLException e) {
			System.out.println("자원해제 실패");
			e.printStackTrace();
		}
	} // CloseDB()

	
	// ---------------------------------------------------

	// 아이디 중복체크
	public int idCheck(String member_id) {
		int check = 0;
		
		try {
			con = getConnection();

			sql = "select * from member where member_id=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				check = 1;
			}else{
				check = 0;
			}
			
		} catch (Exception e) {
			System.out.println("memberDAO의 idCheck메소드 오류");
		} finally {
			CloseDB();
		}
		
		return check;
		
	} // idCheck
	
	// 회원가입
	public void insertMember(memberVO vo) {
		try {
			con = getConnection();
			sql = "insert into member(member_id, member_pw, member_name, member_phone,"
					+ " member_post, member_address1, member_address2, member_address3)"
					+ " values(?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getMember_id());
			pstmt.setString(2, vo.getMember_pw());
			pstmt.setString(3, vo.getMember_name());
			pstmt.setString(4, vo.getMember_phone());
			pstmt.setString(5, vo.getMember_post());
			pstmt.setString(6, vo.getMember_address1());
			pstmt.setString(7, vo.getMember_address2());
			pstmt.setString(8, vo.getMember_address3());
			
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("memberDAO의 insertMember메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // insertMember
	
	// 로그인
	public int loginMember(String member_id, String member_pw) {
		int check = -1;

		try {
			con = getConnection();
			
			sql = "select * from member where member_id=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){ // 아이디 있음
				if(member_pw.equals(rs.getString("member_pw"))){ // 비밀번호 같음
					
					check = 1;
					
				} else { // 비밀번호 오류
					
					check = 0;
				}
				
			} else { // 아이디 없음
				check = -1;
			}
		} catch (Exception e) {
			System.out.println("memberDAO의 loginMember메소드 오류");
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		
		return check;
	} // login
	
	
	// 회원 찾기
	public memberVO findMember(String member_id) {
		memberVO vo = null;
		
		try {
			con = getConnection();
			sql = "select * from member where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new memberVO();
				vo.setMember_num(rs.getInt("member_num"));
				vo.setMember_id(member_id);
				vo.setMember_pw(rs.getString("member_pw"));
				vo.setMember_name(rs.getString("member_name"));
				vo.setMember_phone(rs.getString("member_phone"));
				vo.setMember_post(rs.getString("member_post"));
				vo.setMember_address1(rs.getString("member_address1"));
				vo.setMember_address2(rs.getString("member_address2"));
				vo.setMember_address3(rs.getString("member_address3"));
				vo.setMember_rating(rs.getString("member_rating"));
				vo.setMember_point(rs.getInt("member_point"));
			}
			
		} catch (Exception e) {
			System.out.println("memberDAO의 findMember메소드 오류");
		} finally {
			CloseDB();
		}
		
		return vo;
	} // findMember
		

	// 회원수정
	public int modifyMember(memberVO vo2, String member_pw_n) {
		int check = 0;
		try {
			con = getConnection();

			sql = "select member_pw from member where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo2.getMember_id());
			rs = pstmt.executeQuery();
			
			System.out.println(member_pw_n);

			if(rs.next()){
				if(vo2.getMember_pw().equals(rs.getString("member_pw"))){
					check = 1;
					sql = "update member"
							+ " set member_pw=?, member_name=?, member_phone=?, member_post=?,"
							+ " member_address1=?, member_address2=?, member_address3=?"
							+ " where member_id=?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, member_pw_n);
					pstmt.setString(2, vo2.getMember_name());
					pstmt.setString(3, vo2.getMember_phone());
					pstmt.setString(4, vo2.getMember_post());
					pstmt.setString(5, vo2.getMember_address1());
					pstmt.setString(6, vo2.getMember_address2());
					pstmt.setString(7, vo2.getMember_address3());
					pstmt.setString(8, vo2.getMember_id());
					pstmt.executeUpdate();	
				} else {
					check = 0;
				}
			}
		} catch (Exception e) {
			System.out.println("memberDAO의 modifyMember메소드 오류");
		} finally {
			CloseDB();
		}
		
		return check;
	} // modifyMember


	// 탈퇴할 회원 찾기
	public String deleteMemberCheck(String member_id) {
		String member_pw = "";
		
		try {
			con = getConnection();
			sql = "select member_pw from member where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member_pw = rs.getString("member_pw");
			}
			
		} catch (Exception e) {
			System.out.println("memberDAO의 deleteMemberCheck메소드 오류");
		} finally {
			CloseDB();
		}
		
		return member_pw;
	} // deleteMemberCheck


	// 회원 탈퇴 & 회원관리_삭제
	public void deleteMember(String member_id) {
		try {
			con = getConnection();
			sql = "delete from member where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("memberDAO의 deleteMember메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // deleteMember


	// 회원관리_조회
	public List<memberVO> searchAllMember() {
		List<memberVO> list = new ArrayList<memberVO>();
		
		try{
			con = getConnection();
			
			sql = "select * from member order by 1";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				String member_id = rs.getString("member_id");
				String member_pw = rs.getString("member_pw");
				String member_name = rs.getString("member_name");
				String member_phone = rs.getString("member_phone");
				String member_post = rs.getString("member_post");
				String member_address1 = rs.getString("member_address1");
				String member_address2 = rs.getString("member_address2");
				String member_address3 = rs.getString("member_address3");
				String member_rating = rs.getString("member_rating");
				int member_point = rs.getInt("member_point");
				
				memberVO vo = new memberVO();
				vo.setMember_id(member_id);
				vo.setMember_pw(member_pw);
				vo.setMember_name(member_name);
				vo.setMember_phone(member_phone);
				vo.setMember_post(member_post);
				vo.setMember_address1(member_address1);
				vo.setMember_address2(member_address2);
				vo.setMember_address3(member_address3);
				vo.setMember_rating(member_rating);
				vo.setMember_point(member_point);
				
				list.add(vo);
				
			} //while
			
		} catch (Exception e) {
			System.out.println("memberDAO의 searchAllMember메소드 오류");
		} finally {
			CloseDB();
		}
		
		return list;
		
	} // searchAllMember


	// 회원관리_수정
	public void adminUpdateMember(memberVO vo3) {
		try {
			con = getConnection();
			sql = "update member set member_pw=?, member_name=?, member_phone=?,"
					+ " member_post=?, member_address1=?, member_address2=?, member_address3=?,"
					+ " member_rating=?, member_point=?"
					+ " where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo3.getMember_pw());
			pstmt.setString(2, vo3.getMember_name());
			pstmt.setString(3, vo3.getMember_phone());
			pstmt.setString(4, vo3.getMember_post());
			pstmt.setString(5, vo3.getMember_address1());
			pstmt.setString(6, vo3.getMember_address2());
			pstmt.setString(7, vo3.getMember_address3());
			pstmt.setString(8, vo3.getMember_rating());
			pstmt.setInt(9, vo3.getMember_point());
			pstmt.setString(10, vo3.getMember_id());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("memberDAO의 adminUpdateMember메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // adminUpdateMember
	
	// ---------------------------------------------------

	// 회원번호 가져오기
	public int getMemberNum(String member_id) {
		int member_num = 0;
		
		try {
			con = getConnection();
			sql = "select member_num from member where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member_num = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("memberDAO의 getMemberNum메소드 오류");
		} finally {
			CloseDB();
		}
		
		return member_num;
	} // getMemberNum

	// 사용하고 남은 포인트 + 적립 포인트 저장
	public void updatePoint(int member_num, int member_point) {
		try {
			con = getConnection();
			sql = "update member set member_point=? where member_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_point);
			pstmt.setInt(2, member_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("memberDAO의 updatePoint메소드 오류");
		} finally {
			CloseDB();
		}
	} // updatePoint


	// 회원번호와 회원이름
	public List<memberVO> selectMemberInfo() {
		List<memberVO> memberList = new ArrayList<memberVO>();
		
		try {
			con = getConnection();
			sql = "select member_num, member_name from member";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memberVO vo = new memberVO();
				
				vo.setMember_num(rs.getInt("member_num"));
				vo.setMember_name(rs.getString("member_name"));
				
				memberList.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("memberDAO의 selectMemberInfo메소드 오류");
		} finally {
			CloseDB();
		}
		return memberList;
	} // selectMemberInfo

	// 후기 작성 포인트
	public void reviewPoint(int member_num, int point) {
		int member_point = 0;
		try {
			con = getConnection();
			sql = "select member_point from member where member_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member_point = rs.getInt("member_point");
			}
			
			member_point += point;
			
			sql = "update member set member_point=? where member_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_point);
			pstmt.setInt(2, member_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("memberDAO의 reviewPoint메소드 오류");
		} finally {
			CloseDB();
		}
	} // reviewPoint

	
}
