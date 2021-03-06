package com.codechallenge.commitviewer.infrastructure.cli;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.codechallenge.commitviewer.application.exception.TechnicalException;
import com.codechallenge.commitviewer.application.port.PortUtils;

@RunWith(MockitoJUnitRunner.class)
public class CliCommitRetrieverAdapterTest {

    private static final String EXPECTED_COMMIT_FORMATTER = "%s-%s-%s-%s";

    @Mock
    private CommandLineInterface cli;

    @Mock
    private FileManager fileManager;

    private CliCommitRetrieverAdapter adapter;

    @Before
    public void setup() {
        adapter = new CliCommitRetrieverAdapter(cli, fileManager);
    }

    @Test
    public void canGetCommits() throws IOException, InterruptedException {

        // Given
        var request = PortUtils.getRandomGitRepositoryCommitCliRequest();

        var expectedProjectFolder = "expectedProjectFolder";

        var sha = RandomString.make(10);
        var authorName = RandomString.make(10);

        var epochSeconds = Instant.now().getEpochSecond();
        var dateAsString = String.valueOf(epochSeconds);
        var expectedDate = Instant.ofEpochSecond(epochSeconds);

        var message = RandomString.make(30);

        var commit = String.format(EXPECTED_COMMIT_FORMATTER, sha, authorName, dateAsString, message);

        var mockFile = mock(File.class);

        when(fileManager.createTemporaryFolder(any(String.class))).thenReturn(mockFile);

        when(mockFile.getAbsolutePath()).thenReturn("absolutePath");

        var captor = ArgumentCaptor.forClass(String[].class);

        when(cli.excuteCommand(captor.capture(), any(File.class))).thenReturn(Collections.emptyList())
                .thenReturn(Arrays.asList(expectedProjectFolder)).thenReturn(Arrays.asList(commit));

        when(fileManager.removeTemporaryFolderAndContents(any(File.class))).thenReturn(true);

        // When
        var response = adapter.getCommits(request);

        // Then
        assertThat(response).isNotNull().hasSize(1);

        assertThat(response.get(0).getSha()).isEqualTo(sha);
        assertThat(response.get(0).getAuthorName()).isEqualTo(authorName);
        assertThat(response.get(0).getDate()).isEqualTo(expectedDate);
        assertThat(response.get(0).getMessage()).isEqualTo(message);

        var cliArguments = captor.getAllValues();

        assertThat(cliArguments.size()).isEqualTo(3);

        var gitCloneArguments = cliArguments.get(0);

        assertThat(gitCloneArguments).hasSize(3);
        assertThat(gitCloneArguments[0]).isEqualTo("git");
        assertThat(gitCloneArguments[1]).isEqualTo("clone");
        assertThat(gitCloneArguments[2]).isEqualTo(request.getUrl());

        var retrieveRepositoryFolderArguments = cliArguments.get(1);

        assertThat(retrieveRepositoryFolderArguments).hasSize(1);
        assertThat(retrieveRepositoryFolderArguments[0]).isEqualTo("ls");

        var retrieveCommitsArguments = cliArguments.get(2);

        var numberOfCommitsToSkip = request.getPageRequest().getSize() * (request.getPageRequest().getPage() - 1);

        var sizeArgument = String.format("--max-count=%d", request.getPageRequest().getSize());
        var numberOfCommitsToSkipArgument = String.format("--skip=%d", numberOfCommitsToSkip);

        assertThat(retrieveCommitsArguments).hasSize(5);
        assertThat(retrieveCommitsArguments[0]).isEqualTo("git");
        assertThat(retrieveCommitsArguments[1]).isEqualTo("log");
        assertThat(retrieveCommitsArguments[2]).isEqualTo("--pretty=%H-%cn-%ct-%s");
        assertThat(retrieveCommitsArguments[3]).isEqualTo(sizeArgument);
        assertThat(retrieveCommitsArguments[4]).isEqualTo(numberOfCommitsToSkipArgument);

    }

    @Test(expected = TechnicalException.class)
    public void exceptionCreatingTemporaryRepositoryFolder() throws IOException, InterruptedException {

        // Given
        var request = PortUtils.getRandomGitRepositoryCommitCliRequest();

        when(fileManager.createTemporaryFolder(any(String.class))).thenThrow(new IOException());

        // When
        adapter.getCommits(request);

    }

    @Test(expected = TechnicalException.class)
    public void exceptionCloningRepository() throws IOException, InterruptedException {

        // Given
        var request = PortUtils.getRandomGitRepositoryCommitCliRequest();

        var mockFile = mock(File.class);

        when(fileManager.createTemporaryFolder(any(String.class))).thenReturn(mockFile);

        when(cli.excuteCommand(any(String[].class), any(File.class))).thenThrow(new IOException());

        // When
        adapter.getCommits(request);

    }

    @Test(expected = TechnicalException.class)
    public void exceptionRetrievingRepositoryFolder() throws IOException, InterruptedException {

        // Given
        var request = PortUtils.getRandomGitRepositoryCommitCliRequest();

        var mockFile = mock(File.class);

        when(fileManager.createTemporaryFolder(any(String.class))).thenReturn(mockFile);

        when(cli.excuteCommand(any(String[].class), any(File.class))).thenReturn(Collections.emptyList())
                .thenThrow(new IOException());

        // When
        adapter.getCommits(request);

    }

    @Test(expected = TechnicalException.class)
    public void exceptionRetrievingRepositoryFolderMoreThanOneReturns() throws IOException, InterruptedException {

        // Given
        var request = PortUtils.getRandomGitRepositoryCommitCliRequest();

        var expectedProjectFolder1 = "expectedProjectFolder1";
        var expectedProjectFolder2 = "expectedProjectFolder2";

        File mockFile = mock(File.class);

        when(fileManager.createTemporaryFolder(any(String.class))).thenReturn(mockFile);

        when(cli.excuteCommand(any(String[].class), any(File.class))).thenReturn(Collections.emptyList())
                .thenReturn(Arrays.asList(expectedProjectFolder1, expectedProjectFolder2));

        // When
        adapter.getCommits(request);

    }

    @Test(expected = TechnicalException.class)
    public void exceptionRetrievingCommitList() throws IOException, InterruptedException {

        // Given
        var request = PortUtils.getRandomGitRepositoryCommitCliRequest();

        var expectedProjectFolder = "expectedProjectFolder";

        var mockFile = mock(File.class);

        when(fileManager.createTemporaryFolder(any(String.class))).thenReturn(mockFile);

        when(mockFile.getAbsolutePath()).thenReturn("absolutePath");

        when(cli.excuteCommand(any(String[].class), any(File.class))).thenReturn(Collections.emptyList())
                .thenReturn(Arrays.asList(expectedProjectFolder)).thenThrow(new IOException());

        // When
        adapter.getCommits(request);

    }

}
