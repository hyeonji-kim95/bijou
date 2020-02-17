package com.bijou.qna;

import java.sql.Date;

public class qnaVO {
	
	private int qna_num;
	private String qna_title;
	private String qna_content;
	private String qna_img;
	private int member_num;
	private int product_num;
	private Date qna_date;
	private String qna_re;
	
	// 기본 생성자
	public qnaVO() {}
	
	
	public int getQna_num() {
		return qna_num;
	}
	public void setQna_num(int qna_num) {
		this.qna_num = qna_num;
	}
	public String getQna_title() {
		return qna_title;
	}
	public void setQna_title(String qna_title) {
		this.qna_title = qna_title;
	}
	public String getQna_content() {
		return qna_content;
	}
	public void setQna_content(String qna_content) {
		this.qna_content = qna_content;
	}
	public String getQna_img() {
		return qna_img;
	}
	public void setQna_img(String qna_img) {
		this.qna_img = qna_img;
	}
	public int getMember_num() {
		return member_num;
	}
	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}
	public int getProduct_num() {
		return product_num;
	}
	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}
	public Date getQna_date() {
		return qna_date;
	}
	public void setQna_date(Date qna_date) {
		this.qna_date = qna_date;
	}
	public String getQna_re() {
		return qna_re;
	}
	public void setQna_re(String qna_re) {
		this.qna_re = qna_re;
	}
	
	
	
}
