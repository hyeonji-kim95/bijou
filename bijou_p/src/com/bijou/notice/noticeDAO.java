package com.bijou.notice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class noticeDAO {
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
	} // getConnection
	
	
	
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
	
	
	// 글 목록
	public List<noticeVO> listNotice() {
		List<noticeVO> listNotice = new ArrayList<noticeVO>();
		
		try {
			con = getConnection();
			
			sql = "select * from notice";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int notice_num = rs.getInt("notice_num");
				String notice_title = rs.getString("notice_title");
				String notice_img = rs.getString("notice_img");
				int notice_readcount = rs.getInt("notice_readcount");
				Date notice_date = rs.getDate("notice_date");
				
				
				noticeVO vo = new noticeVO();
				vo.setNotice_num(notice_num);
				vo.setNotice_title(notice_title);
				vo.setNotice_img(notice_img);
				vo.setNotice_readcount(notice_readcount);
				vo.setNotice_date(notice_date);
				
				listNotice.add(vo);
			}//while문
			
		} catch (Exception e) {
			System.out.println("noticeDAO의 listNotice메소드 오류");
		} finally {
			CloseDB();
		}
		
		return listNotice;
	} // listNotice
	
	// 글 개수
	public int countNotice() {
		int total = 0;
		
		try {
			con = getConnection();
			sql = "select count(*) from notice";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt("count(*)");
			}
		} catch (Exception e) {
			System.out.println("noticeDAO의 countNotice메소드 오류");
		} finally {
			CloseDB();
		}
		return total;
	} // countNotice
	
	
	// Auto_increment확인
	public int getAutoNum() {
		int auto_num = 0;
				
		try {
			con = getConnection();
			sql = "SELECT auto_increment FROM information_schema.tables where table_name = 'notice'";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				auto_num = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("noticeDAO의 getAutoNum메소드 오류");
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		
		return auto_num;
	} // getAutoNum

	
	// 글 추가
	public void insertNotice(noticeVO vo) {
		try {
			con = getConnection();
			sql = "insert into notice(notice_title, notice_content, notice_img)"
					+ "values(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getNotice_title());
			pstmt.setString(2, vo.getNotice_content());
			pstmt.setString(3, vo.getNotice_img());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("noticeDAO의 insertNotice메소드 오류");
		} finally {
			CloseDB();
		}

	} // insertNotice

	
	// 글 내용
	public noticeVO noticeContent(int notice_num) {
		noticeVO vo = new noticeVO();
		int notice_readcount = 0;
		
		try {
			con = getConnection();
			sql = "select * from notice where notice_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setNotice_num(rs.getInt("notice_num"));
				vo.setNotice_title(rs.getString("notice_title"));
				vo.setNotice_content(rs.getString("notice_content"));
				vo.setNotice_img(rs.getString("notice_img"));
				vo.setNotice_date(rs.getDate("notice_date"));
				notice_readcount = rs.getInt("notice_readcount");
				vo.setNotice_readcount(notice_readcount);
			}
			
			sql = "update notice set notice_readcount=? where notice_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_readcount += 1);
			pstmt.setInt(2, notice_num);
			
			pstmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return vo;
	} // noticeContent

	
	// 글 삭제
	public void deleteContent(int notice_num) {
		try {
			con = getConnection();
			sql = "delete from notice where notice_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("noticeDAO의 deleteContent메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // deleteContent


	// 글 수정
	public void updateContent(noticeVO vo) {
		int notice_num = vo.getNotice_num();
		String notice_title = vo.getNotice_title();
		String notice_content = vo.getNotice_content();
		String notice_img = vo.getNotice_img();
		
		try {
			con = getConnection();
			sql = "update notice set notice_title=?, notice_content=? ";
					if(notice_img != null && notice_img.length() != 0) {
						sql += ", notice_img=?";
					}
					sql += " where notice_num=?";
					
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, notice_title);
			pstmt.setString(2, notice_content);
			if(notice_img != null && notice_img.length() != 0) {
				pstmt.setString(3, notice_img);
				pstmt.setInt(4, notice_num);
			} else {
				pstmt.setInt(3, notice_num);
			}
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("noticeDAO의 updateContent메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // updateContent


	// 글 검색
	public List<noticeVO> searchNotice(String notice_keyword) {
		List<noticeVO> listNotice = new ArrayList<noticeVO>();
		
		try {
			con = getConnection();
			
			sql = "select notice_num, notice_title, notice_date, notice_readcount from notice"
					+ " where notice_title like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + notice_keyword + "%");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int notice_num = rs.getInt("notice_num");
				String notice_title = rs.getString("notice_title");
				int notice_readcount = rs.getInt("notice_readcount");
				Date notice_date = rs.getDate("notice_date");
				
				
				noticeVO vo = new noticeVO();
				vo.setNotice_num(notice_num);
				vo.setNotice_title(notice_title);
				vo.setNotice_readcount(notice_readcount);
				vo.setNotice_date(notice_date);
				
				listNotice.add(vo);
			}//while문
			
		} catch (Exception e) {
			System.out.println("noticeDAO의 listNotice메소드 오류");
		} finally {
			CloseDB();
		}
		
		return listNotice;
		
	} // searchNotice







	
}
