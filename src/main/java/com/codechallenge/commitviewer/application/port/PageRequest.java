package com.codechallenge.commitviewer.application.port;

import com.codechallenge.commitviewer.application.exception.ApplicationException;

public class PageRequest {

    private final int page;
    private final int size;

    protected PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public static PageRequest from(int page, int size) {

        if (page <= 0)
            throw new ApplicationException("Page is invalid");

        if (size <= 0)
            throw new ApplicationException("Page is invalid");

        return new PageRequest(page, size);
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

}
