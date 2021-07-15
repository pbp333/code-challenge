package com.codechallenge.commitviewer.infrastructure.rest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.port.CommitRetrieverPort;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;
import com.codechallenge.commitviewer.infrastructure.rest.json.GitHubCommitResponse;

@Service
public class RestCommitRetrieverAdapter implements CommitRetrieverPort {

    private final RestTemplate restTemplate;

    @Autowired
    public RestCommitRetrieverAdapter() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public CommitRetriverStrategy getStrategy() {
        return CommitRetriverStrategy.REST;
    }

    @Override
    public List<CommitDto> getCommits(String repositoryUrl) {

        // TODO add pagination

        try {

            String commitsApiUrl = GitHubApiUtil.buildCommitsApiUrlFromRepositoryUrl(repositoryUrl);

            ResponseEntity<GitHubCommitResponse[]> response = restTemplate.exchange(commitsApiUrl, HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()), GitHubCommitResponse[].class);

            GitHubCommitResponse[] body = response.getBody();

            return Arrays.stream(body).map(GitHubJsonMapper::map).collect(Collectors.toList());

        } catch (Exception e) {
            // TODO: handle http exception
            throw e;
        }

    }


}
