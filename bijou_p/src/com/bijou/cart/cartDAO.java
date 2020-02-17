package com.bijou.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class cartDAO {
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
	
	

	// 장바구니에 동일한 상품이 있는지 체크
	public int checkCart(int member_num, int product_num) {
		int check = 0;
		try {
			con = getConnection();
			sql = "select * from cart where member_num=? and product_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			pstmt.setInt(2, product_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				check = 1; // 장바구니에 동일 상품이 있음
			}
			
		} catch (Exception e) {
			System.out.println("cartDAO의 checkCart메소드 오류");
		} finally {
			CloseDB();
		}
		return check;
	} // checkCart
	

	// 장바구니 추가
	public void insertCart(int member_num, int product_num, int cart_count, int product_price) {
		int cart_price = product_price*cart_count;
		
		try {
			con = getConnection();
			sql = "insert into cart(member_num, product_num, cart_count, cart_price)"
					+ " values(?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			pstmt.setInt(2, product_num);
			pstmt.setInt(3, cart_count);
			pstmt.setInt(4, cart_price);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("cartDAO의 insertCart메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // insertCart


	// 장바구니 목록
	public List<cartVO> selectCart(int member_num) {
		List<cartVO> listCart = new ArrayList<cartVO>();
		
		try {
			con = getConnection();
			sql = "select * from cart where member_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cartVO vo = new cartVO();
				
				vo.setCart_num(rs.getInt("cart_num"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setCart_count(rs.getInt("cart_count"));
				vo.setCart_price(rs.getInt("cart_price"));
				
				listCart.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("cartDAO의 selectCart메소드 오류");
		} finally {
			CloseDB();
		}
		return listCart;
	} // selectCart
	

	// 장바구니 수정
	public List<cartVO> updateCart(int cart_num, int cart_count, int member_num) {
		List<cartVO> listCart = new ArrayList<cartVO>();
		
		int cart_price_n = 0;
		
		try {
			con = getConnection();
			sql = "select cart_count, cart_price from cart where cart_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cart_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cart_price_n = (rs.getInt("cart_price") / rs.getInt("cart_count")) * cart_count;
			}
			
			//con = getConnection();
			sql = "update cart set cart_count=?, cart_price=? where cart_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cart_count);
			pstmt.setInt(2, cart_price_n);
			pstmt.setInt(3, cart_num);
			
			pstmt.executeUpdate();
			
			// -------------------------------------------
			
			sql = "select * from cart where member_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cartVO vo = new cartVO();
				
				vo.setCart_num(rs.getInt("cart_num"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setCart_count(rs.getInt("cart_count"));
				vo.setCart_price(rs.getInt("cart_price"));
				
				listCart.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("cartDAO의 listCart메소드 오류");
		} finally {
			CloseDB();
		}
		
		return listCart;
	}


	// 장바구니 삭제
	public void deleteCart(int cart_num) {
		try {
			con = getConnection();
			sql = "delete from cart where cart_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cart_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("cartDAO의 deleteCart메소드 오류");
		} finally {
			CloseDB();
		}
	} // deleteCart


	// 장바구니 상품 중 가장 가격이 높은 상품의 가격
	public cartVO maxPrice(int member_num) {
		cartVO vo = new cartVO();
		try {
			con = getConnection();
			sql = "select * from cart where member_num=? and cart_price = (select max(cart_price) from cart where member_num=?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			pstmt.setInt(2, member_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setCart_num(rs.getInt("cart_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setMember_num(member_num);
				vo.setCart_count(rs.getInt("cart_count"));
				vo.setCart_price(rs.getInt("cart_price"));
			}
			
		} catch (Exception e) {
			System.out.println("cartDAO의 maxPrice메소드 오류");
		} finally {
			CloseDB();
		}
		return vo;
		
	} // maxPrice


	// 포인트를 사용한 뒤 새 가격 카트에 저장
	public void updatePrice(int change_num, int price) {
		System.out.println(price);
		try {
			con = getConnection();
			sql = "update cart set cart_price=? where product_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, price);
			pstmt.setInt(2, change_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("cartDAO의 updatePrice메소드 오류");
		} finally {
			CloseDB();
		}
	} // updatePrice



	public void deleteCartAll(int member_num) {
		try {
			con = getConnection();
			sql = "delete from cart where member_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("cartDAO의 deleteCartAll메소드 오류");
		} finally {
			CloseDB();
		}
	} // deleteCartAll

	// member_num을 이용해 cartList에 가서 카트에 담은 수량이 제품의 수량을 초과하지 않는지 검사
	public int checkCount(int member_num) {
		int check = 0;
		
		try {
			con = getConnection();
			sql = "select product_num, cart_count from cart where member_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int product_num = rs.getInt("product_num");
				int cart_count = rs.getInt("cart_count");
				
				sql = "select product_count from product where product_num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, product_num);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					int product_count = rs.getInt("product_count");
					
					if(product_count >= cart_count) {
						check = 1;
					} else {
						check = 0;
					}
				}
				
				
			}
			
		} catch (Exception e) {
			System.out.println("cartDAO의 checkCount메소드 오류");
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		
		return check;
		
	} // checkCount



	
}
