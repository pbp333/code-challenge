package com.codechallenge.commitviewer.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.dto.CommitDtoUtil;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;
import com.codechallenge.commitviewer.infrastructure.rest.RestCommitRetrieverAdapter;

@RunWith(MockitoJUnitRunner.class)
public class CommitApplicationServiceImplTest {

    @Mock
    private RestCommitRetrieverAdapter restAdapter;

    private CommitApplicationServiceImpl service;

    @Before
    public void setup() {
        this.service = new CommitApplicationServiceImpl(restAdapter);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void canGetCommits() {

        // Given
        String repositoryUrl = "random_url";
        int page = new Random().nextInt(100);
        int size = new Random().nextInt(100);

        var paginatedRequest = PaginatedRequest.<String>builder().request(repositoryUrl).page(page).size(size).build();

        List<CommitDto> commits = Arrays.asList(CommitDtoUtil.getRandom());

        when(restAdapter.getCommits(any(PaginatedRequest.class))).thenReturn(commits);

        // When
        List<CommitDto> retrievedCommits = service.getCommits(paginatedRequest);

        // Then
        assertThat(retrievedCommits).isNotNull().isNotEmpty().containsAll(commits);
    }


}
