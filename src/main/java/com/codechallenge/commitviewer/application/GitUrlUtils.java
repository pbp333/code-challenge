package com.codechallenge.commitviewer.application;

import com.codechallenge.commitviewer.application.exception.ApplicationException;

public class GitUrlUtils {

    private GitUrlUtils() {

    }

    public static String extractRepositoryName(String repositoryUrl) {

        try {

            return repositoryUrl.split("/")[4].replace(".git", "");

        } catch (Exception e) {
            throw new ApplicationException("Invalid GitHub URL");
        }
    }

    public static String extractOwnerName(String repositoryUrl) {
        try {

            return repositoryUrl.split("/")[3];

        } catch (Exception e) {
            throw new ApplicationException("Invalid GitHub URL");
        }

    }

}
