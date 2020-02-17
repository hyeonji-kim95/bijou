package com.bijou.qna;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bijou.cart.cartService;
import com.bijou.cart.cartVO;


@WebServlet("/qna/*")
public class qnaController extends HttpServlet {
	qnaService service;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		service = new qnaService();
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
			if(action == null || action.equals("/list.do")) { // QnA 전체 목록
				service.listQna(request, response);
				
				nextPage = "/b_qna/qna.jsp";
				
			} else if(action.equals("/write_go.do")) { // QnA 전체에서 글 쓰기 버튼 클릭
				nextPage = "/b_qna/qna_write.jsp";
				
			} else if(action.equals("/write.do")) { // QnA 전체에서 글 등록
				service.writeQna(request, response);
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('글 작성 완료');" + "location.href='" + request.getContextPath()
						+ "/qna/list.do';" + "</script>");

				return;
				
			} else if(action.equals("/content_s_go.do")) { // QnA 글 보기 전에 비밀번호 검사 페이지 이동
				int qna_num = Integer.parseInt(request.getParameter("qna_num"));
				request.setAttribute("qna_num", qna_num);
				
				nextPage = "/b_qna/qna_pwcheck.jsp";
			
			} else if(action.equals("/content_s.do")) { // QnA 전체에서 글 보기 전에 비밀번호 검사
				service.pwCheck(request, response);
				
				return;
				
        	} else if(action.equals("/content.do")) { // QnA 전체에서 글 보기
				service.contentQna(request, response);
				
				nextPage = "/b_qna/qna_content.jsp";
				
			} else if(action.equals("/modify.do")) { // QnA 글 수정
				service.modifyQna(request, response);
				
				return;
				
			} else if(action.equals("/delete.do")) { // QnA 글 삭제
				service.deleteQna(request, response);
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('삭제완료');" + " location.href='" + request.getContextPath()
				+ "/qna/list.do';" + "</script>");
				
				return;
			
			} else if(action.equals("/re.do")) { // 댓글 추가/수정
				service.reQna(request, response);
				
				return;
				
			} else if(action.equals("/write_go_product.do")) { // 상품에 해당하는 QnA 글 등록 버튼 클릭
				int product_num = Integer.parseInt(request.getParameter("product_num"));
				request.setAttribute("product_num", product_num);
				
				nextPage = "/b_qna/qna_write.jsp";
				
			} else if(action.equals("/write_product.do")) { // 상품에 해당하는 QnA 글 등록
				service.writeProductQna(request, response);
				
				return;
				
			}
			
        	
        	RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
        	
		} catch (Exception e) {
			System.out.println("qnaController 오류");
		}
		
	} // doHandle
}
