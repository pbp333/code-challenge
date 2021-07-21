package com.codechallenge.commitviewer.application.port.cli;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

import com.codechallenge.commitviewer.application.exception.ApplicationException;
import com.codechallenge.commitviewer.application.port.PortUtils;

public class GitRepositoryCommitCliRequestTest {

    @Test
    public void canBuild() {

        // Given
        var url = RandomString.make(10);
        var pageRequest = PortUtils.getRandomPageRequest();

        // When
        var request = GitRepositoryCommitCliRequest.from(url, pageRequest);

        // Then
        assertThat(request).isNotNull();

        assertThat(request.getUrl()).isEqualTo(url);
        assertThat(request.getPageRequest()).isEqualTo(pageRequest);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionWhenUrlIsNull() {

        // Given
        String url = null;
        var pageRequest = PortUtils.getRandomPageRequest();

        // When
        GitRepositoryCommitCliRequest.from(url, pageRequest);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionWhenUrlIsEmpty() {

        // Given
        var url = "";
        var pageRequest = PortUtils.getRandomPageRequest();

        // When
        GitRepositoryCommitCliRequest.from(url, pageRequest);

    }

}
