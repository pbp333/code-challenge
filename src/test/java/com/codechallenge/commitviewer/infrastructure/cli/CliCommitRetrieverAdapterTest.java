package com.codechallenge.commitviewer.infrastructure.cli;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

@RunWith(MockitoJUnitRunner.class)
public class CliCommitRetrieverAdapterTest {

    private static final String VALID_GITHUB_REPO_URL = "https://github.com/pbp333/code-challenge.git";

    private CliCommitRetrieverAdapter adapter;

    @Before
    public void setup() {
        adapter = new CliCommitRetrieverAdapter();
    }

    @Test
    public void canGetStrategy() {

        // When
        CommitRetriverStrategy strategy = adapter.getStrategy();

        // Then
        assertThat(strategy).isEqualTo(CommitRetriverStrategy.CLI);
    }

    @Test
    public void test() {

        String repositoryUrl = VALID_GITHUB_REPO_URL;
        int page = new Random().nextInt(100);
        int size = new Random().nextInt(100);

        var paginatedRequest = PaginatedRequest.<String>builder().request(repositoryUrl).page(page).size(size).build();

        var commits = adapter.getCommits(paginatedRequest);

        assertThat(commits).isNotEmpty();

    }

}
