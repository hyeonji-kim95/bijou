package com.bijou.oorder;

import java.sql.Date;

public class oorderVO {

	private int oorder_num;
	private int product_num;
	private int member_num;
	private String oorder_name;
	private String oorder_phone;
	private int oorder_count;
	private int oorder_price;
	private String oorder_status;
	private String oorder_post;
	private String oorder_address1;
	private String oorder_address2;
	private String oorder_address3;
	private Date oorder_date;
	private String oorder_transnum;
	private String oorder_pay;
	
	
	public oorderVO() {}
	
	// 상품 페이지에서 주문시 insert하기 위한 값 저장
	public oorderVO(int product_num, int member_num, String oorder_name, String oorder_phone, int oorder_count,
			int oorder_price, String oorder_post, String oorder_address1, String oorder_address2, String oorder_address3,
			String oorder_pay) {
		this.product_num = product_num;
		this.member_num = member_num;
		this.oorder_name = oorder_name;
		this.oorder_phone = oorder_phone;
		this.oorder_count = oorder_count;
		this.oorder_price = oorder_price;
		this.oorder_post = oorder_post;
		this.oorder_address1 = oorder_address1;
		this.oorder_address2 = oorder_address2;
		this.oorder_address3 = oorder_address3;
		this.oorder_pay = oorder_pay;
	}
	
	// 장바구니 주문시 insert하기 위한 값 저장
	public oorderVO(int member_num, String oorder_name, String oorder_phone, String oorder_post,
			String oorder_address1, String oorder_address2, String oorder_address3, String oorder_pay) {
		this.member_num = member_num;
		this.oorder_name = oorder_name;
		this.oorder_phone = oorder_phone;
		this.oorder_post = oorder_post;
		this.oorder_address1 = oorder_address1;
		this.oorder_address2 = oorder_address2;
		this.oorder_address3 = oorder_address3;
		this.oorder_pay = oorder_pay;
	}

	public int getOorder_num() {
		return oorder_num;
	}
	public void setOorder_num(int oorder_num) {
		this.oorder_num = oorder_num;
	}
	public int getProduct_num() {
		return product_num;
	}
	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}
	public int getMember_num() {
		return member_num;
	}
	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}
	public String getOorder_name() {
		return oorder_name;
	}
	public void setOorder_name(String oorder_name) {
		this.oorder_name = oorder_name;
	}
	public String getOorder_phone() {
		return oorder_phone;
	}
	public void setOorder_phone(String oorder_phone) {
		this.oorder_phone = oorder_phone;
	}
	public int getOorder_count() {
		return oorder_count;
	}
	public void setOorder_count(int oorder_count) {
		this.oorder_count = oorder_count;
	}
	public int getOorder_price() {
		return oorder_price;
	}
	public void setOorder_price(int oorder_price) {
		this.oorder_price = oorder_price;
	}
	public String getOorder_status() {
		return oorder_status;
	}
	public void setOorder_status(String oorder_status) {
		this.oorder_status = oorder_status;
	}
	public String getOorder_post() {
		return oorder_post;
	}
	public void setOorder_post(String oorder_post) {
		this.oorder_post = oorder_post;
	}
	public String getOorder_address1() {
		return oorder_address1;
	}
	public void setOorder_address1(String oorder_address1) {
		this.oorder_address1 = oorder_address1;
	}
	public String getOorder_address2() {
		return oorder_address2;
	}
	public void setOorder_address2(String oorder_address2) {
		this.oorder_address2 = oorder_address2;
	}
	public String getOorder_address3() {
		return oorder_address3;
	}
	public void setOorder_address3(String oorder_address3) {
		this.oorder_address3 = oorder_address3;
	}
	public Date getOorder_date() {
		return oorder_date;
	}
	public void setOorder_date(Date oorder_date) {
		this.oorder_date = oorder_date;
	}
	public String getOorder_transnum() {
		return oorder_transnum;
	}
	public void setOorder_transnum(String oorder_transnum) {
		this.oorder_transnum = oorder_transnum;
	}
	public String getOorder_pay() {
		return oorder_pay;
	}
	public void setOorder_pay(String oorder_pay) {
		this.oorder_pay = oorder_pay;
	}
	
	
}
