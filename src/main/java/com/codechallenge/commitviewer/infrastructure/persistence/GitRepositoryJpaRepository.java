package com.codechallenge.commitviewer.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codechallenge.commitviewer.application.core.Commit;
import com.codechallenge.commitviewer.application.core.GitRepository;

@Repository
public interface GitRepositoryJpaRepository extends JpaRepository<GitRepository, Long> {

    Optional<GitRepository> findByOwnerNameAndName(String ownerName, String name);

    boolean existsByUrl(String url);

    Optional<GitRepository> findByUrl(String url);

    @Query("select c from GitRepository gr join gr.commits c where gr.url = :url order by c.date desc")
    List<Commit> findCommitsByRepositoryUrlPaginated(@Param("url") String url, Pageable pageable);

}
