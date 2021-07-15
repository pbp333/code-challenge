package com.codechallenge.commitviewer.infrastructure.rest;

public class GitHubApiUtil {

    private static final String GITHUB_API_URL_PATTERN = "https://api.github.com/repos/%s/%s/commits";

    private static final String PAGE_AND_SIZE_ARGUMENT_PATTERN = "?page=%o&per_page=%o";

    private GitHubApiUtil() {

    }

    public static String buildCommitsApiUrlFromRepositoryUrlWithPagination(String repositoryUrl, int page, int size) {

        String gitHubApiUrl = buildCommitsApiUrlFromRepositoryUrl(repositoryUrl);

        return addPagination(gitHubApiUrl, page, size);
    }

    private static String addPagination(String gitHubApiUrl, int page, int size) {

        var pageAndSizeArgument = String.format(PAGE_AND_SIZE_ARGUMENT_PATTERN, page, size);

        return gitHubApiUrl.concat(pageAndSizeArgument);
    }

    private static String buildCommitsApiUrlFromRepositoryUrl(String repositoryUrl) {

        String ownerName = extractOwnerName(repositoryUrl);
        String repositoryName = extractRepositoryName(repositoryUrl);

        return String.format(GITHUB_API_URL_PATTERN, ownerName, repositoryName);

    }

    private static String extractRepositoryName(String repositoryUrl) {

        try {

            return repositoryUrl.split("/")[4].replace(".git", "");

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid GitHub URL");
        }
    }

    private static String extractOwnerName(String repositoryUrl) {
        try {

            return repositoryUrl.split("/")[3];

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid GitHub URL");
        }

    }

}
