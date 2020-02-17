package com.bijou.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class reviewDAO {
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
	
	// Auto_increment확인
	public int getAutoNum() {
		int auto_num = 0;
		
		try {
			con = getConnection();
			sql = "SELECT auto_increment FROM information_schema.tables where table_name = 'review'";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				auto_num = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("reviewDAO의 getAutoNum메소드 오류");
		} finally {
			CloseDB();
		}
		
		return auto_num;
	} // getAutoNum

	// 리뷰 작성
	public void writeReview(String review_title, String review_content, String review_img, int product_num, int member_num) {
		try {
			con = getConnection();
			sql = "insert into review(review_title, review_content, review_img, member_num, product_num)"
					+ " values(?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, review_title);
			pstmt.setString(2, review_content);
			pstmt.setString(3, review_img);
			pstmt.setInt(4, member_num);
			pstmt.setInt(5, product_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("reviewDAO의 writeReview메소드 오류");
		} finally {
			CloseDB();
		}
	} // writeReview

	// 리뷰 글 수정
	public void updateContent(reviewVO vo) {
		try {
			con = getConnection();
			sql = "update review set review_title=?, review_content=? ";
			if(vo.getReview_img() != null && vo.getReview_img().length() != 0) {
				sql += ", review_img=?";
			}
			sql += " where review_num=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getReview_title());
			pstmt.setString(2, vo.getReview_content());
			if(vo.getReview_img() != null && vo.getReview_img().length() != 0) {
				pstmt.setString(3, vo.getReview_img());
				pstmt.setInt(4, vo.getReview_num());
			} else {
				pstmt.setInt(3, vo.getReview_num());
			}
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("reviewDAO의 updateContent메소드 오류");
		} finally {
			CloseDB();
		}
	} // updateContent

	// 리뷰 글 내용
	public reviewVO contentReview(int review_num) {
		reviewVO vo = new reviewVO();
		
		try {
			con = getConnection();
			sql = "select * from review where review_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, review_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setReview_num(review_num);
				vo.setReview_title(rs.getString("review_title"));
				vo.setReview_content(rs.getString("review_content"));
				vo.setReview_img(rs.getString("review_img"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setReview_date(rs.getDate("review_date"));
				vo.setReview_re(rs.getString("review_re"));
			}
			
		} catch (Exception e) {
			System.out.println("reviewDAO의 contentReview메소드 오류");
		} finally {
			CloseDB();
		}
		
		return vo;
		
	} // contentReview


	// 리뷰 글 삭제
	public void deleteContent(int review_num) {
		try {
			con = getConnection();
			sql = "delete from review where review_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, review_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("reviewDAO의 deleteContent메소드 오류");
		} finally {
			CloseDB();
		}
	} // deleteContent

	// 상품에 해당하는 Review 목록
	public List<reviewVO> listReview(int product_num) {
		List<reviewVO> reviewList = new ArrayList<reviewVO>();
		
		try {
			con = getConnection();
			sql = "select review_num, review_title, member_num, review_date, review_re from review where product_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				reviewVO vo = new reviewVO();
				vo.setReview_num(rs.getInt("review_num"));
				vo.setReview_title(rs.getString("review_title"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setProduct_num(product_num);
				vo.setReview_date(rs.getDate("review_date"));
				vo.setReview_re(rs.getString("review_re"));
				
				reviewList.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("reviewDAO의 listReview메소드 오류");
		} finally {
			CloseDB();
		}
		
		return reviewList;
		
	} // listReview

	// 댓글 추가/수정
	public void reReview(int review_num, String review_re) {
		try {
			con = getConnection();
			sql = "update review set review_re=? where review_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, review_re);
			pstmt.setInt(2, review_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("reviewDAO의 reReview메소드 오류");
		} finally {
			CloseDB();
		}
	} // reReview

	
}
