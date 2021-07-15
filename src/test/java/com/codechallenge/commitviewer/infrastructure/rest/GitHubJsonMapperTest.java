package com.codechallenge.commitviewer.infrastructure.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.infrastructure.rest.json.GitHubCommitResponse;
import com.codechallenge.commitviewer.infrastructure.rest.json.JsonUtil;

public class GitHubJsonMapperTest {

    @Test
    public void canMap() {

        // Given
        GitHubCommitResponse gitHubCommit = JsonUtil.getRandomGitHubCommitResponse();

        // When
        CommitDto commit = GitHubJsonMapper.map(gitHubCommit);

        // Then
        assertThat(commit.getSha()).isEqualTo(gitHubCommit.getSha());
        assertThat(commit.getMessage()).isEqualTo(gitHubCommit.getCommit().getMessage());

        assertThat(commit.getDate()).isEqualTo(gitHubCommit.getCommit().getCommitter().getDate());

        assertThat(commit.getAuthorName()).isEqualTo(gitHubCommit.getCommit().getCommitter().getName());

    }

}
