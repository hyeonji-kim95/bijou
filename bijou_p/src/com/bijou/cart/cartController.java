package com.bijou.cart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/cart/*")
public class cartController extends HttpServlet {
	cartService service;
	
	@Override
	public void init() throws ServletException {
		service = new cartService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String action = request.getPathInfo();
		
        System.out.println(action);
        	
        try {
			if(action == null || action.equals("/cart.do")) { // 장바구니 담기 버튼 클릭
				service.insertCart(request, response);
				
				return;
				
			} else if(action.equals("/mycart.do")) { // 마이페이지 장바구니 확인
				service.mycart(request, response);
				
				nextPage = "/b_cart/cart.jsp";
				
			} else if(action.equals("/modify.do")) { // 장바구니 상품 수량변경
				service.updateCart(request, response);
				
				return;
				
			} else if(action.equals("/delete.do")) { // 장바구니 상품 삭제
				service.deleteCart(request, response);

				return;
			}
        	
        	RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
        	
		} catch (Exception e) {
			System.out.println("cartController 오류");
			e.printStackTrace();
		}
		
	} // doHandle
}
