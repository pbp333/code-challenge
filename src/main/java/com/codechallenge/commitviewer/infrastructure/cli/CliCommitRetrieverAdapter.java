package com.codechallenge.commitviewer.infrastructure.cli;

import java.util.List;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;
import com.codechallenge.commitviewer.application.port.CommitRetrieverPort;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

public class CliCommitRetrieverAdapter implements CommitRetrieverPort {

    @Override
    public CommitRetriverStrategy getStrategy() {
        return CommitRetriverStrategy.CLI;
    }

    @Override
    public List<CommitDto> getCommits(PaginatedRequest<String> request) {
        // TODO Auto-generated method stub
        return null;
    }

}
