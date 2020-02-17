package com.bijou.oorder;

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

import com.bijou.cart.cartVO;
import com.bijou.product.productDAO;

public class oorderDAO {
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
	
	// 상품페이지에서 주문/결제 완료
	public void insertOrder(oorderVO vo2) {
		try {
			con = getConnection();
			sql = "insert into oorder(product_num, member_num, oorder_name, oorder_phone, oorder_count, oorder_price,"
						+ " oorder_post, oorder_address1, oorder_address2, oorder_address3, oorder_pay)"
					+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo2.getProduct_num());
			pstmt.setInt(2, vo2.getMember_num());
			pstmt.setString(3, vo2.getOorder_name());
			pstmt.setString(4, vo2.getOorder_phone());
			pstmt.setInt(5, vo2.getOorder_count());
			pstmt.setInt(6, vo2.getOorder_price());
			pstmt.setString(7, vo2.getOorder_post());
			pstmt.setString(8, vo2.getOorder_address1());
			pstmt.setString(9, vo2.getOorder_address2());
			pstmt.setString(10, vo2.getOorder_address3());
			pstmt.setString(11, vo2.getOorder_pay());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 insertOrder메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // insertOrder

	// 일반사용자 주문조회
	public List<oorderVO> getMyOrder(int member_num) {
		List<oorderVO> list = new ArrayList<oorderVO>();
		
		try {
			con = getConnection();
			sql = "select * from oorder where member_num=? order by oorder_date desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, member_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				oorderVO vo = new oorderVO();
				vo.setOorder_num(rs.getInt("oorder_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setMember_num(member_num);
				vo.setOorder_name(rs.getString("oorder_name"));
				vo.setOorder_phone(rs.getString("oorder_phone"));
				vo.setOorder_count(rs.getInt("oorder_count"));
				vo.setOorder_price(rs.getInt("oorder_price"));
				vo.setOorder_status(rs.getString("oorder_status"));
				vo.setOorder_post(rs.getString("oorder_post"));
				vo.setOorder_address1(rs.getString("oorder_address1"));
				vo.setOorder_address2(rs.getString("oorder_address2"));
				vo.setOorder_address3(rs.getString("oorder_address3"));
				vo.setOorder_date(rs.getDate("oorder_date"));
				vo.setOorder_transnum(rs.getString("oorder_transnum"));
				vo.setOorder_pay(rs.getString("oorder_pay"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 getMyOrder메소드 오류");
		} finally {
			CloseDB();
		}
		
		return list;
		
	} // getMyOrder

	// 일반사용자 주문취소
	public void cancleMyOrder(int oorder_num) {
		try {
			con = getConnection();
			sql = "update oorder set oorder_status='cancle' where oorder_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, oorder_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 cancleMyOrder메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // cancleMyOrder

	// 관리자 목록 _ 전체
	public List<oorderVO> selectAllOrder() {
		List<oorderVO> list = new ArrayList<oorderVO>();
		
		try {
			con = getConnection();
			sql = "select * from oorder order by oorder_date desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				oorderVO vo = new oorderVO();
				
				vo.setOorder_num(rs.getInt("oorder_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setOorder_name(rs.getString("oorder_name"));
				vo.setOorder_phone(rs.getString("oorder_phone"));
				vo.setOorder_count(rs.getInt("oorder_count"));
				vo.setOorder_price(rs.getInt("oorder_price"));
				vo.setOorder_status(rs.getString("oorder_status"));
				vo.setOorder_post(rs.getString("oorder_post"));
				vo.setOorder_address1(rs.getString("oorder_address1"));
				vo.setOorder_address2(rs.getString("oorder_address2"));
				vo.setOorder_address3(rs.getString("oorder_address3"));
				vo.setOorder_date(rs.getDate("oorder_date"));
				vo.setOorder_transnum(rs.getString("oorder_transnum"));
				vo.setOorder_pay(rs.getString("oorder_pay"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 selectAllOrder메소드 오류");
		} finally {
			CloseDB();
		}
		
		return list;
		
	} // selectAllOrder

	// 관리자모드 '대기(hold)' 목록
	public List<oorderVO> selectHoldOrder() {
		List<oorderVO> list = new ArrayList<oorderVO>();
		
		try {
			con = getConnection();
			sql = "select * from oorder where oorder_status='hold' order by oorder_date desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				oorderVO vo = new oorderVO();
				
				vo.setOorder_num(rs.getInt("oorder_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setOorder_name(rs.getString("oorder_name"));
				vo.setOorder_phone(rs.getString("oorder_phone"));
				vo.setOorder_count(rs.getInt("oorder_count"));
				vo.setOorder_price(rs.getInt("oorder_price"));
				vo.setOorder_status(rs.getString("oorder_status"));
				vo.setOorder_post(rs.getString("oorder_post"));
				vo.setOorder_address1(rs.getString("oorder_address1"));
				vo.setOorder_address2(rs.getString("oorder_address2"));
				vo.setOorder_address3(rs.getString("oorder_address3"));
				vo.setOorder_date(rs.getDate("oorder_date"));
				vo.setOorder_transnum(rs.getString("oorder_transnum"));
				vo.setOorder_pay(rs.getString("oorder_pay"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 selectHoldOrder메소드 오류");
		} finally {
			CloseDB();
		}
		
		return list;
		
	} // selectHoldOrder

	// 관리자모드 '배송 준비중(preparing)' 목록
	public List<oorderVO> selectPreparingOrder() {
		List<oorderVO> list = new ArrayList<oorderVO>();
		
		try {
			con = getConnection();
			sql = "select * from oorder where oorder_status='preparing' order by oorder_date desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				oorderVO vo = new oorderVO();
				
				vo.setOorder_num(rs.getInt("oorder_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setOorder_name(rs.getString("oorder_name"));
				vo.setOorder_phone(rs.getString("oorder_phone"));
				vo.setOorder_count(rs.getInt("oorder_count"));
				vo.setOorder_price(rs.getInt("oorder_price"));
				vo.setOorder_status(rs.getString("oorder_status"));
				vo.setOorder_post(rs.getString("oorder_post"));
				vo.setOorder_address1(rs.getString("oorder_address1"));
				vo.setOorder_address2(rs.getString("oorder_address2"));
				vo.setOorder_address3(rs.getString("oorder_address3"));
				vo.setOorder_date(rs.getDate("oorder_date"));
				vo.setOorder_transnum(rs.getString("oorder_transnum"));
				vo.setOorder_pay(rs.getString("oorder_pay"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 selectPreparingOrder메소드 오류");
		} finally {
			CloseDB();
		}
		
		return list;
		
	} // selectPreparingOrder

	// 관리자모드 '배송중(shipping)' 목록
	public List<oorderVO> selectShippingOrder() {
		List<oorderVO> list = new ArrayList<oorderVO>();
		
		try {
			con = getConnection();
			sql = "select * from oorder where oorder_status='shipping' order by oorder_date desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				oorderVO vo = new oorderVO();
				
				vo.setOorder_num(rs.getInt("oorder_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setOorder_name(rs.getString("oorder_name"));
				vo.setOorder_phone(rs.getString("oorder_phone"));
				vo.setOorder_count(rs.getInt("oorder_count"));
				vo.setOorder_price(rs.getInt("oorder_price"));
				vo.setOorder_status(rs.getString("oorder_status"));
				vo.setOorder_post(rs.getString("oorder_post"));
				vo.setOorder_address1(rs.getString("oorder_address1"));
				vo.setOorder_address2(rs.getString("oorder_address2"));
				vo.setOorder_address3(rs.getString("oorder_address3"));
				vo.setOorder_date(rs.getDate("oorder_date"));
				vo.setOorder_transnum(rs.getString("oorder_transnum"));
				vo.setOorder_pay(rs.getString("oorder_pay"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 selectShippingOrder메소드 오류");
		} finally {
			CloseDB();
		}
		
		return list;
		
	} // selectShippingOrder

	// 관리자모드 '배송완료(completed)' 목록
	public List<oorderVO> selectCompletedOrder() {
		List<oorderVO> list = new ArrayList<oorderVO>();
		
		try {
			con = getConnection();
			sql = "select * from oorder where oorder_status='completed' order by oorder_date desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				oorderVO vo = new oorderVO();
				
				vo.setOorder_num(rs.getInt("oorder_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setOorder_name(rs.getString("oorder_name"));
				vo.setOorder_phone(rs.getString("oorder_phone"));
				vo.setOorder_count(rs.getInt("oorder_count"));
				vo.setOorder_price(rs.getInt("oorder_price"));
				vo.setOorder_status(rs.getString("oorder_status"));
				vo.setOorder_post(rs.getString("oorder_post"));
				vo.setOorder_address1(rs.getString("oorder_address1"));
				vo.setOorder_address2(rs.getString("oorder_address2"));
				vo.setOorder_address3(rs.getString("oorder_address3"));
				vo.setOorder_date(rs.getDate("oorder_date"));
				vo.setOorder_transnum(rs.getString("oorder_transnum"));
				vo.setOorder_pay(rs.getString("oorder_pay"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 selectCompletedOrder메소드 오류");
		} finally {
			CloseDB();
		}
		
		return list;
		
	} // selectCompletedOrder
	
	// 관리자모드 '배송완료 후 리뷰작성완료(completedR)' 목록
	public List<oorderVO> selectCompletedROrder() {
		List<oorderVO> list = new ArrayList<oorderVO>();
		
		try {
			con = getConnection();
			sql = "select * from oorder where oorder_status='completedR' order by oorder_date desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				oorderVO vo = new oorderVO();
				
				vo.setOorder_num(rs.getInt("oorder_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setOorder_name(rs.getString("oorder_name"));
				vo.setOorder_phone(rs.getString("oorder_phone"));
				vo.setOorder_count(rs.getInt("oorder_count"));
				vo.setOorder_price(rs.getInt("oorder_price"));
				vo.setOorder_status(rs.getString("oorder_status"));
				vo.setOorder_post(rs.getString("oorder_post"));
				vo.setOorder_address1(rs.getString("oorder_address1"));
				vo.setOorder_address2(rs.getString("oorder_address2"));
				vo.setOorder_address3(rs.getString("oorder_address3"));
				vo.setOorder_date(rs.getDate("oorder_date"));
				vo.setOorder_transnum(rs.getString("oorder_transnum"));
				vo.setOorder_pay(rs.getString("oorder_pay"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 selectCompletedROrder메소드 오류");
		} finally {
			CloseDB();
		}
		
		return list;
	} // selectCompletedROrder

	// 관리자모드 '주문취소(cancle)' 목록
	public List<oorderVO> selectCancledOrder() {
		List<oorderVO> list = new ArrayList<oorderVO>();
		
		try {
			con = getConnection();
			sql = "select * from oorder where oorder_status='cancle' order by oorder_date desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				oorderVO vo = new oorderVO();
				
				vo.setOorder_num(rs.getInt("oorder_num"));
				vo.setProduct_num(rs.getInt("product_num"));
				vo.setMember_num(rs.getInt("member_num"));
				vo.setOorder_name(rs.getString("oorder_name"));
				vo.setOorder_phone(rs.getString("oorder_phone"));
				vo.setOorder_count(rs.getInt("oorder_count"));
				vo.setOorder_price(rs.getInt("oorder_price"));
				vo.setOorder_status(rs.getString("oorder_status"));
				vo.setOorder_post(rs.getString("oorder_post"));
				vo.setOorder_address1(rs.getString("oorder_address1"));
				vo.setOorder_address2(rs.getString("oorder_address2"));
				vo.setOorder_address3(rs.getString("oorder_address3"));
				vo.setOorder_date(rs.getDate("oorder_date"));
				vo.setOorder_transnum(rs.getString("oorder_transnum"));
				vo.setOorder_pay(rs.getString("oorder_pay"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 selectCancledOrder메소드 오류");
		} finally {
			CloseDB();
		}
		
		return list;
		
	} // selectCancledOrder

	// 관리자모드 주문상태 변경
	public void updateStatus(int oorder_num, String oorder_status) {
		try {
			con = getConnection();
			sql = "update oorder set oorder_status=? where oorder_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, oorder_status);
			pstmt.setInt(2, oorder_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 updateStatus메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // updateStatus

	// 관리자모드 주문삭제
	public void deleteOrder(int oorder_num) {
		try {
			con = getConnection();
			sql = "delete from oorder where oorder_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, oorder_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			CloseDB();
		}
		
	} // deleteOrder

	// 관리자모드 운송번호 등록/변경
	public void updateTransnum(int oorder_num, String oorder_transnum) {
		try {
			con = getConnection();
			sql = "update oorder set oorder_transnum=? where oorder_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, oorder_transnum);
			pstmt.setInt(2, oorder_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 updateTransnum메소드 오류");
		} finally {
			CloseDB();
		}
		
	} // updateTransnum

	// 장바구니 주문 페이지에서 결제완료
	public void payCart(int count, oorderVO vo3) {
		int product_num = 0;
		int cart_count = 0;
		
		try {
			con = getConnection();
			sql = "select product_num, cart_count, cart_price from cart where member_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo3.getMember_num());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sql = "insert into oorder(product_num, member_num, oorder_name, oorder_phone, oorder_count, oorder_price,"
						+ " oorder_post, oorder_address1, oorder_address2, oorder_address3, oorder_pay)"
					+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, rs.getInt("product_num"));
				product_num = rs.getInt("product_num");
				pstmt.setInt(2, vo3.getMember_num());
				pstmt.setString(3, vo3.getOorder_name());
				pstmt.setString(4, vo3.getOorder_phone());
				pstmt.setInt(5, rs.getInt("cart_count"));
				cart_count = rs.getInt("cart_count");
				pstmt.setInt(6, rs.getInt("cart_price"));
				pstmt.setString(7, vo3.getOorder_post());
				pstmt.setString(8, vo3.getOorder_address1());
				pstmt.setString(9, vo3.getOorder_address2());
				pstmt.setString(10, vo3.getOorder_address3());
				pstmt.setString(11, vo3.getOorder_pay());
				
				pstmt.executeUpdate();
				
				// 상품수량 변경
				productDAO pro_dao = new productDAO();
				pro_dao.orderProduct(product_num, cart_count);
			
			}
			
			// 상품수량 변경
			productDAO pro_dao = new productDAO();
			pro_dao.orderProduct(product_num, cart_count);
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 payCart메소드 오류");
		} finally {
			CloseDB();
		}
	} // payCart
	
	// ---------------------------------------------------

	// 리뷰 작성 후 주문상태를 'completedR'로 변경
	public void writeReview(int oorder_num) {
		try {
			con = getConnection();
			sql = "update oorder set oorder_status='completedR' where oorder_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, oorder_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("oorderDAO의 writeReview메소드 오류");
		} finally {
			CloseDB();
		}
	} // writeReview
	
	
}
