package com.bijou.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;


@WebServlet("/notice/*")
public class noticeController extends HttpServlet {
	noticeService service;

	
	@Override
	public void init(ServletConfig config) throws ServletException {
		service = new noticeService();
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
			if (action == null || action.equals("/list.do")) {
				service.listNotice(request, response);
				
				nextPage = "/b_notice/notice.jsp";

			} else if(action.equals("/write.do")) { // 글쓰기 버튼 클릭
				nextPage = "/b_notice/notice_write.jsp";
				
			} else if (action.equals("/addNotice.do")) { // 글 추가
				service.insertNotice(request, response);
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('글 작성 완료');" + "location.href='" + request.getContextPath()
						+ "/notice/list.do';" + "</script>");

				return;
				
			} else if(action.equals("/content.do")) { // 글 상세보기
				service.noticeContent(request, response);
				
				nextPage = "/b_notice/notice_content.jsp";
				
			} else if(action.equals("/delete.do")) { // 글 삭제
				service.deleteContent(request, response);
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('삭제완료');" + " location.href='" + request.getContextPath()
				+ "/notice/list.do';" + "</script>");
				
				return;
				
			} else if(action.equals("/modify.do")) { // 글 수정	
				service.updateContent(request, response);
				
				return;
				
			} else if(action.equals("/search.do")) {
				service.searchNotice(request, response);
				
				nextPage = "/b_notice/notice_search.jsp";
				
			}
			
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
			
		} catch (Exception e) {
			System.out.println("noticeController의 doHandle메소드 오류");
			e.printStackTrace();
		}
	} // doHandle
	
	
	
	
	
}
