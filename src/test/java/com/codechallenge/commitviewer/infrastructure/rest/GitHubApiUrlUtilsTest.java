package com.codechallenge.commitviewer.infrastructure.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Test;

public class GitHubApiUrlUtilsTest {

    private final static String EXPECTED_COMMIT_API_URL = "https://api.github.com/repos/pbp333/code-challenge/commits";

    private static final String PAGE_AND_SIZE_ARGUMENT_PATTERN = "?page=%o&per_page=%o";

    @Test
    public void canBuildCommitsApiUrlFromRepositoryUrlWithPagination() {

        // Given
        var repositoryName = "code-challenge";
        var ownerName = "pbp333";
        var page = new Random().nextInt(100);
        var size = new Random().nextInt(100);

        var pageAndSizeArgument = String.format(PAGE_AND_SIZE_ARGUMENT_PATTERN, page, size);

        var expectedResponseUrl = EXPECTED_COMMIT_API_URL.concat(pageAndSizeArgument);

        // When
        var commitsApiUrl = GitHubApiUrlUtils.buildCommitsApiUrlFromRepositoryUrlWithPagination(repositoryName,
                ownerName, page, size);

        // Then
        assertThat(commitsApiUrl).isEqualTo(expectedResponseUrl);

    }

}
