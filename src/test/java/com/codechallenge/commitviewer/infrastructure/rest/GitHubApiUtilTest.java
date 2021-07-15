package com.codechallenge.commitviewer.infrastructure.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Test;

public class GitHubApiUtilTest {

    private final static String EXPECTED_COMMIT_API_URL = "https://api.github.com/repos/pbp333/code-challenge/commits";

    private static final String PAGE_AND_SIZE_ARGUMENT_PATTERN = "?page=%o&per_page=%o";

    @Test
    public void canBuildCommitsApiUrlFromRepositoryUrlWithPagination() {

        // Given
        String repositoryUrl = "https://github.com/pbp333/code-challenge.git";
        int page = new Random().nextInt(100);
        int size = new Random().nextInt(100);

        var pageAndSizeArgument = String.format(PAGE_AND_SIZE_ARGUMENT_PATTERN, page, size);

        var expectedResponseUrl = EXPECTED_COMMIT_API_URL.concat(pageAndSizeArgument);

        // When
        String commitsApiUrl =
                GitHubApiUtil.buildCommitsApiUrlFromRepositoryUrlWithPagination(repositoryUrl, page, size);

        // Then
        assertThat(commitsApiUrl).isEqualTo(expectedResponseUrl);

    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionBuildingCommitsApiUrlFromRepositoryUrlInvalidUrlCantExtractAuthorName() {

        // Given
        String repositoryUrl = "https://github.com/";
        int page = new Random().nextInt(100);
        int size = new Random().nextInt(100);

        // When
        GitHubApiUtil.buildCommitsApiUrlFromRepositoryUrlWithPagination(repositoryUrl, page, size);

    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionBuildingCommitsApiUrlFromRepositoryUrlInvalidUrlCantExtractRepositoryName() {

        // Given
        String repositoryUrl = "https://github.com/pbp333";
        int page = new Random().nextInt(100);
        int size = new Random().nextInt(100);

        // When
        GitHubApiUtil.buildCommitsApiUrlFromRepositoryUrlWithPagination(repositoryUrl, page, size);

    }

}
