package com.codechallenge.commitviewer.infrastructure.cli;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.codechallenge.commitviewer.application.exception.TechnicalException;
import com.codechallenge.commitviewer.application.port.PortUtils;

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


}
