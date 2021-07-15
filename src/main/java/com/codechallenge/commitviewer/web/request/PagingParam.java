package com.codechallenge.commitviewer.web.request;

public class PagingParam {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 5;

    private int page;
    private int size;

    public PagingParam() {
        this.page = DEFAULT_PAGE;
        this.size = DEFAULT_SIZE;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
