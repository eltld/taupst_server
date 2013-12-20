package test.util;

import java.util.Collection;

public class Page {

	private Integer pageNo;
	private Integer pageSize;
	private Boolean nextPage;
	private Boolean prePage;
	private Long totalRecNum;
	private Integer totalPageNum;
	private Collection pageContent;
	private Integer startIndex;
	private Integer endIndex;

	public Page() {
		super();
		pageNo = 1;
		pageSize = 5;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getNextPage() {
		return pageNo < getTotalPageNum() ? true : false;
	}

	public Boolean getPrePage() {
		return pageNo > 1 ? true : false;
	}

	public Long getTotalRecNum() {
		return totalRecNum;
	}

	public void setTotalRecNum(Long totalRecNum) {
		this.totalRecNum = totalRecNum;
	}

	public Integer getTotalPageNum() {
		return totalRecNum % pageSize > 0 ? (int) (totalRecNum / pageSize + 1)
				: (int) (totalRecNum / pageSize);
	}

	public Collection getPageContent() {
		return pageContent;
	}

	public void setPageContent(Collection pageContent) {
		this.pageContent = pageContent;
	}

	public int getStartIndex() {
		return pageSize * (pageNo - 1); // size:10 pageno:3 21
	}

	public int getEndIndex() {
		return (pageSize * pageNo > this.totalRecNum) ? (int) (this.totalRecNum
				.longValue()) : (pageSize * pageNo);
	}

}
