package com.codechallenge.commitviewer.infrastructure.cli;

import com.codechallenge.commitviewer.application.port.CommitRetrieverPort;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

public class CliCommitRetrieverAdapter implements CommitRetrieverPort{

    @Override
    public CommitRetriverStrategy getStrategy() {
        return CommitRetriverStrategy.CLI;
    }

}
