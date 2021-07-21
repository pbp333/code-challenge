package com.codechallenge.commitviewer.application.core;

import java.util.List;
import java.util.Optional;

public interface GitRepositoryRepository {

    GitRepository save(GitRepository gitRepository);

    Optional<GitRepository> findByOwnerNameAndName(String ownerName, String name);

    void delete(GitRepository gitRepository);

    List<Commit> findCommitsByRepositoryNameAndOwnerPaginated(String repositoryName, String ownerName, int page,
            int size);

}
