package com.codechallenge.commitviewer.application;

import com.codechallenge.commitviewer.application.port.CommitRetrieverPort;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

public interface CommitRetrieverFactory {
    
    CommitRetrieverPort getPort(CommitRetriverStrategy strategy);

}
