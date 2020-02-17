package com.bijou.oorder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bijou.cart.cartDAO;
import com.bijou.cart.cartVO;
import com.bijou.member.memberDAO;
import com.bijou.member.memberVO;
import com.bijou.product.productDAO;
import com.bijou.product.productVO;

public class oorderService {
	oorderVO vo;
	oorderDAO dao;
	
	memberVO mem_vo;
	memberDAO mem_dao;
	
	productVO pro_vo;
	productDAO pro_dao;
	
	cartVO cart_vo;
	cartDAO cart_dao;

	public oorderService() {
		vo = new oorderVO();
		dao = new oorderDAO();
		
		mem_vo = new memberVO();
		mem_dao = new memberDAO();
		
		pro_vo= new productVO();
		pro_dao = new productDAO();
		
		cart_vo = new cartVO();
		cart_dao = new cartDAO();
	}
	
	List<oorderVO> list;

	
	// -----------------------------------------------------------------
	
	// 상품 페이지에서 주문 버튼 클릭 -> 넘어오는 값과 일반 사용자의 값을 넘겨줌
	public void orderProduct(HttpServletRequest request, HttpServletResponse response) {
		// 주문하는 상품정보
		int product_num = Integer.parseInt(request.getParameter("product_num")); // 주문하는 상품번호
		int oorder_count = Integer.parseInt(request.getParameter("oorder_count")); // 주문하는 상품수량
		int oorder_price = Integer.parseInt(request.getParameter("product_price"))*oorder_count; // 주문하는 가격
		
		pro_vo = pro_dao.contentProduct(product_num); // 해당 상품정보를 받아옴
		
		request.setAttribute("pro_vo", pro_vo); // 해당 상품정보
		request.setAttribute("oorder_count", oorder_count); // 선택한 수량
		request.setAttribute("oorder_price", oorder_price); // 선택한 수량에 대한 가격
		
		// 주문하는 회원의 본인정보
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("member_id");
		
		
		mem_vo = mem_dao.findMember(member_id);
		
		request.setAttribute("mem_vo", mem_vo);
		
	}

	// 상품 주문페이지에서 결제완료
	public void payProduct(HttpServletRequest request, HttpServletResponse response) {
		int product_num = Integer.parseInt(request.getParameter("product_num")); // 결제한 상품번호
		int member_num = Integer.parseInt(request.getParameter("member_num")); // 결제한 회원번호
		
		String oorder_name = request.getParameter("oorder_name"); // 받는사람 이름
		String oorder_phone = request.getParameter("oorder_phone"); // 받는사람 전화번호
		int oorder_count = Integer.parseInt(request.getParameter("oorder_count")); // 결제한 개수
		int oorder_price = Integer.parseInt(request.getParameter("oorder_price")); // 결제한 금액
		String oorder_post = request.getParameter("oorder_post"); // 받는사람 우편번호
		String oorder_address1 = request.getParameter("oorder_address1"); // 받는사람 주소1
		String oorder_address2 = request.getParameter("oorder_address2"); // 받는사람 주소2
		String oorder_address3 = request.getParameter("oorder_address3"); // 받는사람 주소3
		String oorder_pay = request.getParameter("oorder_pay"); // 받는사람 결제방식

		oorderVO vo2 = new oorderVO(product_num, member_num, oorder_name, oorder_phone, oorder_count, oorder_price,
				oorder_post, oorder_address1, oorder_address2, oorder_address3, oorder_pay);
		
		dao.insertOrder(vo2);
		
		// 포인트 변경
		int member_point_n = Integer.parseInt(request.getParameter("member_point_n")); // 결제시 사용하고 남은 포인트
		int addPoint = (int)(oorder_price * 0.01); // 결제한 금액에 더해져야 하는 포인트
		int member_point = member_point_n + addPoint; // 일반 사용자 포인트
		mem_dao.updatePoint(member_num, member_point);
		
		// 상품수량 변경
		pro_dao.orderProduct(product_num, oorder_count);
		
		// 관리자에게 메일 발송
		javaMail mail = new javaMail();
		mail.javaMail();
	}

	// 일반사용자 주문조회
	public void myOrder(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("member_id");
		
		// 사용자의 회원번호 불러오기
		int member_num = mem_dao.getMemberNum(member_id);
		
		list = dao.getMyOrder(member_num);
		
		request.setAttribute("list", list);
		
	}

	// 일반사용자 주문취소
	public void myOrderCancle(HttpServletRequest request, HttpServletResponse response) {
		int oorder_num = Integer.parseInt(request.getParameter("oorder_num"));
		dao.cancleMyOrder(oorder_num);
	}

	// 관리자 목록 _ 전체
	public void selectAllOrder(HttpServletRequest request, HttpServletResponse response) {
		list = dao.selectAllOrder();
		
		request.setAttribute("list", list);
		
	}

	// 관리자모드 '대기(hold)' 목록
	public void selectHoldOrder(HttpServletRequest request, HttpServletResponse response) {
		list = dao.selectHoldOrder();
		
		request.setAttribute("list", list);
	}

	// 관리자모드 '배송 준비중(preparing)' 목록
	public void selectPreparingOrder(HttpServletRequest request, HttpServletResponse response) {
		list = dao.selectPreparingOrder();
		
		request.setAttribute("list", list);
	}

