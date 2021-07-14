package com.codechallenge.commitviewer.infrastructure.rest;

import java.util.List;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.port.CommitRetrieverPort;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

public class RestCommitRetrieverAdapter implements CommitRetrieverPort {



    @Override
    public CommitRetriverStrategy getStrategy() {
        return CommitRetriverStrategy.REST;
    }

    @Override
    public List<CommitDto> getCommits(String repositoryUrl) {

        String commitsApiUrl = GitHubApiUtil.buildCommitsApiUrlFromRepositoryUrl(repositoryUrl);


        return null;
    }


}
