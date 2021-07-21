package com.codechallenge.commitviewer.application.port.cli;

import java.util.List;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;

public interface CommitRetrieverCliPort {

    List<CommitDto> getCommits(GitRepositoryCommitCliRequest request);

}
