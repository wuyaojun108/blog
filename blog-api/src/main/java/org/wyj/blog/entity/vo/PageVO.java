package org.wyj.blog.entity.vo;

import java.util.List;

public class PageVO<T> {
    private int totalPageNum;
    private List<T> data;

    public PageVO() {
    }

    public PageVO(int totalPageNum, List<T> data) {
        this.totalPageNum = totalPageNum;
        this.data = data;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
