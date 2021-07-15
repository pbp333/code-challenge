package com.codechallenge.commitviewer.application.port;

import java.util.List;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;

public interface CommitRetrieverPort {

    CommitRetriverStrategy getStrategy();

    List<CommitDto> getCommits(PaginatedRequest<String> request);

}
