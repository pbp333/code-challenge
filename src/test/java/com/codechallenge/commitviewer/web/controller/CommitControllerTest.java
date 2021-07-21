package com.codechallenge.commitviewer.web.controller;

import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.codechallenge.commitviewer.application.api.ApiUtils;
import com.codechallenge.commitviewer.application.api.CommitApplicationService;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;

public class CommitControllerTest extends AbstractControllerTest<CommitController> {

    private static final String COMMITS_ENDPOINT = "/commits";

    @Mock
    private CommitApplicationService service;

    @InjectMocks
    private CommitController controller;

    @Override
    protected CommitController getControllerUnderTest() {
        return controller;
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void canGetCommitsWithPagination() throws Exception {

        // Given
        var repositoryUrl = "repositoryUrl";
        int page = 1;
        int size = 10;

        var commit = ApiUtils.getStaticCommitDto();

        // @formatter:off
        String expectedJson = 
                "["
                + " {"
                + "     \"sha\":\"sha-123456\","
                + "     \"message\":\"message 123\","
                + "     \"date\":\"2021-07-15T15:14:13.373180Z\","
                + "     \"authorName\":\"Author Name\""
                + " }"
                + "]";
        // @formatter:on

        var captor = ArgumentCaptor.forClass(PaginatedRequest.class);

        var commits = Arrays.asList(commit);

        when(service.getCommits(any(PaginatedRequest.class))).thenReturn(commits);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        requestParams.add("url", repositoryUrl);
        requestParams.add("page", String.valueOf(page));
        requestParams.add("size", String.valueOf(size));

        // When
        getMockMvc().perform(get(COMMITS_ENDPOINT).params(requestParams)).andExpect(status().isOk())
                .andExpect(json().isPresent()).andExpect(json().isEqualTo(expectedJson));

        // Then
        verify(service).getCommits(captor.capture());

        PaginatedRequest<String> serviceRequest = captor.getValue();

        assertThat(serviceRequest.getRequest()).isEqualTo(repositoryUrl);
        assertThat(serviceRequest.getPage()).isEqualTo(page);
        assertThat(serviceRequest.getSize()).isEqualTo(size);

    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void canGetCommitsWithoutPagination() throws Exception {

        // Given
        var repositoryUrl = "repositoryUrl";
        int defaultPage = 1;
        int defaultSize = 5;

        var commit = ApiUtils.getStaticCommitDto();

        // @formatter:off
        String expectedJson = 
                "["
                + " {"
                + "     \"sha\":\"sha-123456\","
                + "     \"message\":\"message 123\","
                + "     \"date\":\"2021-07-15T15:14:13.373180Z\","
                + "     \"authorName\":\"Author Name\""
                + " }"
                + "]";
        // @formatter:on

        var captor = ArgumentCaptor.forClass(PaginatedRequest.class);

        var commits = Arrays.asList(commit);

        when(service.getCommits(any(PaginatedRequest.class))).thenReturn(commits);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        requestParams.add("url", repositoryUrl);

        // When
        getMockMvc().perform(get(COMMITS_ENDPOINT).params(requestParams)).andExpect(status().isOk())
                .andExpect(json().isPresent()).andExpect(json().isEqualTo(expectedJson));

        // Then
        verify(service).getCommits(captor.capture());

        PaginatedRequest<String> serviceRequest = captor.getValue();

        assertThat(serviceRequest.getRequest()).isEqualTo(repositoryUrl);
        assertThat(serviceRequest.getPage()).isEqualTo(defaultPage);
        assertThat(serviceRequest.getSize()).isEqualTo(defaultSize);

    }

}
