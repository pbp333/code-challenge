package com.codechallenge.commitviewer.application.port.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

import com.codechallenge.commitviewer.application.exception.ApplicationException;
import com.codechallenge.commitviewer.application.port.PortUtils;

public class GitRepositoryCommitRestRequestTest {

    @Test
    public void canBuild() {

        // Given
        var repositoryName = RandomString.make(10);
        var ownerName = RandomString.make(10);
        var pageRequest = PortUtils.getRandomPageRequest();

        // When
        var request = GitRepositoryCommitRestRequest.from(repositoryName, ownerName, pageRequest);

        // Then
        assertThat(request).isNotNull();

        assertThat(request.getRepositoryName()).isEqualTo(repositoryName);
        assertThat(request.getOwnerName()).isEqualTo(ownerName);

        assertThat(request.getPageRequest()).isEqualTo(pageRequest);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionWhenRepositoryNameIsNull() {

        // Given
        String repositoryName = null;
        var ownerName = RandomString.make(10);
        var pageRequest = PortUtils.getRandomPageRequest();

        // When
        GitRepositoryCommitRestRequest.from(repositoryName, ownerName, pageRequest);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionWhenReporitoryNameIsEmpty() {

        // Given
        var repositoryName = "";
        var ownerName = RandomString.make(10);
        var pageRequest = PortUtils.getRandomPageRequest();

        // When
        GitRepositoryCommitRestRequest.from(repositoryName, ownerName, pageRequest);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionWhenOwnerNameIsNull() {

        // Given
        var repositoryName = RandomString.make(10);
        String ownerName = null;
        var pageRequest = PortUtils.getRandomPageRequest();

        // When
        GitRepositoryCommitRestRequest.from(repositoryName, ownerName, pageRequest);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionWhenOwnerNameIsEmpty() {

        // Given
        var repositoryName = RandomString.make(10);
        var ownerName = "";
        var pageRequest = PortUtils.getRandomPageRequest();

        // When
        GitRepositoryCommitRestRequest.from(repositoryName, ownerName, pageRequest);

    }



}
