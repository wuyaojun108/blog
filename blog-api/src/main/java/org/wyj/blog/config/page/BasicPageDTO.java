package org.wyj.blog.config.page;

/**
 * @auther 武耀君
 * @date 2024/4/14
 */
public class BasicPageDTO {
    private Integer pageNo;
    private Integer pageSize;
    private Integer total;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
