package com.atm.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.service.OnlineService;
import com.opensymphony.xwork2.ActionSupport;

public class PageAction extends ActionSupport {
	private static final Logger log = LoggerFactory.getLogger(PageAction.class);
	private int currentPage;
	private int pageNum;
	private int maxPage;
	private String list;

	public String userList() {
		// 还是交给online对象搞定
		OnlineService online = new OnlineService();
		if (currentPage == 0) {
			currentPage = 1;
		}
		if (pageNum == 0) {
			pageNum = 20;
		}
		System.out.println("-------------------currentPage:" + currentPage);
		System.out.println("-------------------pageNum:" + pageNum);
		// 用户数量
		int max = online.getMaxRecord();
		System.out.println("-------------------max:" + max);
		if (max % pageNum == 0) {
			maxPage = max / pageNum;
		} else {
			maxPage = max / pageNum + 1;
		}
		System.out.println("-------------------maxPage:" + maxPage);
		int first = (currentPage - 1) * pageNum;
		System.out.println("begin get UserList");
		list = online.getUserList(first, pageNum);

		return SUCCESS;
	}

	public String loginList(){
		/*log.info("exxcite./.......");
		OnlineService onlineService = new OnlineService();
		list = onlineService.getLoginList();
		// inputStream = new StringBufferInputStream(transcoding(string));
		return SUCCESS;*/

		// 还是交给online对象搞定
		OnlineService online = new OnlineService();
		if (currentPage == 0) {
			currentPage = 1;
		}
		if (pageNum == 0) {
			pageNum = 20;
		}
		System.out.println("-------------------currentPage:" + currentPage);
		System.out.println("-------------------pageNum:" + pageNum);
		// 用户数量
		int max = online.getLoginMaxRecord();
		System.out.println("-------------------max:" + max);
		if (max % pageNum == 0) {
			maxPage = max / pageNum;
		} else {
			maxPage = max / pageNum + 1;
		}
		System.out.println("-------------------maxPage:" + maxPage);
		int first = (currentPage - 1) * pageNum;
		System.out.println("begin get UserList");
		list = online.getLoginList(first, pageNum);

		return SUCCESS;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

}
