package com.bijou.qna;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.bijou.member.memberDAO;
import com.bijou.member.memberVO;

public class qnaService {
	qnaDAO dao;
	qnaVO vo;
	
	memberDAO mem_dao;
	
	private static String ARTICLE_IMAGE_REPO = "C:\\bijou\\b_qna_img";
	
	//생성자 호출시 qnaDAO객체 생성
	public qnaService() {
		dao = new qnaDAO();
		vo = new qnaVO();
		
		mem_dao = new memberDAO();
	}
	
	// -----------------------------------------------------------------
	
	// QnA 전체 목록
	public void listQna(HttpServletRequest request, HttpServletResponse response) {
		String _section = request.getParameter("section");
		String _pageNum = request.getParameter("pageNum");
		int section = Integer.parseInt(((_section==null)? "1":_section) );
		int pageNum = Integer.parseInt(((_pageNum==null)? "1":_pageNum));
		
		Map<String, Integer> pagingMap = new HashMap<String, Integer>();
		pagingMap.put("section", section);
		pagingMap.put("pageNum", pageNum);
		
		List<qnaVO> qnaList = dao.listQna(pagingMap);
		request.setAttribute("qnaList", qnaList);
		request.setAttribute("section", section);
		request.setAttribute("pageNum", pageNum);
		
		// 회원정보 넘겨주기
		List<memberVO> memberList = mem_dao.selectMemberInfo();
		
		request.setAttribute("memberList", memberList);
		
		// QnA 전체 글 개수
		int count = dao.countQna();
		request.setAttribute("count", count);
		
	}
	
