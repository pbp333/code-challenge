package com.codechallenge.commitviewer.application.api;

import java.util.List;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;

public interface CommitApplicationService {

    List<CommitDto> getCommits(PaginatedRequest<String> request);



}
