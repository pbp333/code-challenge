package com.codechallenge.commitviewer.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codechallenge.commitviewer.application.api.CommitApplicationService;
import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;
import com.codechallenge.commitviewer.infrastructure.cli.CliCommitRetrieverAdapter;
import com.codechallenge.commitviewer.infrastructure.rest.RestCommitRetrieverAdapter;

@Service
public class CommitApplicationServiceImpl implements CommitApplicationService {

    private final RestCommitRetrieverAdapter restAdapter;
    private final CliCommitRetrieverAdapter cliAdapter;

    @Autowired
    public CommitApplicationServiceImpl(RestCommitRetrieverAdapter restAdapter, CliCommitRetrieverAdapter cliAdapter) {
        this.restAdapter = restAdapter;
        this.cliAdapter = cliAdapter;
    }

    @Override
    public List<CommitDto> getCommits(PaginatedRequest<String> request) {

        return cliAdapter.getCommits(request);
    }

}
