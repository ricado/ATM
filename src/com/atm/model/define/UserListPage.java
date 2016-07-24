package com.atm.model.define;

public class UserListPage{
	private int currentPage = 0;
	private int maxPage;
	private int nextPage;
	private int lastPage;
	private int pageNum = 20;
	private int first = 0;
	private int max = 20;
	
	
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}

	public int getNextPage() {
		return getCurrentPage()+1;
	}

	public int getLastPage() {
		return getCurrentPage()-1;
	}
}
