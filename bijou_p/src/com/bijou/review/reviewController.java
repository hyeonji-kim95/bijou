package com.bijou.review;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bijou.cart.cartService;
import com.bijou.cart.cartVO;


@WebServlet("/review/*")
public class reviewController extends HttpServlet {
	reviewService service;
	
	@Override
	public void init() throws ServletException {
		service = new reviewService();
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
			if(action.equals("/write_go.do")) { // 리뷰 작성 버튼 클릭
				service.write_go(request, response);
				
				nextPage = "/b_review/review_write.jsp";
				
			} else if(action.equals("/write.do")) { // 리뷰 글 저장
				service.writeReview(request, response);
				
				return;
			
			} else if(action.equals("/content.do")) { // 리뷰 글 내용
				service.contentReview(request, response);
				
				nextPage = "/b_review/review_content.jsp";
				
			} else if(action.equals("/modify.do")) { // 리뷰 글 수정
				service.updateReview(request, response);
				
				return;
				
			} else if(action.equals("/delete.do")) { // 리뷰 글 삭제
				service.deleteReview(request, response);
				
				return;
				
			} else if(action.equals("/re.do")) { // 댓글 추가/수정
				service.reReview(request, response);
				
				return;
				
			}
        	
        	RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
        	
		} catch (Exception e) {
			System.out.println("reviewController 오류");
		}
		
	} // doHandle
}
