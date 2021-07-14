package com.codechallenge.commitviewer.infrastructure.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class GitHubApiUtilTest {

    private final static String EXPECTED_COMMIT_API_URL = "https://api.github.com/repos/pbp333/code-challenge/commits";

    @Test
    public void canBuildCommitsApiUrlFromRepositoryUrl() {

        // Given
        String repositoryUrl = "https://github.com/pbp333/code-challenge.git";

        // When
        String commitsApiUrl = GitHubApiUtil.buildCommitsApiUrlFromRepositoryUrl(repositoryUrl);

        // Then
        assertThat(commitsApiUrl).isEqualTo(EXPECTED_COMMIT_API_URL);

    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionBuildingCommitsApiUrlFromRepositoryUrlInvalidUrlCantExtractAuthorName() {

        // Given
        String repositoryUrl = "https://github.com/";

        // When
        GitHubApiUtil.buildCommitsApiUrlFromRepositoryUrl(repositoryUrl);

    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionBuildingCommitsApiUrlFromRepositoryUrlInvalidUrlCantExtractRepositoryName() {

        // Given
        String repositoryUrl = "https://github.com/pbp333";

        // When
        GitHubApiUtil.buildCommitsApiUrlFromRepositoryUrl(repositoryUrl);

    }

}
