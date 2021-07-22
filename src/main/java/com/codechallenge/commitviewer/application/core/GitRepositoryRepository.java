package com.codechallenge.commitviewer.application.core;

import java.util.List;
import java.util.Optional;

public interface GitRepositoryRepository {

    GitRepository save(GitRepository gitRepository);

    void delete(GitRepository gitRepository);

    boolean existsByUrl(String url);

    Optional<GitRepository> findByUrl(String url);

    List<Commit> findCommitsByRepositoryUrlPaginated(String url, int page, int size);

}
