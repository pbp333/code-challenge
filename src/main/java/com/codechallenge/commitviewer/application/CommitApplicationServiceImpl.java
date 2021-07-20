package com.codechallenge.commitviewer.application;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codechallenge.commitviewer.application.api.CommitApplicationService;
import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;
import com.codechallenge.commitviewer.application.exception.ApplicationException;
import com.codechallenge.commitviewer.application.exception.TechnicalException;
import com.codechallenge.commitviewer.application.port.CommitRetrieverPort;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

@Service
public class CommitApplicationServiceImpl implements CommitApplicationService {

    private static final Logger LOGGER = Logger.getGlobal();

    private final CommitRetrieverFactory retrieverFactory;

    @Autowired
    public CommitApplicationServiceImpl(CommitRetrieverFactory retrieverFactory) {
        this.retrieverFactory = retrieverFactory;
    }

    @Override
    public List<CommitDto> getCommits(PaginatedRequest<String> request) {

        try {

            return getPortByStrategy(CommitRetriverStrategy.REST).getCommits(request);

        } catch (TechnicalException e) {

            LOGGER.warning("Failed retrieving commits with Rest Adapter.\nRetrying with Cli Adapter");
            return getPortByStrategy(CommitRetriverStrategy.CLI).getCommits(request);
        }

    }

    private CommitRetrieverPort getPortByStrategy(CommitRetriverStrategy strategy) {
        return retrieverFactory.getPort(strategy)
                .orElseThrow(() -> new ApplicationException("Commit Retriver Strategy is not implemented"));
    }

}
