package com.codechallenge.commitviewer.infrastructure.cli;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequestUtil;
import com.codechallenge.commitviewer.application.exception.TechnicalException;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

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
    public void canGetStrategy() {

        // When
        CommitRetriverStrategy strategy = adapter.getStrategy();

        // Then
        assertThat(strategy).isEqualTo(CommitRetriverStrategy.CLI);
    }

    @Test
    public void canGetCommits() throws IOException, InterruptedException {

        // Given
        PaginatedRequest<String> request = PaginatedRequestUtil.getRandom();

        var expectedProjectFolder = "expectedProjectFolder";

        String sha = RandomString.make(10);
        String authorName = RandomString.make(10);

        LocalDateTime date = LocalDateTime.now().withNano(0);

        Long epochSeconds = date.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        String dateAsString = String.valueOf(epochSeconds);

        String message = RandomString.make(30);

        String commit = String.format(EXPECTED_COMMIT_FORMATTER, sha, authorName, dateAsString, message);

        File mockFile = mock(File.class);

        when(fileManager.createTemporaryFolder(any(String.class))).thenReturn(mockFile);

        when(mockFile.getAbsolutePath()).thenReturn("absolutePath");

        when(cli.excuteCommand(any(String[].class), any(File.class))).thenReturn(Collections.emptyList())
                .thenReturn(Arrays.asList(expectedProjectFolder)).thenReturn(Arrays.asList(commit));

        when(fileManager.removeTemporaryFolderAndContents(any(File.class))).thenReturn(true);

        // When
        var response = adapter.getCommits(request);

        // Then
        assertThat(response).isNotNull().hasSize(1);

        assertThat(response.get(0).getSha()).isEqualTo(sha);
        assertThat(response.get(0).getAuthorName()).isEqualTo(authorName);
        assertThat(response.get(0).getDate()).isEqualTo(date);
        assertThat(response.get(0).getMessage()).isEqualTo(message);

    }

    @Test(expected = TechnicalException.class)
    public void exceptionCreatingTemporaryRepositoryFolder() throws IOException, InterruptedException {

        // Given
        PaginatedRequest<String> request = PaginatedRequestUtil.getRandom();

        when(fileManager.createTemporaryFolder(any(String.class))).thenThrow(new IOException());

        // When
        adapter.getCommits(request);

    }

    @Test(expected = TechnicalException.class)
    public void exceptionCloningRepository() throws IOException, InterruptedException {

        // Given
        PaginatedRequest<String> request = PaginatedRequestUtil.getRandom();

        File mockFile = mock(File.class);

        when(fileManager.createTemporaryFolder(any(String.class))).thenReturn(mockFile);

        when(cli.excuteCommand(any(String[].class), any(File.class))).thenThrow(new IOException());

        // When
        adapter.getCommits(request);

    }

    @Test(expected = TechnicalException.class)
    public void exceptionRetrievingRepositoryFolder() throws IOException, InterruptedException {

        // Given
        PaginatedRequest<String> request = PaginatedRequestUtil.getRandom();

        File mockFile = mock(File.class);

        when(fileManager.createTemporaryFolder(any(String.class))).thenReturn(mockFile);

        when(cli.excuteCommand(any(String[].class), any(File.class))).thenReturn(Collections.emptyList())
                .thenThrow(new IOException());

        // When
        adapter.getCommits(request);

    }

    @Test(expected = TechnicalException.class)
    public void exceptionRetrievingRepositoryFolderMoreThanOneReturns() throws IOException, InterruptedException {

        // Given
        PaginatedRequest<String> request = PaginatedRequestUtil.getRandom();

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
        PaginatedRequest<String> request = PaginatedRequestUtil.getRandom();

        var expectedProjectFolder = "expectedProjectFolder";

        File mockFile = mock(File.class);

        when(fileManager.createTemporaryFolder(any(String.class))).thenReturn(mockFile);

        when(mockFile.getAbsolutePath()).thenReturn("absolutePath");

        when(cli.excuteCommand(any(String[].class), any(File.class))).thenReturn(Collections.emptyList())
                .thenReturn(Arrays.asList(expectedProjectFolder)).thenThrow(new IOException());

        // When
        adapter.getCommits(request);

    }



}
