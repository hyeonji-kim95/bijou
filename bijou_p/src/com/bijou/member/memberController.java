package com.bijou.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/member/*")
public class memberController extends HttpServlet {
	
	memberService service;
	
	@Override
	public void init() throws ServletException {
		service = new memberService();
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
			if (action == null || action.equals("/login_go.do")) { // 로그인하지 않았거나 로그인을 클릭했을 경우
				// 로그인 페이지로 이동
				nextPage = "/b_member/login.jsp";
				
			} else if(action.equals("/join_go.do")) { // 회원가입을 클릭했을 경우
				// 회원가입 페이지로 이동
				nextPage = "/b_member/join.jsp";
			} else if(action.equals("/idCheck.do")) { // 아이디 중복체크
				service.idCheck(request, response);
				
				return;
				
			} else if (action.equals("/join.do")) { // 회원 가입
				service.join(request, response);
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "alert('환영합니다^^');" + "location.href='" + request.getContextPath()
							+ "/member/login_go.do';" + "</script>");
				
				return;
				
			} else if (action.equals("/login.do")) { // 로그인
				service.loginMember(request, response);
				
				return;
				
			} else if(action.equals("/logout.do")) { // 로그아웃
				session.invalidate();
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "alert('로그아웃 성공');" + "location.href='" + request.getContextPath() + "/main/index.do';" + "</script>");
				
				return;
			} else if(action.equals("/modify_go.do")) { // 회원수정을 클릭했을 경우
				service.findMember(request, response);
				
				// 회원수정 페이지로 이동
				nextPage = "/b_member/modify.jsp";
				
			} else if(action.equals("/modify.do")) { // 회원 수정
				service.modifyMember(request, response);
				
				return;
				
			} else if(action.equals("/delete.do")) { // 회원탈퇴
				service.deleteMember(request, response);
				
				return;
				
			} else if(action.equals("/admin.do")) { // 회원 관리 페이지로 이동
				service.searchAllMember(request, response);
				
				nextPage = "/b_member/admin.jsp";
				
			} else if(action.equals("/admin_update.do")) { // 회원 관리 수정 사항 저장(1명)
				service.adminUpdateMember(request, response);
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "alert('수정완료');" + "location.href='" + request.getContextPath() + "/member/admin.do';" + "</script>");
				
				return;
				
			} else if(action.equals("/admin_delete.do")) { // 회원 삭제
				service.deleteMember_admin(request, response);
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "alert('삭제완료');" + "location.href='" + request.getContextPath() + "/member/admin.do';" + "</script>");
				
				return;
				
			}

			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
			
		} catch (Exception e) {
			System.out.println("memberController 오류");
		}
		
		
	} // doHandle
	
}
