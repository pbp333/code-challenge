package com.codechallenge.commitviewer.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.codechallenge.commitviewer.application.api.ApiUtils;
import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;
import com.codechallenge.commitviewer.application.core.Commit;
import com.codechallenge.commitviewer.application.core.CoreUtils;
import com.codechallenge.commitviewer.application.core.GitRepositoryRepository;
import com.codechallenge.commitviewer.application.exception.TechnicalException;
import com.codechallenge.commitviewer.application.port.cli.GitRepositoryCommitCliRequest;
import com.codechallenge.commitviewer.application.port.rest.GitRepositoryCommitRestRequest;
import com.codechallenge.commitviewer.infrastructure.cli.CliCommitRetrieverAdapter;
import com.codechallenge.commitviewer.infrastructure.rest.RestCommitRetrieverAdapter;

@RunWith(MockitoJUnitRunner.class)
public class CommitApplicationServiceImplTest {

    private static final String VALID_REPOSITORY_URL = "https://github.com/pbp333/code-challenge.git";

    @Mock
    private RestCommitRetrieverAdapter restAdapter;

    @Mock
    private CliCommitRetrieverAdapter cliAdapter;

    @Mock
    private GitRepositoryRepository repository;

    private CommitApplicationServiceImpl service;

    @Before
    public void setup() {
        this.service = new CommitApplicationServiceImpl(cliAdapter, restAdapter, repository);
    }

    @Test
    public void canGetCommitsFromRepository() {

        // Given
        var paginatedRequest = getValidUrlRequest();

        List<Commit> commits = Arrays.asList(CoreUtils.getRandomCommit());

        when(repository.findCommitsByRepositoryNameAndOwnerPaginated(any(String.class), any(String.class),
                any(Integer.class), any(Integer.class))).thenReturn(commits);

        // When
        List<CommitDto> retrievedCommits = service.getCommits(paginatedRequest);

        // Then
        assertThat(retrievedCommits).isNotNull().hasSize(commits.size());
    }

    @Test
    public void canGetCommitsFromRestWhenRepositoryHasGitRepositoryButNoCommits() {

        // Given
        var paginatedRequest = getValidUrlRequest();

        List<CommitDto> commits = Arrays.asList(ApiUtils.getRandomCommitDto());

        when(repository.findCommitsByRepositoryNameAndOwnerPaginated(any(String.class), any(String.class),
                any(Integer.class), any(Integer.class))).thenReturn(Collections.emptyList());
        when(repository.findByOwnerNameAndName(any(String.class), any(String.class))).thenReturn(
                Optional.of(CoreUtils.getRandomGitRepositoryBuilder().commits(Collections.emptySet()).build()));
        when(restAdapter.getCommits(any(GitRepositoryCommitRestRequest.class))).thenReturn(commits);

        // When
        List<CommitDto> retrievedCommits = service.getCommits(paginatedRequest);

        // Then
        assertThat(retrievedCommits).isNotNull().isNotEmpty().containsAll(commits);
    }

    @Test
    public void canGetCommitsFromRestWhenRepositoryHasNone() {

        // Given
        var paginatedRequest = getValidUrlRequest();

        List<CommitDto> commits = Arrays.asList(ApiUtils.getRandomCommitDto());

        when(repository.findCommitsByRepositoryNameAndOwnerPaginated(any(String.class), any(String.class),
                any(Integer.class), any(Integer.class))).thenReturn(Collections.emptyList());
        when(repository.findByOwnerNameAndName(any(String.class), any(String.class))).thenReturn(Optional.empty());
        when(restAdapter.getCommits(any(GitRepositoryCommitRestRequest.class))).thenReturn(commits);

        // When
        List<CommitDto> retrievedCommits = service.getCommits(paginatedRequest);

        // Then
        assertThat(retrievedCommits).isNotNull().isNotEmpty().containsAll(commits);
    }

    @Test
    public void canGetCommitsByCliWhenRestFailsAndRepositoryHasNone() {

        // Given
        var paginatedRequest = getValidUrlRequest();

        List<CommitDto> commits = Arrays.asList(ApiUtils.getRandomCommitDto());

        when(repository.findCommitsByRepositoryNameAndOwnerPaginated(any(String.class), any(String.class),
                any(Integer.class), any(Integer.class))).thenReturn(Collections.emptyList());
        when(repository.findByOwnerNameAndName(any(String.class), any(String.class))).thenReturn(Optional.empty());
        when(restAdapter.getCommits(any(GitRepositoryCommitRestRequest.class)))
                .thenThrow(new TechnicalException("message"));
        when(cliAdapter.getCommits(any(GitRepositoryCommitCliRequest.class))).thenReturn(commits);

        // When
        List<CommitDto> retrievedCommits = service.getCommits(paginatedRequest);

        // Then
        assertThat(retrievedCommits).isNotNull().isNotEmpty().containsAll(commits);
    }

    private PaginatedRequest<String> getValidUrlRequest() {

        String repositoryUrl = VALID_REPOSITORY_URL;
        int page = new Random().nextInt(100);
        int size = new Random().nextInt(100);

        return PaginatedRequest.<String>builder().request(repositoryUrl).page(page).size(size).build();
    }

}
