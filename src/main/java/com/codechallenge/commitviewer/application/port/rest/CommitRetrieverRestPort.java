package com.codechallenge.commitviewer.application.port.rest;

import java.util.List;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;

public interface CommitRetrieverRestPort {

    List<CommitDto> getCommits(GitRepositoryCommitRestRequest request);

}
