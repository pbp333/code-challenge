package com.codechallenge.commitviewer.infrastructure.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Optional<GitRepository> findByOwnerNameAndName(String ownerName, String name) {
        return jpaRepository.findByOwnerNameAndName(ownerName, name);
    }

    @Override
    public void delete(GitRepository gitRepository) {
        jpaRepository.delete(gitRepository);
    }
}
