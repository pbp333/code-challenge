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


}
