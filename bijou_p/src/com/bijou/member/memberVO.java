package com.bijou.member;

public class memberVO {
	private int member_num;
	private String member_id;
	private String member_pw;
	private String member_name;
	private String member_phone;
	private String member_post;
	private String member_address1; 
	private String member_address2;
	private String member_address3;
	private String member_rating;
	private int member_point;
	
	
	// 기본 생성자
	public memberVO() {}
	// member_num, member_rating, member_point는 제외
	public memberVO(String member_id, String member_pw, String member_name, String member_phone,
			String member_post, String member_address1, String member_address2, String member_address3) {
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.member_name = member_name;
		this.member_phone = member_phone;
		this.member_post = member_post;
		this.member_address1 = member_address1;
		this.member_address2 = member_address2;
		this.member_address3 = member_address3;
	}
	// 전부
	public memberVO(String member_id, String member_pw, String member_name, String member_phone,
			String member_post, String member_address1, String member_address2, String member_address3,
			String member_rating, int member_point) {
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.member_name = member_name;
		this.member_phone = member_phone;
		this.member_post = member_post;
		this.member_address1 = member_address1;
		this.member_address2 = member_address2;
		this.member_address3 = member_address3;
		this.member_rating = member_rating;
		this.member_point = member_point;
	}


	public int getMember_num() {
		return member_num;
	}
	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_pw() {
		return member_pw;
	}
	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_phone() {
		return member_phone;
	}
	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}
	
	public String getMember_post() {
		return member_post;
	}
	public void setMember_post(String member_post) {
		this.member_post = member_post;
	}
	public String getMember_address1() {
		return member_address1;
	}
	public void setMember_address1(String member_address1) {
		this.member_address1 = member_address1;
	}
	public String getMember_address2() {
		return member_address2;
	}
	public void setMember_address2(String member_address2) {
		this.member_address2 = member_address2;
	}
	public String getMember_address3() {
		return member_address3;
	}
	public void setMember_address3(String member_address3) {
		this.member_address3 = member_address3;
	}
	public String getMember_rating() {
		return member_rating;
	}
	public void setMember_rating(String member_rating) {
		this.member_rating = member_rating;
	}
	public int getMember_point() {
		return member_point;
	}
	public void setMember_point(int member_point) {
		this.member_point = member_point;
	}
	
	
	
}
