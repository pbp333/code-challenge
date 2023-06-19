package com.codechallenge.commitviewer.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codechallenge.commitviewer.application.core.Commit;
import com.codechallenge.commitviewer.application.core.GitRepository;
import com.codechallenge.commitviewer.application.core.GitRepositoryRepository;

@Service
public class GitRepositoryRepositoryImpl implements GitRepositoryRepository {

    private final GitRepositoryJpaRepository jpaRepository;

    @Autowired
    public GitRepositoryRepositoryImpl(GitRepositoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public GitRepository save(GitRepository gitRepository) {
        return jpaRepository.save(gitRepository);
    }

    @Override
    public void delete(GitRepository gitRepository) {
        jpaRepository.delete(gitRepository);
    }

    @Override
    public boolean existsByUrl(String url) {
        return jpaRepository.existsByUrl(url);
    }

    @Override
    public Optional<GitRepository> findByUrl(String url) {
        return jpaRepository.findByUrl(url);
    }

    @Override
    public List<Commit> findCommitsByRepositoryUrlPaginated(String url, int page, int size) {

        // DB initial page is 0
        var repositoryPage = page - 1;

        var pageable = PageRequest.of(repositoryPage, size);

        return jpaRepository.findCommitsByRepositoryUrlPaginated(url, pageable);
    }
}
