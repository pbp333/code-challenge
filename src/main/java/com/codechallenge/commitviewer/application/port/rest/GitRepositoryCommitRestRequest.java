package com.codechallenge.commitviewer.application.port.rest;

import com.codechallenge.commitviewer.application.exception.ApplicationException;
import com.codechallenge.commitviewer.application.port.PageRequest;

public class GitRepositoryCommitRestRequest {

    private final String repositoryName;
    private final String ownerName;
    private final PageRequest pageRequest;

    private GitRepositoryCommitRestRequest(String repositoryName, String ownerName, PageRequest pageRequest) {
        this.repositoryName = repositoryName;
        this.ownerName = ownerName;
        this.pageRequest = pageRequest;
    }

    public static GitRepositoryCommitRestRequest from(String repositoryName, String ownerName,
            PageRequest pageRequest) {

        if (repositoryName == null || repositoryName.isEmpty())
            throw new ApplicationException("Repository Name is invalid");

        if (ownerName == null || ownerName.isEmpty())
            throw new ApplicationException("Owner Name is invalid");

        return new GitRepositoryCommitRestRequest(repositoryName, ownerName, pageRequest);
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }

}
