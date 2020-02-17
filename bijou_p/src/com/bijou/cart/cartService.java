package com.bijou.cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bijou.member.memberDAO;
import com.bijou.member.memberService;
import com.bijou.product.productDAO;
import com.bijou.product.productService;
import com.bijou.product.productVO;

public class cartService {
	
	cartDAO dao;
	
	public cartService() {
		dao = new cartDAO();
	}

	memberDAO mem_dao = new memberDAO();
	productDAO pro_dao = new productDAO();
	
	List<cartVO> listCart;
	
	// -----------------------------------------------------------------
	
	// 장바구니 추가
	public void insertCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter pw = response.getWriter();
			HttpSession session = request.getSession();
			
			String member_id = (String)session.getAttribute("member_id");
	
			if(member_id == null) {
				pw.print("<script>" + "location.href='" + request.getContextPath()
			+ "/member/login_go.do';" + "</script>");
			} else {
				int member_num = mem_dao.getMemberNum(member_id);
				
				int product_num = Integer.parseInt(request.getParameter("product_num"));
				int cart_count = Integer.parseInt(request.getParameter("oorder_count"));
				int product_price = Integer.parseInt(request.getParameter("product_price"));
				
				// 장바구니에 동일한 상품이 있는지 체크
				int check = dao.checkCart(member_num, product_num);
				if(check == 1) {
					pw.print("<script>" + "  alert('이미 장바구니에 담은 상품입니다.');" + "location.href='" + request.getContextPath()
					+ "/product/content.do?product_num=" + product_num + "';" + "</script>");
				} else {
					dao.insertCart(member_num, product_num, cart_count, product_price);
					
					pw.print("<script>" + "  alert('장바구니 담기 완료');" + "location.href='" + request.getContextPath()
							+ "/product/content.do?product_num=" + product_num + "';" + "</script>");
					}
			} // 큰 if~else
			
			return;
		} catch (IOException e) {
			System.out.println("cartService메소드의 insertCart메소드 오류");
		}

	}
	
	// 장바구니 목록
	public void mycart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("member_id");
		
		// member_id를 이용해 member_num을 가져옴
		int member_num = mem_dao.getMemberNum(member_id);
		
		// member_num을 이용해 cartList를 가져옴
		listCart = dao.selectCart(member_num);

		request.setAttribute("listCart", listCart);
		
		List<Integer> listProductNum = new ArrayList<Integer>();
		for(int i = 0; i < listCart.size(); i++) {
			listProductNum.add(listCart.get(i).getProduct_num());
		}
		
		List<productVO> productCart = new ArrayList<productVO>();
		productCart = pro_dao.productCart(listProductNum);
		
		request.setAttribute("productCart", productCart);
		
	}
	
	// 장바구니 수정
	public void updateCart(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			HttpSession session = request.getSession();
			String member_id = (String)session.getAttribute("member_id");
			int cart_num = Integer.parseInt(request.getParameter("cart_num"));
			int cart_count = Integer.parseInt(request.getParameter("cart_count"));
			
			int member_num = mem_dao.getMemberNum(member_id);
			
			listCart = dao.updateCart(cart_num, cart_count, member_num);
			
			request.setAttribute("listCart", listCart);
			

		
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('수정완료');" + "location.href='" + request.getContextPath() + "/cart/mycart.do';" + "</script>");
			
			return;
			
		} catch (IOException e) {
			System.out.println("cartService메소드의 updateCart메소드 오류");
		}
		
	}
	
	// 장바구니 삭제
	public void deleteCart(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			HttpSession session = request.getSession();
			String member_id = (String)session.getAttribute("member_id");
			int cart_num = Integer.parseInt(request.getParameter("cart_num"));
			
			dao.deleteCart(cart_num);
		
		
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('삭제완료');" + "location.href='" + request.getContextPath()
			+ "/cart/mycart.do';" + "</script>");
			
			return;
			
		} catch (IOException e) {
			System.out.println("cartService메소드의 deleteCart메소드 오류");
		}
		
	}
	

	
}
