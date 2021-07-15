package com.codechallenge.commitviewer.web.controller;

import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.codechallenge.commitviewer.application.api.CommitApplicationService;
import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.dto.CommitDtoUtil;

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

    @Test
    public void canGetCommits() throws Exception {

        // Given
        String repositoryUrl = "repositoryUrl";

        CommitDto commit = CommitDtoUtil.getStatic();

        // @formatter:off
        String expectedJson = 
                "["
                + " {"
                + "     \"sha\":\"sha-123456\","
                + "     \"message\":\"message 123\","
                + "     \"date\":\"2021-07-15T15:14:13.37318\","
                + "     \"authorName\":\"Author Name\""
                + " }"
                + "]";
        // @formatter:on

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        List<CommitDto> commits = Arrays.asList(commit);

        when(service.getCommits(any(String.class))).thenReturn(commits);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        requestParams.add("url", repositoryUrl);
        requestParams.add("page", "1");
        requestParams.add("size", "5");

        // When
        getMockMvc().perform(get(COMMITS_ENDPOINT).params(requestParams)).andExpect(status().isOk())
                .andExpect(json().isPresent()).andExpect(json().isEqualTo(expectedJson));

        // Then
        verify(service).getCommits(captor.capture());

        String serviceParamUrl = captor.getValue();

        assertThat(serviceParamUrl).isEqualTo(repositoryUrl);

    }

}
