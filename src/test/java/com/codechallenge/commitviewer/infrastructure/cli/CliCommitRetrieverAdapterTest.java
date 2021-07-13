package com.codechallenge.commitviewer.infrastructure.cli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

@RunWith(MockitoJUnitRunner.class)
public class CliCommitRetrieverAdapterTest {

    CliCommitRetrieverAdapter adapter;

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

}
