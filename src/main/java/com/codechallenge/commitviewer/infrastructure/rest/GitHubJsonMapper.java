package com.codechallenge.commitviewer.infrastructure.rest;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.infrastructure.rest.json.GitHubCommitResponse;

public class GitHubJsonMapper {

    private GitHubJsonMapper() {

    }

    public static CommitDto map(GitHubCommitResponse gitHubCommit) {

        if (gitHubCommit == null)
            throw new IllegalArgumentException("GitHub commit is invalid");

        var builder = CommitDto.builder();

        builder.sha(gitHubCommit.getSha());
        builder.message(gitHubCommit.getCommit().getMessage());
        builder.date(gitHubCommit.getCommit().getAuthor().getDate());
        builder.authorName(gitHubCommit.getCommit().getAuthor().getName());

        return builder.build();

    }

}
