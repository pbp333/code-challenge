package com.codechallenge.commitviewer.infrastructure.rest;

import com.codechallenge.commitviewer.application.port.CommitRetrieverPort;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

public class RestCommitRetrieverAdapter implements CommitRetrieverPort{

    @Override
    public CommitRetriverStrategy getStrategy() {
        // TODO Auto-generated method stub
        return CommitRetriverStrategy.REST;
    }

}
