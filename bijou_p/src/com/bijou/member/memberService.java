package com.bijou.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class memberService {
	memberDAO dao;
	
	//생성자 호출시 memberDAO객체 생성
	public memberService() {
		dao = new memberDAO();
	}
	
	// -----------------------------------------------------------------
	
	// 아이디 중복체크
	public void idCheck(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			String member_id = request.getParameter("member_id");
			
			int check = dao.idCheck(member_id);
			
			PrintWriter out = response.getWriter();
			
			if(check == 1) {
				out.print("not_usable");
			} else {
				out.print("usable");
			}
		} catch (IOException e) {
			System.out.println("memberService의 idCheck메소드 오류");
		}
	}
	
	// 회원가입
	public void join(HttpServletRequest request, HttpServletResponse response) {
		String member_id = request.getParameter("member_id");
		String member_pw = request.getParameter("member_pw");
		String member_name = request.getParameter("member_name");
		String member_phone = request.getParameter("member_phone");
		String member_post = request.getParameter("member_post");
		String member_address1 = request.getParameter("member_address1");
		String member_address2 = request.getParameter("member_address2");
		String member_address3 = request.getParameter("member_address3");
		
		//memberVO에 저장
		memberVO vo2 = new memberVO(member_id, member_pw, member_name, member_phone, member_post, member_address1, member_address2, member_address3);	
		//요청된 회원 정보를 DB의 테이블에 insert하기 위해 메소드 호출
		dao.insertMember(vo2);

		
	}
	
	// 로그인
	public void loginMember(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			HttpSession session = request.getSession();
			
			String member_id = request.getParameter("member_id");
			String member_pw = request.getParameter("member_pw");
			
			int check = dao.loginMember(member_id, member_pw);
			
		
			PrintWriter pw = response.getWriter();
			
			if(check == 1) { // 로그인 성공
				session.setAttribute("member_id", member_id);
				
				pw.print("<script>" + "alert('로그인 성공');" + "location.href='" + request.getContextPath() + "/main/index.do';" + "</script>");
			} else if(check == 0) { // 비밀번호 틀림
				pw.print("<script>" + "alert('비밀번호 오류');" + "location.href='" + request.getContextPath()
								+ "/member/login_go.do';" + "</script>");
			} else { // 아이디 없음
				pw.print("<script>" + "alert('아이디 없음');" + "location.href='" + request.getContextPath()
								+ "/member/login_go.do';" + "</script>");
			}
			return;
		} catch (IOException e) {
			System.out.println("memberService의 loginMember메소드 오류");
		}
		
	}
	
	// 회원찾기
	public void findMember(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		
		String member_id = (String)session.getAttribute("member_id");
		memberVO vo = dao.findMember(member_id);
		
		request.setAttribute("member_name", vo.getMember_name());
		request.setAttribute("member_phone", vo.getMember_phone());
		request.setAttribute("member_post", vo.getMember_post());
		request.setAttribute("member_address1", vo.getMember_address1());
		request.setAttribute("member_address2", vo.getMember_address2());
		request.setAttribute("member_address3", vo.getMember_address3());
		request.setAttribute("member_rating", vo.getMember_rating());
		request.setAttribute("member_point", vo.getMember_point());
	}

	// 회원수정
	public void modifyMember(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			String member_id = request.getParameter("member_id");
			String member_pw = request.getParameter("member_pw");
			String member_pw_n = request.getParameter("member_pw_n");
			String member_name = request.getParameter("member_name");
			String member_phone = request.getParameter("member_phone");
			String member_post = request.getParameter("member_post");
			String member_address1 = request.getParameter("member_address1");
			String member_address2 = request.getParameter("member_address2");
			String member_address3 = request.getParameter("member_address3");
			
			//memberVO에 저장
			memberVO vo2 = new memberVO(member_id, member_pw, member_name, member_phone, member_post, member_address1, member_address2, member_address3);	
			//요청된 회원 정보를 DB의 테이블에 insert하기 위해 메소드 호출
			int check = dao.modifyMember(vo2, member_pw_n);
			
		
			PrintWriter pw = response.getWriter();
			
			if(check == 1) { // 기존 비밀번호가 맞으면
				pw.print("<script>" + "alert('수정완료');" + " location.href='" + request.getContextPath() + "/main/index.do';" + "</script>");
			} else { // 기존 비밀번호가 틀리면
				pw.print("<script>" + "alert('비밀번호 오류');" + " location.href='" + request.getContextPath()
				+ "/member/modify_go.do';" + "</script>");
			}
		} catch (IOException e) {
			System.out.println("memberService의 modifyMember메소드 오류");
		}
		
		
	}

	// 회원탈퇴
	public void deleteMember(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			HttpSession session = request.getSession();
			
			String member_id = request.getParameter("member_id");
			String member_pw = request.getParameter("member_pw");
			
			String pw = dao.deleteMemberCheck(member_id);
			
			PrintWriter pw2 = response.getWriter();
			
			if(pw.equals(member_pw)) { // 비밀번호 맞으면 탈퇴처리
				dao.deleteMember(member_id);
				session.invalidate();
				pw2.print("<script>" + "alert('탈퇴완료');" + " location.href='" + request.getContextPath() + "/main/index.do';" + "</script>");
			} else { // 탈퇴안됨
				pw2.print("<script>" + "alert('비밀번호 오류');" + " location.href='" + request.getContextPath()
				+ "/member/modify_go.do';" + "</script>");
			}
			
			return;
		} catch (IOException e) {
			System.out.println("memberService의 deleteMember메소드 오류");
		}
		
	}

	// 회원관리_조회
	public void searchAllMember(HttpServletRequest request, HttpServletResponse response) {
		List<memberVO> memberList;
		memberList = dao.searchAllMember();
		request.setAttribute("memberList", memberList);
	}

	// 회원관리_수정
	public void adminUpdateMember(HttpServletRequest request, HttpServletResponse response) {
		String member_id = request.getParameter("member_id");
		String member_pw = request.getParameter("member_pw");
		String member_name = request.getParameter("member_name");
		String member_phone = request.getParameter("member_phone");
		String member_post = request.getParameter("member_post");
		String member_address1 = request.getParameter("member_address1");
		String member_address2 = request.getParameter("member_address2");
		String member_address3 = request.getParameter("member_address3");
		String member_rating = request.getParameter("member_rating");
		int member_point = Integer.parseInt(request.getParameter("member_point"));
		
		
		memberVO vo3 = new memberVO(member_id, member_pw, member_name, member_phone, member_post, member_address1, member_address2, member_address3, member_rating, member_point);	
		
		dao.adminUpdateMember(vo3);
	}
	
	// 회원관리_삭제
	public void deleteMember_admin(HttpServletRequest request, HttpServletResponse response) {
		String member_id = request.getParameter("member_id");
		
		dao.deleteMember(member_id);
	}

	// -----------------------------------------------------------------

}	
