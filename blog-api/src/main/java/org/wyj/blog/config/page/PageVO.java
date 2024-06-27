package org.wyj.blog.config.page;

import java.util.List;

/**
 * @author 武耀君
 * @date 2024/4/14
 */
public class PageVO<T> {
    private Integer totalPageNum;
    private List<T> data;

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
