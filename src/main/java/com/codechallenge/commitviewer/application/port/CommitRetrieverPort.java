package com.codechallenge.commitviewer.application.port;

import java.util.List;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;

public interface CommitRetrieverPort {

    CommitRetriverStrategy getStrategy();

    List<CommitDto> getCommits(String repositoryUrl);

}
