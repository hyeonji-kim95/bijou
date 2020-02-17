package com.bijou.oorder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bijou.cart.cartService;
import com.bijou.cart.cartVO;
import com.bijou.member.memberService;
import com.bijou.member.memberVO;
import com.bijou.product.productService;
import com.bijou.product.productVO;


@WebServlet("/oorder/*")
public class oorderController extends HttpServlet {
	oorderService service;
	oorderVO vo;
	
	@Override
	public void init() throws ServletException {
		service = new oorderService();
		vo = new oorderVO();
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
	        if(action == null || action.equals("/product_go.do")) { // 상품 페이지에서 주문버튼 클릭
	        	service.orderProduct(request, response);
	        	
	        	nextPage = "/b_oorder/oorder_product.jsp";
	        	
	        } else if(action.equals("/product.do")) { // 상품 주문페이지에서 결제완료
	        	service.payProduct(request, response);
	        	
	        	PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('결제완료');" + "location.href='" + request.getContextPath()
						+ "/oorder/myorder.do';" + "</script>");

				return;
				
	        } else if(action.equals("/myorder.do")) { // 내 주문 조회
	        	service.myOrder(request, response);
	        	
	        	nextPage = "/b_oorder/oorder_myorder.jsp";
	        	
	        } else if(action.equals("/myorder_cancle.do")) { // 일반사용자 주문취소
	        	service.myOrderCancle(request, response);
	        	
	        	PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('취소완료');" + "location.href='" + request.getContextPath()
						+ "/oorder/myorder.do';" + "</script>");

				return;
				
	        } else if(action.equals("/admin.do")) { // 관리자모드 주문 전체목록
	        	service.selectAllOrder(request, response);
	        	
	        	nextPage = "/b_oorder/oorder_admin.jsp";
	        	
	        } else if(action.equals("/hold.do")) { // 관리자모드 '대기(hold)' 목록
	        	service.selectHoldOrder(request, response);
	        	
	        	nextPage = "/b_oorder/oorder_admin.jsp";
	        	
	        } else if(action.equals("/preparing.do")) { // 관리자모드 '배송 준비중(preparing)' 목록
	        	service.selectPreparingOrder(request, response);
	        	
	        	nextPage = "/b_oorder/oorder_admin.jsp";
	        	
	        } else if(action.equals("/shipping.do")) { // 관리자모드 '배송중(shipping)' 목록
	        	service.selectShippingOrder(request, response);
	        	
	        	nextPage = "/b_oorder/oorder_admin.jsp";
	        	
	        } else if(action.equals("/completed.do")) { // 관리자모드 '배송완료(completed)' 목록
	        	service.selectCompletedOrder(request, response);
	        	
	        	nextPage = "/b_oorder/oorder_admin.jsp";
	        
	        } else if(action.equals("/completedR.do")) { // 관리자모드 '배송완료 후 리뷰작성완료(completedR)' 목록
	        	service.selectCompletedROrder(request, response);
	        	
	        	nextPage = "/b_oorder/oorder_admin.jsp";
	        	
	        } else if(action.equals("/cancle.do")) { // 관리자모드 '주문취소(cancle)' 목록
	        	service.selectCancleOrder(request, response);
	        	
	        	nextPage = "/b_oorder/oorder_admin.jsp";
	        	
	        } else if(action.equals("/status.do")) { // 관리자모드 주문상태 변경
	        	service.updateStatus(request, response);
	        	
	        	PrintWriter pw = response.getWriter();
				pw.print("<script>" + "alert('변경완료');" + "location.href=document.referrer;" + "</script>");
				
				return;
	        	
	        } else if(action.equals("/delete.do")) { // 관리자모드 주문삭제
	        	service.deleteOrder(request, response);
	        	
	        	PrintWriter pw = response.getWriter();
				pw.print("<script>" + "alert('삭제완료');" + "location.href=document.referrer;" + "</script>");
				
				return;
	        	
	        } else if(action.equals("/transnum_go.do")) { // 관리자모드 운송번호 버튼 클릭
	        	nextPage = "/b_oorder/oorder_transnum.jsp";
	        	
	        } else if(action.equals("/transnum.do")) { // 관리자모드 운송번호 등록/변경
	        	service.updateTransnum(request, response);
	        	
	        	return;
	        } else if(action.equals("/cart_go.do")) { // 장바구니에서 주문하기 버튼 클릭
	        	int check = service.orderCart(request, response);
	        	
	        	if(check == 1) { // 초과하지 않는 경우
	        		nextPage = "/b_oorder/oorder_cart.jsp";
	        	} else {
	        		PrintWriter pw = response.getWriter();
					pw.print("<script>" + "  alert('제품 수량 부족. 수량을 체크하거나 쇼핑몰로 전화 부탁드립니다.');" + " location.href='" + request.getContextPath()
					+ "/cart/mycart.do';" + "</script>");
					
					return;
	        	}
	        	
	        } else if(action.equals("/cart.do")) { // 장바구니 주문 페이지에서 결제완료
	        	service.payCart(request, response);
	        	
	        	PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('결제완료');" + "location.href='" + request.getContextPath()
						+ "/oorder/myorder.do';" + "</script>");

				return;
	        }
	        
			
	     	RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
	    	
		} catch (Exception e) {
			System.out.println("oorderController 오류");
			e.printStackTrace();
		}
        
	} // doHandle
}