	// 관리자모드 '배송중(shipping)' 목록
	public void selectShippingOrder(HttpServletRequest request, HttpServletResponse response) {
		list = dao.selectShippingOrder();
		
		request.setAttribute("list", list);
	}

	// 관리자모드 '배송완료(completed)' 목록
	public void selectCompletedOrder(HttpServletRequest request, HttpServletResponse response) {
		list = dao.selectCompletedOrder();
		
		request.setAttribute("list", list);
	}
	
	// 관리자모드 '배송완료 후 리뷰작성완료(completedR)' 목록
	public void selectCompletedROrder(HttpServletRequest request, HttpServletResponse response) {
		list = dao.selectCompletedROrder();
		
		request.setAttribute("list", list);
	}	

	// 관리자모드 '주문취소(cancle)' 목록
	public void selectCancleOrder(HttpServletRequest request, HttpServletResponse response) {
		list = dao.selectCancledOrder();
		
		request.setAttribute("list", list);
	}

	// 관리자모드 주문상태 변경
	public void updateStatus(HttpServletRequest request, HttpServletResponse response) {
		int oorder_num = Integer.parseInt(request.getParameter("oorder_num"));
		String oorder_status = request.getParameter("oorder_status");
		
		dao.updateStatus(oorder_num, oorder_status);
		
	}

	// 관리자모드 주문삭제
	public void deleteOrder(HttpServletRequest request, HttpServletResponse response) {
		int oorder_num = Integer.parseInt(request.getParameter("oorder_num"));
		
		dao.deleteOrder(oorder_num);
		
	}

	// 관리자모드 운송번호 등록/변경
	public void updateTransnum(HttpServletRequest request, HttpServletResponse response) {
		int oorder_num = Integer.parseInt(request.getParameter("oorder_num"));
		String oorder_transnum = request.getParameter("oorder_transnum");
		
		dao.updateTransnum(oorder_num, oorder_transnum);
		
	}
	
	// 장바구니에서 주문 버튼 클릭 -> 넘어오는 값과 일반 사용자의 값을 넘겨줌
	public int orderCart(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("member_id");
		
		// member_id를 이용해 member_num을 가져옴
		int member_num = mem_dao.getMemberNum(member_id);
		
		// member_num을 이용해 cartList를 가져옴
		List<cartVO> listCart = cart_dao.selectCart(member_num);
		
		// member_num을 이용해 cartList에 가서 카트에 담은 수량이 제품의 수량을 초과하지 않는지 검사
		int check = cart_dao.checkCount(member_num);
		
		// 카트에 있는 상품 개수
		int count = listCart.size();
		request.setAttribute("count", count);
		
		int total = Integer.parseInt(request.getParameter("total"));
		request.setAttribute("total", total);
		
		// 주문하는 회원의 본인정보	
		mem_vo = mem_dao.findMember(member_id);
		
		request.setAttribute("mem_vo", mem_vo);
		
		return check;

	}
	
	// 장바구니 주문 페이지에서 결제완료
	public void payCart(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("member_id");
		
		// member_id를 이용해 member_num을 가져옴
		int member_num = mem_dao.getMemberNum(member_id);
		
		// member_num을 이용해 cartList를 가져옴
		List<cartVO> listCart = cart_dao.selectCart(member_num);
		
		// 카트에 있는 상품 개수
		int count = listCart.size();
		
		// 장바구니 상품 중 가장 가격이 높은 상품
		cart_vo = cart_dao.maxPrice(member_num);
		// 사용한 포인트
		int use_point = Integer.parseInt(request.getParameter("use_point"));
		
		// 변경될 가격
		int price = cart_vo.getCart_price() - use_point;
		int change_num = cart_vo.getProduct_num();
		
		// 카트에 가격 변경
		cart_dao.updatePrice(change_num, price);
		
		// 원래 포인트에서 사용한 포인트를 제외하고, 결제한 금액의 1퍼센트를 포인트로 저장
		int oorder_price = Integer.parseInt(request.getParameter("oorder_price")); // 결제한 금액
		int member_point = mem_vo.getMember_point() - use_point + (int)(oorder_price * 0.01);
		mem_dao.updatePoint(member_num, member_point);
		

		// oorderVO에 request에서 넘어오는 값 저장하기
		String oorder_name = request.getParameter("oorder_name"); // 받는사람 이름
		String oorder_phone = request.getParameter("oorder_phone"); // 받는사람 전화번호
		String oorder_post = request.getParameter("oorder_post"); // 받는사람 우편번호
		String oorder_address1 = request.getParameter("oorder_address1"); // 받는사람 주소1
		String oorder_address2 = request.getParameter("oorder_address2"); // 받는사람 주소2
		String oorder_address3 = request.getParameter("oorder_address3"); // 받는사람 주소3
		String oorder_pay = request.getParameter("oorder_pay"); // 받는사람 결제방식

		oorderVO vo3 = new oorderVO(member_num, oorder_name, oorder_phone, 
				oorder_post, oorder_address1, oorder_address2, oorder_address3, oorder_pay);
		
		dao.payCart(count, vo3); // 여기에 oorderVO에 저장한 vo도 넘겨줘야 함
		cart_dao.deleteCartAll(member_num); // 장바구니 목록 전부 삭제
		
		// 관리자에게 메일 발송
		javaMail mail = new javaMail();
		mail.javaMail();
		
	}
	

}
