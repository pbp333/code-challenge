package com.codechallenge.commitviewer.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codechallenge.commitviewer.application.core.GitRepository;

@Repository
public interface GitRepositoryJpaRepository extends JpaRepository<GitRepository, Long> {

    Optional<GitRepository> findByOwnerNameAndName(String ownerName, String name);

}
