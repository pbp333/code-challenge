package com.codechallenge.commitviewer.application;

import java.util.Optional;

import com.codechallenge.commitviewer.application.port.CommitRetrieverPort;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

public interface CommitRetrieverFactory {

    Optional<CommitRetrieverPort> getPort(CommitRetriverStrategy strategy);

}
