package com.codechallenge.commitviewer.application.core;

import java.util.Optional;

public interface GitRepositoryRepository {

    GitRepository save(GitRepository gitRepository);

    Optional<GitRepository> findByOwnerNameAndName(String ownerName, String name);

    void delete(GitRepository gitRepository);

}
