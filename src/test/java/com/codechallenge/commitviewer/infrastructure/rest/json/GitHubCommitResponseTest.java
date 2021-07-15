package com.codechallenge.commitviewer.infrastructure.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class GitHubCommitResponseTest {

    @Test
    public void canBuild() {

        // Given
        String sha = RandomString.make(10);
        String nodeId = RandomString.make(10);
        Commit commit = JsonUtil.getRandomCommit();

        // When
        GitHubCommitResponse response = new GitHubCommitResponse();

        response.setSha(sha);
        response.setNodeId(nodeId);
        response.setCommit(commit);

        // Then
        assertThat(response.getSha()).isEqualTo(sha);
        assertThat(response.getNodeId()).isEqualTo(nodeId);

        assertThat(response.getCommit()).isEqualTo(commit);

    }

}
