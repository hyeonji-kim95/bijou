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

@WebServlet("/download_p.do")
public class FileDownloadController_p extends HttpServlet {

	private static String ARTICLE_IMAGE_REPO = "c:\\bijou\\b_product_img";

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
		String product_img = request.getParameter("product_img");
		String product_num = request.getParameter("product_num");
		
		String product_cimg1 = request.getParameter("product_cimg1");
		String product_cimg2 = request.getParameter("product_cimg2");
		String product_cimg3 = request.getParameter("product_cimg3");
		
		String path = ARTICLE_IMAGE_REPO;
		OutputStream out = response.getOutputStream();
		File product_img_file;
		
		if(product_img != null && product_img.length() != 0) {
			
			// 글번호에 대한 파일 경로를 설정함
			path = ARTICLE_IMAGE_REPO + "\\" + product_num + "\\" + product_img;
			
			product_img_file = new File(path);
			
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment;fileName=" + product_img);
			
			FileInputStream in = new FileInputStream(product_img_file);
			
			byte[] buffer = new byte[1024*8];
			
			while(true) {
				int count = in.read(buffer);
				if(count == -1) {
					break;
				}
				out.write(buffer, 0, count);
			}
			
			in.close();
		}
		
		// ----------------------------------------------------------------------
		
		if(product_cimg1 != null && product_cimg1.length() != 0) {
			path = ARTICLE_IMAGE_REPO + "\\" + product_num + "\\" + product_cimg1;
			
			product_img_file = new File(path);
			
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment;fileName=" + product_cimg1);
			
			FileInputStream in1 = new FileInputStream(product_img_file);
			
			byte[] cbuffer1 = new byte[1024*8];
			
			while(true) {
				int count = in1.read(cbuffer1);
				if(count == -1) {
					break;
				}
				out.write(cbuffer1, 0, count);
			}
			
			in1.close();
		} // product_cimg1
		
		if(product_cimg2 != null && product_cimg2.length() != 0) {
			path = ARTICLE_IMAGE_REPO + "\\" + product_num + "\\" + product_cimg2;
			
			product_img_file = new File(path);
			
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment;fileName=" + product_cimg2);
			
			FileInputStream in2 = new FileInputStream(product_img_file);
			
			byte[] cbuffer2 = new byte[1024*8];
			
			while(true) {
				int count = in2.read(cbuffer2);
				if(count == -1) {
					break;
				}
				out.write(cbuffer2, 0, count);
			}
			
			in2.close();
		} // product_cimg2
		

		if(product_cimg3 != null && product_cimg3.length() != 0) {
			path = ARTICLE_IMAGE_REPO + "\\" + product_num + "\\" + product_cimg3;
			
			product_img_file = new File(path);
			
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment;fileName=" + product_cimg3);
			
			FileInputStream in3 = new FileInputStream(product_img_file);
			
			byte[] cbuffer3 = new byte[1024*8];
			
			while(true) {
				int count = in3.read(cbuffer3);
				if(count == -1) {
					break;
				}
				out.write(cbuffer3, 0, count);
			}
			
			in3.close();
		} // product_cimg1
		

		out.close();
		
	}

}
