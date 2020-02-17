package com.bijou.product;

import java.io.IOException;
import java.io.PrintWriter;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bijou.qna.qnaService;



@WebServlet("/product/*")
public class productController extends HttpServlet {
	productService service;

	
	@Override
	public void init() throws ServletException {
		service = new productService();
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
		
		HttpSession session = request.getSession();
		
		String action = request.getPathInfo();
		
        System.out.println(action);
		
        try {
			if(action == null || action.equals("/list.do")) { // 상품목록
				service.listProduct(request, response);
				
				nextPage = "/b_product/product.jsp";
				
			} else if(action.equals("/content.do")) { // 상품 상세보기
				service.contentProduct(request, response);
				
				nextPage = "/b_product/product_content.jsp";
				
			} else if(action.equals("/write_go.do")) { // 상품등록 버튼 클릭
				nextPage = "/b_product/product_write.jsp";
				
			} else if(action.equals("/write.do")) { // 상품등록
				service.insertProduct(request, response);
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('상품등록 완료');" + "location.href='" + request.getContextPath()
						+ "/product/list.do';" + "</script>");

				return;
				
        	} else if(action.equals("/save.do")) { // 상품수정
        		service.updateProduct(request, response);

				return;
        		
			} else if(action.equals("/delete.do")) { // 상품삭제
				service.deleteProduct(request, response);
				
				return;
				
			}
        	
        	RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
        	
		} catch (Exception e) {
			System.out.println("productController 오류");
			e.printStackTrace();
		}
		
	} // doHandle
	
	
	
}
