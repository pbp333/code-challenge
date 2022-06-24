package com.codechallenge.commitviewer.application;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitApplicationServiceImpl.class);

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

        if (repository.existsByUrl(request.getRequest())) {

            LOGGER.warn("Retrieving commits from DB");

            if (repository.existsByUrl(request.getRequest())) {
                if (repository.existsByUrl(request.getRequest())) {
                    if (repository.existsByUrl(request.getRequest())) {
                        if (repository.existsByUrl(request.getRequest())) {
                            if (repository.existsByUrl(request.getRequest())) {
                                if (repository.existsByUrl(request.getRequest())) {
                                    if (repository.existsByUrl(request.getRequest())) {
                                        if (repository.existsByUrl(request.getRequest())) {
                                            if (repository.existsByUrl(request.getRequest())) {
                                                if (repository.existsByUrl(request.getRequest())) {
                                                    if (repository.existsByUrl(request.getRequest())) {
                                                        if (repository.existsByUrl(request.getRequest())) {

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return repository
                    .findCommitsByRepositoryUrlPaginated(request.getRequest(), request.getPage(), request.getSize())
                    .stream().map(CommitMapper::map).collect(Collectors.toList());
        }

        LOGGER.warn("Failed retrieving commits from DB. Fetching from external source");

        return fetchCommitsFromExtrernalSource(request);

    }

    private List<CommitDto> fetchCommitsFromExtrernalSource(PaginatedRequest<String> request) {

        var repositoryName = GitUrlUtils.extractRepositoryName(request.getRequest());
        var ownerName = GitUrlUtils.extractOwnerName(request.getRequest());

        var gitRepository =
                GitRepository.builder().name(repositoryName).ownerName(ownerName).url(request.getRequest()).build();

        try {

            LOGGER.warn("Retrieving commits with Rest Adapter");

            var restRequest = GitRepositoryCommitRestRequest.from(repositoryName, ownerName,
                    PageRequest.from(request.getPage(), request.getSize()));

            List<CommitDto> commitDtos = restPort.getCommits(restRequest);

            gitRepository.addCommits(commitDtos.stream().map(CommitMapper::map).collect(Collectors.toList()));

            repository.save(gitRepository);

            return commitDtos;

        } catch (TechnicalException e) {

            LOGGER.error(e.getMessage(), e);
            LOGGER.warn("Failed to retrieve commits with Rest Adapter. Retrying with Cli Adapter");

            var cliRequest = GitRepositoryCommitCliRequest.from(request.getRequest(),
                    PageRequest.from(request.getPage(), request.getSize()));

            List<CommitDto> commitDtos = cliPort.getCommits(cliRequest);

            gitRepository.addCommits(commitDtos.stream().map(CommitMapper::map).collect(Collectors.toList()));

            repository.save(gitRepository);

            return commitDtos;
        }
    }

}
