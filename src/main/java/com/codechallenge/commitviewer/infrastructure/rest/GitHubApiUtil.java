package com.codechallenge.commitviewer.infrastructure.rest;

public class GitHubApiUtil {

    private static final String GITHUB_API_URL_PATTERN = "https://api.github.com/repos/%s/%s/commits";

    private GitHubApiUtil() {

    }

    public static String buildCommitsApiUrlFromRepositoryUrl(String repositoryUrl) {

        String repositoryName = extractRepositoryName(repositoryUrl);
        String ownerName = extractOwnerName(repositoryUrl);

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
