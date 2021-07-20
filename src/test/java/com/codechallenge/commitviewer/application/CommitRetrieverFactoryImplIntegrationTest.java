package com.codechallenge.commitviewer.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.codechallenge.commitviewer.application.port.CommitRetrieverFactoryImpl;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;
import com.codechallenge.commitviewer.infrastructure.cli.CliCommitRetrieverAdapter;
import com.codechallenge.commitviewer.infrastructure.rest.RestCommitRetrieverAdapter;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class CommitRetrieverFactoryImplIntegrationTest {

    @Autowired
    private CommitRetrieverFactoryImpl factoryImpl;

    @Test
    public void canGetRestRetriverPort() {

        // Given
        var strategy = CommitRetriverStrategy.REST;

        // When
        var adapter = factoryImpl.getPort(strategy);

        // Then
        assertThat(adapter).isNotNull().isNotEmpty()
                .hasValueSatisfying(port -> assertThat(port).isInstanceOf(RestCommitRetrieverAdapter.class));

    }

    @Test
    public void canGetCliRetriverPort() {

        // Given
        var strategy = CommitRetriverStrategy.CLI;

        // When
        var adapter = factoryImpl.getPort(strategy);

        // Then
        assertThat(adapter).isNotNull().isNotEmpty()
                .hasValueSatisfying(port -> assertThat(port).isInstanceOf(CliCommitRetrieverAdapter.class));

    }

    @Test
    public void emptyOptionalWhenStrategyIsNotImplemented() {

        // Given
        CommitRetriverStrategy strategy = null;

        // When
        var adapter = factoryImpl.getPort(strategy);

        // Then
        assertThat(adapter).isNotNull().isEmpty();

    }

}
