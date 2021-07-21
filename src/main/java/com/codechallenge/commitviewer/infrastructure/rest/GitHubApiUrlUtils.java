package com.codechallenge.commitviewer.infrastructure.rest;

public class GitHubApiUrlUtils {

    private static final String GITHUB_API_URL_PATTERN = "https://api.github.com/repos/%s/%s/commits";

    private static final String PAGE_AND_SIZE_ARGUMENT_PATTERN = "?page=%o&per_page=%o";

    private GitHubApiUrlUtils() {

    }

    public static String buildCommitsApiUrlFromRepositoryUrlWithPagination(String repositoryName, String ownerName,
            int page, int size) {

        var gitHubApiUrl = String.format(GITHUB_API_URL_PATTERN, ownerName, repositoryName);

        return addPagination(gitHubApiUrl, page, size);
    }

    private static String addPagination(String gitHubApiUrl, int page, int size) {

        var pageAndSizeArgument = String.format(PAGE_AND_SIZE_ARGUMENT_PATTERN, page, size);

        return gitHubApiUrl.concat(pageAndSizeArgument);
    }

}
