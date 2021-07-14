package com.codechallenge.commitviewer.application.api;

import java.util.List;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;

public interface CommitApplicationService {

    List<CommitDto> getCommits(String repositoryUrl);

}
