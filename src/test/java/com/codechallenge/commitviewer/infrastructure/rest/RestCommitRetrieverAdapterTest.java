package com.codechallenge.commitviewer.infrastructure.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;
import com.codechallenge.commitviewer.infrastructure.rest.json.GitHubCommitResponse;
import com.codechallenge.commitviewer.infrastructure.rest.json.JsonUtil;

@RunWith(MockitoJUnitRunner.class)
public class RestCommitRetrieverAdapterTest {

    private static final String VALID_GITHUB_REPO_URL = "https://github.com/pbp333/code-challenge.git";

    @Mock
    private RestTemplate restTemplate;

    private RestCommitRetrieverAdapter adapter;

    @Before
    public void setup() throws Exception {
        adapter = new RestCommitRetrieverAdapter();

        // TODO change architecture to avoid using reflection
        Field adapterRestTemplate = adapter.getClass().getDeclaredField("restTemplate");
        adapterRestTemplate.setAccessible(true);

        adapterRestTemplate.set(adapter, restTemplate);
    }

    @Test
    public void canGetStrategy() {

        // When
        CommitRetriverStrategy strategy = adapter.getStrategy();

        // Then
        assertThat(strategy).isEqualTo(CommitRetriverStrategy.REST);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void canGetCommits() {

        // Given
        String repositoryUrl = VALID_GITHUB_REPO_URL;

        ResponseEntity<GitHubCommitResponse[]> response = mock(ResponseEntity.class);

        GitHubCommitResponse[] gitHubResponse = {JsonUtil.getRandomGitHubCommitResponse()};

        when(restTemplate.exchange(isA(String.class), eq(HttpMethod.GET), isA(HttpEntity.class),
                eq(GitHubCommitResponse[].class))).thenReturn(response);

        when(response.getBody()).thenReturn(gitHubResponse);

        // When
        List<CommitDto> commits = adapter.getCommits(repositoryUrl);

        // Then
        assertThat(commits).isNotNull().hasSize(gitHubResponse.length);

    }

}
