package com.bijou.product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.swing.plaf.synth.SynthSeparatorUI;

import com.bijou.notice.noticeVO;

public class productDAO {
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
	

	// 상품목록
	public List<productVO> listProduct() {
		List<productVO> listProduct = new ArrayList<productVO>();
		try {
			con = getConnection();
			sql = "select * from product";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				productVO vo = new productVO();
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setProduct_name(rs.getString("product_name"));
				vo.setProduct_img(rs.getString("product_img"));
				vo.setProduct_count(rs.getInt("product_count"));
				vo.setProduct_price(rs.getInt("product_price"));
				vo.setProduct_content(rs.getString("product_content"));
				vo.setProduct_cimg1(rs.getString("product_cimg1"));
				vo.setProduct_cimg2(rs.getString("product_cimg2"));
				vo.setProduct_cimg3(rs.getString("product_cimg3"));
				
				listProduct.add(vo);
			}//while문
			
		} catch (Exception e) {
			System.out.println("productDAO의 listProduct메소드 오류");
		} finally {
			CloseDB();
		}
		
		return listProduct;
	}


	// 상품 상세보기
	public productVO contentProduct(int product_num) {
		productVO vo = new productVO();
		
		try {
			con = getConnection();
			sql = "select * from product where product_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setProduct_num(product_num);
				vo.setProduct_name(rs.getString("product_name"));
				vo.setProduct_img(rs.getString("product_img"));
				vo.setProduct_count(rs.getInt("product_count"));
				vo.setProduct_price(rs.getInt("product_price"));
				vo.setProduct_content(rs.getString("product_content"));
				vo.setProduct_cimg1(rs.getString("product_cimg1"));
				vo.setProduct_cimg2(rs.getString("product_cimg2"));
				vo.setProduct_cimg3(rs.getString("product_cimg3"));
			}
			
		} catch (Exception e) {
			System.out.println("productDAO의 contentProduct메소드 오류");
		} finally {
			CloseDB();
		}
		
		return vo;
	} // contentProduct


	// Auto_increment확인
	public int getAutoNum() {
		int auto_num = 0;
		
		try {
			con = getConnection();
			sql = "SELECT auto_increment FROM information_schema.tables where table_name = 'product'";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				auto_num = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("productDAO의 getAutoNum메소드 오류");
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		
		return auto_num;
		
	} // getAutoNum


	// 상품등록
	public void insertProduct(productVO vo) {
		try {
			con = getConnection();
			sql = "insert into product(product_name, product_img, product_count, product_price,"
					+ " product_content, product_cimg1, product_cimg2, product_cimg3)"
					+ " values(?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getProduct_name());
			pstmt.setString(2, vo.getProduct_img());
			pstmt.setInt(3, vo.getProduct_count());
			pstmt.setInt(4, vo.getProduct_price());
			pstmt.setString(5, vo.getProduct_content());
			pstmt.setString(6, vo.getProduct_cimg1());
			pstmt.setString(7, vo.getProduct_cimg2());
			pstmt.setString(8, vo.getProduct_cimg3());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("productDAO의 insertProduct메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // insertProduct


	// 상품수정
	public void updateProduct(int product_num, String product_name, int product_price, int product_count) {
		try {
			con = getConnection();
			sql = "update product set product_name=?, product_price=?, product_count=?"
					+ " where product_num=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, product_name);
			pstmt.setInt(2, product_price);
			pstmt.setInt(3, product_count);
			pstmt.setInt(4, product_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("productDAO의 updateProduct메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // updateProduct


	// 상품삭제
	public void deleteProduct(int product_num) {
		try {
			con = getConnection();
			sql = "delete from product where product_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("productDAO의 deleteProduct메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // deleteProduct

	// ---------------------------------------------------

	// 장바구니에 담긴 상품목록
	public List<productVO> productCart(List<Integer> listProductNum) {
		List<productVO> productCart = new ArrayList<productVO>();
		
		try {
			con = getConnection();
			sql = "select product_name, product_img, product_count from product where product_num=?";
			for(int i = 0; i < listProductNum.size(); i++) {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, listProductNum.get(i));
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					productVO vo = new productVO();
					vo.setProduct_num(listProductNum.get(i));
					vo.setProduct_name(rs.getString("product_name"));
					vo.setProduct_img(rs.getString("product_img"));
					vo.setProduct_count(rs.getInt("product_count"));
					
					productCart.add(vo);
				}
			}
			
		} catch (Exception e) {
			System.out.println("productDAO의 productCart메소드 오류");
		} finally {
			CloseDB();
		}
		
		return productCart;
	} // productCart

	// 주문 후 상품 수량 변경
	public void orderProduct(int product_num, int oorder_count) {
		int product_count = 0;
		try {
			con = getConnection();
			sql = "select product_count from product where product_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product_count = rs.getInt("product_count");
			}
			
			product_count -= oorder_count;
			
			sql = "update product set product_count=? where product_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_count);
			pstmt.setInt(2, product_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("productDAO의 orderProduct메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // orderProduct



	
	
	
	
}
