package com.codechallenge.commitviewer.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.dto.CommitDtoUtil;
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

    @Test
    public void canGetCommits() {

        // Given
        String repositoryUrl = "random_url";

        List<CommitDto> commits = Arrays.asList(CommitDtoUtil.getRandom());

        when(restAdapter.getCommits(any(String.class))).thenReturn(commits);

        // When
        List<CommitDto> retrievedCommits = service.getCommits(repositoryUrl);

        // Then
        assertThat(retrievedCommits).isNotNull().isNotEmpty().containsAll(commits);
    }


}
