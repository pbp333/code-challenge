package com.codechallenge.commitviewer.application;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codechallenge.commitviewer.application.api.CommitApplicationService;
import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;
import com.codechallenge.commitviewer.application.core.GitRepository;
import com.codechallenge.commitviewer.application.core.GitRepositoryRepository;
import com.codechallenge.commitviewer.application.exception.TechnicalException;
import com.codechallenge.commitviewer.application.port.PageRequest;
import com.codechallenge.commitviewer.application.port.cli.CommitRetrieverCliPort;
import com.codechallenge.commitviewer.application.port.cli.GitRepositoryCommitCliRequest;
import com.codechallenge.commitviewer.application.port.rest.CommitRetrieverRestPort;
import com.codechallenge.commitviewer.application.port.rest.GitRepositoryCommitRestRequest;

@Service
public class CommitApplicationServiceImpl implements CommitApplicationService {

    private static final Logger LOGGER = Logger.getGlobal();

    private final CommitRetrieverCliPort cliPort;
    private final CommitRetrieverRestPort restPort;
    private final GitRepositoryRepository repository;

    @Autowired
    public CommitApplicationServiceImpl(CommitRetrieverCliPort cliPort, CommitRetrieverRestPort restPort,
            GitRepositoryRepository repository) {
        this.cliPort = cliPort;
        this.restPort = restPort;
        this.repository = repository;
    }

    @Override
    public List<CommitDto> getCommits(PaginatedRequest<String> request) {

        var repositoryName = GitUrlUtils.extractRepositoryName(request.getRequest());
        var ownerName = GitUrlUtils.extractOwnerName(request.getRequest());

        // Rest Api initial page is 1, repository initial page is 0
        int repositoryPage = request.getPage() - 1;

        var commits = repository.findCommitsByRepositoryNameAndOwnerPaginated(repositoryName, ownerName, repositoryPage,
                request.getSize());

        if (!commits.isEmpty())
            return commits.stream().map(CommitMapper::map).collect(Collectors.toList());

        var gitRepository = repository.findByOwnerNameAndName(ownerName, repositoryName)
                .orElseGet(() -> GitRepository.builder().name(repositoryName).ownerName(ownerName).build());

        try {

            var restRequest = GitRepositoryCommitRestRequest.from(repositoryName, ownerName,
                    PageRequest.from(request.getPage(), request.getSize()));

            List<CommitDto> commitDtos = restPort.getCommits(restRequest);

            gitRepository.addCommits(commitDtos.stream().map(CommitMapper::map).collect(Collectors.toList()));

            repository.save(gitRepository);

            return commitDtos;

        } catch (TechnicalException e) {

            LOGGER.warning("Failed retrieving commits with Rest Adapter.\nRetrying with Cli Adapter");

            var cliRequest = GitRepositoryCommitCliRequest.from(request.getRequest(),
                    PageRequest.from(request.getPage(), request.getSize()));

            List<CommitDto> commitDtos = cliPort.getCommits(cliRequest);

            gitRepository.addCommits(commitDtos.stream().map(CommitMapper::map).collect(Collectors.toList()));

            repository.save(gitRepository);

            return commitDtos;
        }

    }

}