	// QnA 전체에서 글 쓰기
	public void writeQna(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			String member_id = (String)session.getAttribute("member_id");
			// 사용자의 회원번호 불러오기
			int member_num = mem_dao.getMemberNum(member_id);

			// 글을 추가하기 전에 받아와야 함 -> 안그러면 추가되고 auto값을 구하기 때문에 받아야하는 값 + 1이 나옴
			int auto_num = dao.getAutoNum();
			
			Map<String, String> qnaMap = upload(request, response);
			String qna_title = qnaMap.get("qna_title");
			String qna_content = qnaMap.get("qna_content");
			String qna_img = qnaMap.get("qna_img");
			String qna_pw = qnaMap.get("qna_pw");
			
			dao.writeQna(member_num, qna_title, qna_content, qna_img, qna_pw);
			
			if (qna_img != null && qna_img.length() != 0) {
				File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + qna_img);
				File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + auto_num);
				destDir.mkdirs();
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				srcFile.delete();
			}
		} catch (Exception e) {
			System.out.println("qnaService의 writeQna메소드 오류");
		}
		
	}
	
	// QnA 전체에서 글 보기 전에 비밀번호 검사
	public void pwCheck(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			// 글 번호
			int qna_num = Integer.parseInt(request.getParameter("qna_num"));
			// 입력한 비밀번호
			String qna_pw = request.getParameter("qna_pw");
			
			// 검사
			int check = dao.pwCheck(qna_num, qna_pw);
			
			
			PrintWriter pw = response.getWriter();

			if(check == 1) { // 비밀번호 맞음
				pw.print("<script>" + " location.href='" + request.getContextPath()
				+ "/qna/content.do?qna_num=" + qna_num + "';" + "</script>");
			} else { // 비밀번호 틀림
				pw.print("<script>" + "  alert('비밀번호 오류');" + "history.go(-1);" + "</script>");
			}
			return;

		} catch (Exception e) {
			System.out.println("qnaService의 pwCheck메소드 오류");
		}
		
	}
	
	// QnA 전체에서 글 보기
	public void contentQna(HttpServletRequest request, HttpServletResponse response) {
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		
		vo = dao.contentQna(qna_num);
		request.setAttribute("qnaContent", vo);
		
	}
	
	// 글 수정
	public void modifyQna(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> map = upload(request, response);
			int qna_num = Integer.parseInt(map.get("qna_num"));
			String qna_title = map.get("qna_title");
			String qna_content = map.get("qna_content");
			String qna_img = map.get("qna_img");
			
			vo.setQna_num(qna_num);
			vo.setQna_title(qna_title);
			vo.setQna_content(qna_content);
			vo.setQna_img(qna_img);
			
			dao.updateContent(vo);
			
			if(qna_img != null && qna_img.length() != 0) {
				String originalFileName = map.get("originalFileName");
				File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + qna_img);
				File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + qna_num);
				destDir.mkdirs();
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				File oldFile = new File(ARTICLE_IMAGE_REPO + "\\" + qna_num + "\\" + originalFileName);
				oldFile.delete();
			}
			
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('수정완료');" + " location.href='" + request.getContextPath()
			+ "/qna/content.do?qna_num=" + qna_num + "';" + "</script>");
			
			return;

		} catch (Exception e) {
			System.out.println("qnaService의 modifyQna메소드 오류");
		}
	}
	
	// QnA 글 삭제
	public void deleteQna(HttpServletRequest request, HttpServletResponse response) {
		try {
			//삭제할 글번호를 request에서 꺼내오기
			int qna_num = Integer.parseInt(request.getParameter("qna_num"));
			
			dao.deleteContent(qna_num);
			
			// 파일 삭제
			File imgDir = new File(ARTICLE_IMAGE_REPO + "\\" + qna_num);
			if(imgDir.exists()) {
				FileUtils.deleteDirectory(imgDir);
			}
			
		} catch (Exception e) {
			System.out.println("qnaService의 deleteQna메소드 오류");
		}
		
	}
	
	// 상품에 해당하는 QnA 목록
	public void listProductQna(HttpServletRequest request, HttpServletResponse response) {
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		
		List<qnaVO> qnaList = dao.listProductQna(product_num);
		request.setAttribute("qnaList", qnaList);
		
		// 회원정보 넘겨주기
		List<memberVO> memberList = mem_dao.selectMemberInfo();
				
		request.setAttribute("memberList", memberList);
		
	}
	
	// 상품에 해당하는 QnA 글 등록
	public void writeProductQna(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			String member_id = (String)session.getAttribute("member_id");
			// 사용자의 회원번호 불러오기
			int member_num = mem_dao.getMemberNum(member_id);

			// 글을 추가하기 전에 받아와야 함 -> 안그러면 추가되고 auto값을 구하기 때문에 받아야하는 값 + 1이 나옴
			int auto_num = dao.getAutoNum();
			
			Map<String, String> qnaMap = upload(request, response);
			String qna_title = qnaMap.get("qna_title");
			String qna_content = qnaMap.get("qna_content");
			String qna_img = qnaMap.get("qna_img");
			String qna_pw = qnaMap.get("qna_pw");
			int product_num = Integer.parseInt(qnaMap.get("product_num"));
			
			dao.writeProductQna(member_num, qna_title, qna_content, qna_img, product_num, qna_pw);
			
			if (qna_img != null && qna_img.length() != 0) {
				File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + qna_img);
				File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + auto_num);
				destDir.mkdirs();
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				srcFile.delete();
			}
			
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('등록완료');" + " location.href='" + request.getContextPath()
			+ "/product/content.do?product_num=" + product_num + "';" + "</script>");
			
			return;
			
		} catch (Exception e) {
			System.out.println("qnaService의 writeProductQna메소드 오류");
		}
		
	}
	
	// 댓글 추가/수정
	public void reQna(HttpServletRequest request, HttpServletResponse response) {
		try {
			int qna_num = Integer.parseInt(request.getParameter("qna_num"));
			String qna_re = request.getParameter("qna_re");
			
			dao.reQna(qna_num, qna_re);
			
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('등록완료');" + " location.href='" + request.getContextPath()
			+ "/qna/content.do?qna_num=" + qna_num + "';" + "</script>");
		
		} catch (Exception e) {
			System.out.println("qnaService의 reQna메소드 오류");
		}
		
	}
	
	// -----------------------------------------------------------------
	
	// 파일 업로드
	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> qnaMap = new HashMap<String, String>();
		String encoding = "utf-8";
		File currentDirPath = new File(ARTICLE_IMAGE_REPO);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				if (fileItem.isFormField()) {
					qnaMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				} else {
					System.out.println("파라미터명:" + fileItem.getFieldName());
					System.out.println("파일크기:" + fileItem.getSize() + "bytes");
					
					if (fileItem.getSize() > 0) {
						int idx = fileItem.getName().lastIndexOf("\\");
						
						if (idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}

						String fileName = fileItem.getName().substring(idx + 1);
						
						//익스플로러에서 업로드 파일의 경로 제거 후 map에 파일명 저장
						qnaMap.put(fileItem.getFieldName(), fileName);  
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName);
						fileItem.write(uploadFile);

					} // end if
				} // end if
			} // end for
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return qnaMap;
		
	} // upload

	// -----------------------------------------------------------------
		
	
}
