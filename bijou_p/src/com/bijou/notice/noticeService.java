package com.bijou.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

public class noticeService {
	
	noticeDAO dao;
	
	private static String ARTICLE_IMAGE_REPO = "C:\\bijou\\b_notice_img";
	
	public noticeService() {
		dao = new noticeDAO();
	}
	
	List<noticeVO> listNotice;
	
	// -----------------------------------------------------------------

	// 글 목록
	public void listNotice(HttpServletRequest request, HttpServletResponse response) {
		listNotice = dao.listNotice();
		request.setAttribute("listNotice", listNotice);
	}

	// 글 추가
	public void insertNotice(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> noticeMap = upload(request, response);
			String notice_title = noticeMap.get("notice_title");
			String notice_content = noticeMap.get("notice_content");
			String notice_img = noticeMap.get("notice_img");
			
			noticeVO vo = new noticeVO();
			vo.setNotice_title(notice_title);
			vo.setNotice_content(notice_content);
			vo.setNotice_img(notice_img);
			
			int auto_num = dao.getAutoNum();
			
			dao.insertNotice(vo);
		
		
			if (notice_img != null && notice_img.length() != 0) {
				File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + notice_img);
				File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + auto_num);
				destDir.mkdirs();
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				srcFile.delete();
			}
		} catch (Exception e) {
			System.out.println("noticeServiceml insertNotice메소드 오류");
		}
		
	}
		
	// 글 내용
	public void noticeContent(HttpServletRequest request, HttpServletResponse response) {
		int notice_num = Integer.parseInt(request.getParameter("notice_num"));

		noticeVO noticeContent = new noticeVO();
		noticeContent = dao.noticeContent(notice_num);
		
		request.setAttribute("noticeContent", noticeContent);
	}

	// 글 삭제
	public void deleteContent(HttpServletRequest request, HttpServletResponse response) {
		try {
			//삭제할 글번호를 request에서 꺼내오기
			int notice_num = Integer.parseInt(request.getParameter("notice_num"));
			
			dao.deleteContent(notice_num);
			
			// 파일 삭제
			File imgDir = new File(ARTICLE_IMAGE_REPO + "\\" + notice_num);
			if(imgDir.exists()) {
				FileUtils.deleteDirectory(imgDir);
			}
			
			System.out.println(request.getContextPath() + "/notice/list.do");

		} catch (Exception e) {
			System.out.println("noticeService의 deleteContent메소드 오류");
		}
		
	}

	// 글 수정
	public void updateContent(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			Map<String, String> map = upload(request, response);
			int notice_num = Integer.parseInt(map.get("notice_num"));
			
			noticeVO vo = new noticeVO();
			
			vo.setNotice_num(notice_num);
			String notice_title = map.get("notice_title");
			String notice_content = map.get("notice_content");
			String notice_img = map.get("notice_img");
			vo.setNotice_title(notice_title);
			vo.setNotice_content(notice_content);
			vo.setNotice_img(notice_img);
			
			dao.updateContent(vo);
			
			if(notice_img != null && notice_img.length() != 0) {
				String originalFileName = map.get("originalFileName");
				File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + notice_img);
				File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + notice_num);
				destDir.mkdirs();
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				File oldFile = new File(ARTICLE_IMAGE_REPO + "\\" + notice_num + "\\" + originalFileName);
				oldFile.delete();
			}
			
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('수정완료');" + " location.href='" + request.getContextPath()
			+ "/notice/content.do?notice_num=" + notice_num + "';" + "</script>");
			
			return;

		} catch (Exception e) {
			System.out.println("noticeService의 updateContent메소드 오류");
		}
	}

	// 글 검색
	public void searchNotice(HttpServletRequest request, HttpServletResponse response) {
		String notice_keyword = request.getParameter("notice_keyword");
		
		listNotice = dao.searchNotice(notice_keyword);
		request.setAttribute("listNotice", listNotice);
	}
	

	// -----------------------------------------------------------------
	
	// 파일 업로드
	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> noticeMap = new HashMap<String, String>();
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
					noticeMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
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
						noticeMap.put(fileItem.getFieldName(), fileName);  
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName);
						fileItem.write(uploadFile);

					} // end if
				} // end if
			} // end for
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return noticeMap;
		
	} // upload


	// -----------------------------------------------------------------
	
}
