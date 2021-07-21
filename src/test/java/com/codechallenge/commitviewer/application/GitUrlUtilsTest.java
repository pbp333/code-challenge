package com.codechallenge.commitviewer.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.codechallenge.commitviewer.application.exception.ApplicationException;

public class GitUrlUtilsTest {

    @Test
    public void canExtractOwnerName() {

        // Given
        String repositoryUrl = "https://github.com/pbp333/code-challenge.git";

        var expectedOwnerName = "pbp333";

        // When
        String ownerName = GitUrlUtils.extractOwnerName(repositoryUrl);

        // Then
        assertThat(ownerName).isEqualTo(expectedOwnerName);

    }

    @Test
    public void canExtractRepositoryName() {

        // Given
        String repositoryUrl = "https://github.com/pbp333/code-challenge.git";

        var expectedRepositoryName = "code-challenge";

        // When
        String repositoryName = GitUrlUtils.extractRepositoryName(repositoryUrl);

        // Then
        assertThat(repositoryName).isEqualTo(expectedRepositoryName);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionExtractingOwnerName() {

        // Given
        String repositoryUrl = "https://github.com/";

        // When
        GitUrlUtils.extractOwnerName(repositoryUrl);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionExtractingRepositoryName() {

        // Given
        String repositoryUrl = "https://github.com/pbp333";

        // When
        GitUrlUtils.extractRepositoryName(repositoryUrl);

    }

}
