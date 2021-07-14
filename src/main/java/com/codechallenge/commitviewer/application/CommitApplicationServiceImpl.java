package com.codechallenge.commitviewer.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codechallenge.commitviewer.application.api.CommitApplicationService;
import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.infrastructure.rest.RestCommitRetrieverAdapter;

@Service
public class CommitApplicationServiceImpl implements CommitApplicationService {

    private final RestCommitRetrieverAdapter restAdapter;

    @Autowired
    public CommitApplicationServiceImpl(RestCommitRetrieverAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    @Override
    public List<CommitDto> getCommits(String repositoryUrl) {
        return restAdapter.getCommits(repositoryUrl);
    }

}
