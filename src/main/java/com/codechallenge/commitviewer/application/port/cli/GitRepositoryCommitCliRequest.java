package com.codechallenge.commitviewer.application.port.cli;

import com.codechallenge.commitviewer.application.exception.ApplicationException;
import com.codechallenge.commitviewer.application.port.PageRequest;

public class GitRepositoryCommitCliRequest {

    private final String url;
    private final PageRequest pageRequest;

    private GitRepositoryCommitCliRequest(String url, PageRequest pageRequest) {
        this.url = url;
        this.pageRequest = pageRequest;
    }

    public static GitRepositoryCommitCliRequest from(String url, PageRequest pageRequest) {

        if (url == null || url.isEmpty())
            throw new ApplicationException("Url is invalid");

        return new GitRepositoryCommitCliRequest(url, pageRequest);

    }

    public String getUrl() {
        return url;
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }

}
