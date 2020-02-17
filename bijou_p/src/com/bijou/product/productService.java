package com.bijou.product;

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

import com.bijou.qna.qnaService;
import com.bijou.review.reviewService;

public class productService {
	productDAO dao;

	private static String ARTICLE_IMAGE_REPO2 = "C:\\bijou\\b_product_img";
	
	public productService() {
		dao = new productDAO();
	}
	
	List<productVO> listProduct;
	
	// -----------------------------------------------------------------
	
	// 상품목록
	public void listProduct(HttpServletRequest request, HttpServletResponse response) {
		listProduct = dao.listProduct();
		request.setAttribute("listProduct", listProduct);
	}

	// 상품 상세보기
	public void contentProduct(HttpServletRequest request, HttpServletResponse response) {
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		productVO vo = new productVO();
		vo = dao.contentProduct(product_num);
		request.setAttribute("vo", vo);
		
		// 상품에 해당하는 QnA 목록
		qnaService qna_service = new qnaService();
		qna_service.listProductQna(request, response);
		
		// 상품에 해당하는 Review 목록
		reviewService review_service = new reviewService();
		review_service.listReview(request, response);
		
	}
	
	// 상품등록
	public void insertProduct(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> productMap = upload(request, response);
			String product_name = productMap.get("product_name");
			String product_img = productMap.get("product_img");
			int product_count = Integer.parseInt(productMap.get("product_count"));
			int product_price = Integer.parseInt(productMap.get("product_price"));
			String product_content = productMap.get("product_content");
			String product_cimg1 = productMap.get("product_cimg1");
			String product_cimg2 = productMap.get("product_cimg2");
			String product_cimg3 = productMap.get("product_cimg3");
			
			productVO vo = new productVO();

			vo.setProduct_name(product_name);
			vo.setProduct_img(product_img);
			vo.setProduct_count(product_count);
			vo.setProduct_price(product_price);
			vo.setProduct_content(product_content);
			vo.setProduct_cimg1(product_cimg1);
			vo.setProduct_cimg2(product_cimg2);
			vo.setProduct_cimg3(product_cimg3);
			
			int auto_num = dao.getAutoNum();
			
			dao.insertProduct(vo);
			
			if (product_img != null && product_img.length() != 0) {
				File srcFile = new File(ARTICLE_IMAGE_REPO2 + "\\" + "temp" + "\\" + product_img);
				File destDir = new File(ARTICLE_IMAGE_REPO2 + "\\" + auto_num);
				destDir.mkdirs();
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				srcFile.delete();
				
				// 상세 이미지
				if(product_cimg1 != null && product_cimg1.length() != 0) {
					File CsrcFile1 = new File(ARTICLE_IMAGE_REPO2 + "\\" + "temp" + "\\" + product_cimg1);
					FileUtils.moveFileToDirectory(CsrcFile1, destDir, true);
					CsrcFile1.delete();
				}
				if(product_cimg2 != null && product_cimg2.length() != 0) {
					File CsrcFile2 = new File(ARTICLE_IMAGE_REPO2 + "\\" + "temp" + "\\" + product_cimg2);
					FileUtils.moveFileToDirectory(CsrcFile2, destDir, true);
					CsrcFile2.delete();
				}
				if(product_cimg3 != null && product_cimg3.length() != 0) {
					File CsrcFile3 = new File(ARTICLE_IMAGE_REPO2 + "\\" + "temp" + "\\" + product_cimg3);
					FileUtils.moveFileToDirectory(CsrcFile3, destDir, true);
					CsrcFile3.delete();
				}
			}
			
		} catch (Exception e) {
			System.out.println("productService의 insertProduct메소드 오류");
		}
	}

	// 상품수정
	public void updateProduct(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			int product_num = Integer.parseInt(request.getParameter("product_num"));
			String product_name = request.getParameter("product_name");
			int product_price = Integer.parseInt(request.getParameter("product_price"));
			int product_count = Integer.parseInt(request.getParameter("product_count"));
			
			dao.updateProduct(product_num, product_name, product_price, product_count);
			
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('상품수정 완료');" + "location.href='" + request.getContextPath()
					+ "/product/content.do?product_num=" + product_num + "';" + "</script>");
			
			return;
		} catch (IOException e) {
			System.out.println("productService의 updateProduct메소드 오류");
		}
	}

	// 상품삭제
	public void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			int product_num = Integer.parseInt(request.getParameter("product_num"));
			
			dao.deleteProduct(product_num);

			// 파일 삭제
			File imgDir = new File(ARTICLE_IMAGE_REPO2 + "\\" + product_num);
			if(imgDir.exists()) {
				FileUtils.deleteDirectory(imgDir);
			}
			
			System.out.println(request.getContextPath() + "/product/list.do");
			
			PrintWriter pw = response.getWriter();
			pw.print("<script>" + "  alert('삭제완료');" + " location.href='" + request.getContextPath()
			+ "/product/list.do';" + "</script>");
		} catch (IOException e) {
			System.out.println("productService의 deleteProduct메소드 오류");
		}
	}
	
	// -----------------------------------------------------------------
	
	// 파일 업로드
	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> productMap = new HashMap<String, String>();
		String encoding = "utf-8";
		File currentDirPath = new File(ARTICLE_IMAGE_REPO2);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				if (fileItem.isFormField()) {
					productMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
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
						productMap.put(fileItem.getFieldName(), fileName);  
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName);
						fileItem.write(uploadFile);

					} // end if
				} // end if
			} // end for
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return productMap;
		
	} // upload	

	// -----------------------------------------------------------------
	
}
