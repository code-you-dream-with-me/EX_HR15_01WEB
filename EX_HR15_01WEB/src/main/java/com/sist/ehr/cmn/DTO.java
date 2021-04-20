package com.sist.ehr.cmn;
/**
 * 모든 VO의 Parent
 * @author sist
 *
 */
public class DTO {

	//번호
	private int num;
	//총글수
	private int totalCnt;
	
	public DTO() {}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	@Override
	public String toString() {
		return "DTO [num=" + num + ", totalCnt=" + totalCnt + "]";
	}
	
	
}
