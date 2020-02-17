package com.bijou.qna;

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

public class qnaDAO {
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
	
	// QnA 전체 목록
	public List<qnaVO> listQna(Map<String, Integer> pagingMap) {
		List<qnaVO> qnaList = new ArrayList<qnaVO>();
		int section = (Integer)pagingMap.get("section");
		int pageNum = (Integer)pagingMap.get("pageNum");
		
		try{
		   con = getConnection();
		   sql = "select qna_num, qna_title, member_num, product_num, qna_date, qna_re from qna"       
				+ " where qna_num between(?-1) * 100 + (?-1) * 10 + 1 and (?-1) * 100 + ? * 10";
		   
		   pstmt= con.prepareStatement(sql);
		   pstmt.setInt(1, section);
		   pstmt.setInt(2, pageNum);
		   pstmt.setInt(3, section);
		   pstmt.setInt(4, pageNum);
		   
		   rs = pstmt.executeQuery();
		   
		   while(rs.next()){
		      qnaVO vo = new qnaVO();
		      vo.setQna_num(rs.getInt("qna_num"));
		      vo.setQna_title(rs.getString("qna_title"));
		      vo.setMember_num(rs.getInt("member_num"));
		      vo.setProduct_num(rs.getInt("product_num"));
		      vo.setQna_date(rs.getDate("qna_date"));
		      vo.setQna_re(rs.getString("qna_re"));
		      
		      qnaList.add(vo);
		   }

		} catch(Exception e) {
			System.out.println("qnaDAO의 listQna메소드 오류");	
		} finally {
			CloseDB();
		}
		
		return qnaList;
		
	} // listQna

	// QnA 전체 글 개수
	public int countQna() {
		try {
			con = getConnection();
			sql = "select count(qna_num) from qna";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return (rs.getInt(1));
			}
			
		} catch (Exception e) {
			System.out.println("qnaDAO의 countQna메소드 오류");
		} finally {
			CloseDB();
		}
		
		return 0;
		
	} // countQna

	// QnA 전체에서 글 쓰기
	public void writeQna(int member_num, String qna_title, String qna_content, String qna_img, String qna_pw) {
		try {
			con = getConnection();
			sql = "insert into qna(qna_title, qna_content, qna_img, member_num, qna_pw)"
					+ " values(?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, qna_title);
			pstmt.setString(2, qna_content);
			pstmt.setString(3, qna_img);
			pstmt.setInt(4, member_num);
			pstmt.setString(5, qna_pw);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("qnaDAO의 writeQna메소드 오류");
		} finally {
			CloseDB();
		}
	} // writeQna

	// Auto_increment확인
	public int getAutoNum() {
		int auto_num = 0;
		
		try {
			con = getConnection();
			sql = "SELECT auto_increment FROM information_schema.tables where table_name = 'qna'";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				auto_num = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("qnaDAO의 getAutoNum메소드 오류");
		} finally {
			CloseDB();
		}
		
		return auto_num;
	} // getAutoNum

	// QnA 전체에서 글 보기 전에 비밀번호 검사
	public int pwCheck(int qna_num, String qna_pw) {
		int check = 0;
		
		try {
			con = getConnection();
			sql = "select qna_pw from qna where qna_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(qna_pw.equals(rs.getString("qna_pw")))
					check = 1;
			}
			
		} catch (Exception e) {
			System.out.println("qnaDAO의 pwCheck메소드 오류");
		} finally {
			CloseDB();
		}
		
		return check;
		
	} // pwCheck

	// QnA 전체에서 글 보기
	public qnaVO contentQna(int qna_num) {
		qnaVO vo = new qnaVO();
		
		try {
			con = getConnection();
			sql = "select * from qna where qna_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setQna_num(qna_num);
				vo.setQna_title(rs.getString("qna_title"));
				vo.setQna_content(rs.getString("qna_content"));
				vo.setQna_img(rs.getString("qna_img"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setQna_date(rs.getDate("qna_date"));
				vo.setQna_re(rs.getString("qna_re"));
			}
			
		} catch (Exception e) {
			System.out.println("qnaDAO의 contentQna메소드 오류");
		} finally {
			CloseDB();
		}
		
		return vo;
		
	} // contentQna

	// 글 수정
	public void updateContent(qnaVO vo) {
		try {
			con = getConnection();
			sql = "update qna set qna_title=?, qna_content=? ";
			if(vo.getQna_img() != null && vo.getQna_img().length() != 0) {
				sql += ", qna_img=?";
			}
			sql += " where qna_num=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getQna_title());
			pstmt.setString(2, vo.getQna_content());
			if(vo.getQna_img() != null && vo.getQna_img().length() != 0) {
				pstmt.setString(3, vo.getQna_img());
				pstmt.setInt(4, vo.getQna_num());
			} else {
				pstmt.setInt(3, vo.getQna_num());
			}
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("qnaDAO의 updateContent메소드 오류");
		} finally {
			CloseDB();
		}
	} // updateContent

	// QnA 글 삭제
	public void deleteContent(int qna_num) {
		try {
			con = getConnection();
			sql = "delete from qna where qna_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("qnaDAO의 deleteContent메소드 오류");
		} finally {
			CloseDB();
		}
	} // deleteContent

	// 상품에 해당하는 QnA 목록
	public List<qnaVO> listProductQna(int product_num) {
		List<qnaVO> qnaList = new ArrayList<qnaVO>();
		
		try {
			con = getConnection();
			sql = "select qna_num, qna_title, member_num, qna_date, qna_re from qna where product_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				qnaVO vo = new qnaVO();
				vo.setQna_num(rs.getInt("qna_num"));
				vo.setQna_title(rs.getString("qna_title"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setProduct_num(product_num);
				vo.setQna_date(rs.getDate("qna_date"));
				vo.setQna_re(rs.getString("qna_re"));
				
				qnaList.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("qnaDAO의 listProductQna메소드 오류");
		} finally {
			CloseDB();
		}
		
		return qnaList;
		
	} // listProductQna

	// 상품에 해당하는 QnA 글 등록
	public void writeProductQna(int member_num, String qna_title, String qna_content, String qna_img, int product_num, String qna_pw) {
		try {
			con = getConnection();
			sql = "insert into qna(qna_title, qna_content, qna_img, member_num, product_num, qna_pw)"
					+ " values(?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, qna_title);
			pstmt.setString(2, qna_content);
			pstmt.setString(3, qna_img);
			pstmt.setInt(4, member_num);
			pstmt.setInt(5, product_num);
			pstmt.setString(6, qna_pw);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("qnaDAO의 writeProductQna메소드 오류");
		} finally {
			CloseDB();
		}
	} // writeProductQna

	// 댓글 추가/수정
	public void reQna(int qna_num, String qna_re) {
		try {
			con = getConnection();
			sql = "update qna set qna_re=? where qna_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, qna_re);
			pstmt.setInt(2, qna_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("qnaDAO의 reQna메소드 오류");
		} finally {
			CloseDB();
		}
	} // reQna
	
}
