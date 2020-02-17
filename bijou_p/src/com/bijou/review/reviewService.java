package com.bijou.review;

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
import com.bijou.oorder.oorderDAO;
import com.bijou.qna.qnaVO;


public class reviewService {
	reviewDAO dao;
	reviewVO vo;
	
	memberDAO mem_dao;
	
	private static String ARTICLE_IMAGE_REPO = "C:\\bijou\\b_review_img";
	
	//생성자 호출시 qnaDAO객체 생성
	public reviewService() {
		dao = new reviewDAO();
		vo = new reviewVO();
		
		mem_dao = new memberDAO();
	}
	
	// -----------------------------------------------------------------
	
	// 리뷰 작성 버튼 클릭
	public void write_go(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("member_id");
		// 사용자의 회원번호 불러오기
		int member_num = mem_dao.getMemberNum(member_id);
		request.setAttribute("member_num", member_num);
		
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		request.setAttribute("product_num", product_num);
		
		int oorder_num = Integer.parseInt(request.getParameter("oorder_num"));
		request.setAttribute("oorder_num", oorder_num);
		
	}
	
	// 리뷰 글 저장
	public void writeReview(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			HttpSession session = request.getSession();
			String member_id = (String)session.getAttribute("member_id");
			// 사용자의 회원번호 불러오기
			int member_num = mem_dao.getMemberNum(member_id);

			// 글을 추가하기 전에 받아와야 함 -> 안그러면 추가되고 auto값을 구하기 때문에 받아야하는 값 + 1이 나옴
			int auto_num = dao.getAutoNum();
			
			Map<String, String> qnaMap = upload(request, response);
			String review_title = qnaMap.get("review_title");
			String review_content = qnaMap.get("review_content");
			String review_img = qnaMap.get("review_img");
			int product_num = Integer.parseInt(qnaMap.get("product_num"));
			
			dao.writeReview(review_title, review_content, review_img, product_num, member_num);
			
			// 리뷰 작성 후 주문상태를 'completedR'로 변경
			int oorder_num = Integer.parseInt(qnaMap.get("oorder_num"));
			oorderDAO oorder_dao = new oorderDAO();
			oorder_dao.writeReview(oorder_num);
			
			// 일반 후기 : 포인트 +200점 / 포토 후기 : 포인트 +500점
			if(review_img == null) {
				mem_dao.reviewPoint(member_num, 200);
			} else {
				mem_dao.reviewPoint(member_num, 500);
			}
			
			if (review_img != null && review_img.length() != 0) {
				File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + review_img);
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
			System.out.println("reviewService의 writeReview메소드 오류");
		}
		
	}
	
	// 리뷰 글 내용
	public void contentReview(HttpServletRequest request, HttpServletResponse response) {
		int review_num = Integer.parseInt(request.getParameter("review_num"));
		
		vo = dao.contentReview(review_num);
		request.setAttribute("reviewContent", vo);
		
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("member_id");
		int member_num = mem_dao.getMemberNum(member_id);
		request.setAttribute("member_num", member_num);
		
	}
	
	// 리뷰 글 수정
	public void updateReview(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			Map<String, String> map = upload(request, response);
			int review_num = Integer.parseInt(map.get("review_num"));
			String review_title = map.get("review_title");
			String review_content = map.get("review_content");
			String review_img = map.get("review_img");
			
			vo.setReview_num(review_num);
			vo.setReview_title(review_title);
			vo.setReview_content(review_content);
			vo.setReview_img(review_img);
			
			dao.updateContent(vo);
			
			if(review_img != null && review_img.length() != 0) {
				String originalFileName = map.get("originalFileName");
				File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + review_img);
				File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + review_num);
				destDir.mkdirs();
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				File oldFile = new File(ARTICLE_IMAGE_REPO + "\\" + review_num + "\\" + originalFileName);
				oldFile.delete();
			}
			
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('수정완료');" + " location.href='" + request.getContextPath()
			+ "/review/content.do?review_num=" + review_num + "';" + "</script>");
			
			return;

		} catch (Exception e) {
			System.out.println("qnaService의 modifyQna메소드 오류");
		}
	}
	
	// 리뷰 글 삭제
	public void deleteReview(HttpServletRequest request, HttpServletResponse response) {
		try {
			//삭제할 글번호를 request에서 꺼내오기
			int review_num = Integer.parseInt(request.getParameter("review_num"));
			
			dao.deleteContent(review_num);
			
			// 파일 삭제
			File imgDir = new File(ARTICLE_IMAGE_REPO + "\\" + review_num);
			if(imgDir.exists()) {
				FileUtils.deleteDirectory(imgDir);
			}
			
		} catch (Exception e) {
			System.out.println("reviewService의 deleteReview메소드 오류");
		}
	}
	
	// 상품에 해당하는 Review 목록
	public void listReview(HttpServletRequest request, HttpServletResponse response) {
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		
		List<reviewVO> reviewList = dao.listReview(product_num);
		request.setAttribute("reviewList", reviewList);
		
		// 회원정보 넘겨주기
		List<memberVO> memberList = mem_dao.selectMemberInfo();
				
		request.setAttribute("memberList", memberList);
	}
	
	// 댓글 추가/수정
	public void reReview(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			int review_num = Integer.parseInt(request.getParameter("review_num"));
			String review_re = request.getParameter("review_re");
			
			dao.reReview(review_num, review_re);
			
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('등록완료');" + " location.href='" + request.getContextPath()
			+ "/review/content.do?review_num=" + review_num + "';" + "</script>");
		
		} catch (Exception e) {
			System.out.println("reviewService의 reReview메소드 오류");
		}
		
	}
	
	// -----------------------------------------------------------------
	
	// 파일 업로드
	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> reviewMap = new HashMap<String, String>();
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
					reviewMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
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
						reviewMap.put(fileItem.getFieldName(), fileName);  
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName);
						fileItem.write(uploadFile);

					} // end if
				} // end if
			} // end for
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reviewMap;
		
	} // upload

	// -----------------------------------------------------------------

}
