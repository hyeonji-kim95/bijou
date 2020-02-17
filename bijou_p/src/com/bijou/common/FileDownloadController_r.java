package com.bijou.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download_r.do")
public class FileDownloadController_r extends HttpServlet {
	private static String ARTICLE_IMAGE_REPO = "c:\\bijou\\b_review_img";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		// 이미지 파일 이름과 글번호를 가져옴
		String review_img = request.getParameter("review_img");
		String review_num = request.getParameter("review_num");
		
		OutputStream out = response.getOutputStream();
		// 글번호에 대한 파일 경로를 설정함
		String path = ARTICLE_IMAGE_REPO + "\\" + review_num + "\\" + review_img;
		
		File review_img_file = new File(path);
		
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment;fileName=" + review_img);
		
		FileInputStream in = new FileInputStream(review_img_file);
		
		byte[] buffer = new byte[1024*8];
		
		while(true) {
			int count = in.read(buffer);
			if(count == -1) {
				break;
			}
			out.write(buffer, 0, count);
		}
		
		in.close();
		out.close();
		
	}
}
