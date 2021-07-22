package com.codechallenge.commitviewer.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.codechallenge.commitviewer.application.core.CoreUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitRepositoryRepositoryImplIntegrationTest {

    @Autowired
    private GitRepositoryRepositoryImpl repository;

    @Test
    public void canSaveAndFindGitRepository() {

        // Given
        var gitRepositoryBuilder = CoreUtils.getRandomGitRepositoryBuilder();

        var commit = CoreUtils.getRandomCommit();

        gitRepositoryBuilder.commits(Collections.singleton(commit));

        var gitRepository = gitRepositoryBuilder.build();

        // When
        repository.save(gitRepository);

        var gitRepositoryExists = repository.existsByUrl(gitRepository.getUrl());

        var gitRepositoryFromDBOptional = repository.findByUrl(gitRepository.getUrl());

        // Then
        assertThat(gitRepositoryExists).isTrue();

        var gitRepositoryFromDB = gitRepositoryFromDBOptional.get();

        assertThat(gitRepositoryFromDB.getOwnerName()).isEqualTo(gitRepository.getOwnerName());
        assertThat(gitRepositoryFromDB.getName()).isEqualTo(gitRepository.getName());

        assertThat(gitRepositoryFromDB.getCommits()).hasSize(gitRepository.getCommits().size()).hasSize(1);

        assertThat(gitRepositoryFromDB.getCommits().stream().findAny().get()).satisfies(dbCommit -> {
            assertThat(dbCommit.getId()).isNotNull();
            assertThat(dbCommit.getSha()).isEqualTo(commit.getSha());
            assertThat(dbCommit.getAuthorName()).isEqualTo(commit.getAuthorName());
            assertThat(dbCommit.getDate()).isEqualTo(commit.getDate());
            assertThat(dbCommit.getMessage()).isEqualTo(commit.getMessage());
        });

        repository.delete(gitRepositoryFromDB);
    }

    @Test
    public void canFindCommitsByGitRepositoryUrl() {

        // Given
        var gitRepositoryBuilder = CoreUtils.getRandomGitRepositoryBuilder();

        var commit = CoreUtils.getRandomCommit();

        gitRepositoryBuilder.commits(Collections.singleton(commit));

        var gitRepository = gitRepositoryBuilder.build();

        var gitRepositoryFromDB = repository.save(gitRepository);

        int page = 1;
        int size = 10;

        // When
        var commits = repository.findCommitsByRepositoryUrlPaginated(gitRepository.getUrl(), page, size);

        // Then
        assertThat(commits).isNotNull().hasSize(1);

        assertThat(commits.stream().findAny().get()).satisfies(dbCommit -> {
            assertThat(dbCommit.getId()).isNotNull();
            assertThat(dbCommit.getSha()).isEqualTo(commit.getSha());
            assertThat(dbCommit.getAuthorName()).isEqualTo(commit.getAuthorName());
            assertThat(dbCommit.getDate()).isEqualTo(commit.getDate());
            assertThat(dbCommit.getMessage()).isEqualTo(commit.getMessage());
        });

        repository.delete(gitRepositoryFromDB);
    }

    @Test
    public void canFindCommitsByGitRepositoryUrlResultsAreOrderedByDateDesc() {

        // Given
        var gitRepositoryBuilder = CoreUtils.getRandomGitRepositoryBuilder();

        var commit1 = CoreUtils.getRandomCommitBuilder()
                .date(LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC)).build();
        var commit2 = CoreUtils.getRandomCommitBuilder().date(Instant.now()).build();

        gitRepositoryBuilder.commits(Stream.of(commit1, commit2).collect(Collectors.toSet()));

        var gitRepository = gitRepositoryBuilder.build();

        var gitRepositoryFromDB = repository.save(gitRepository);

        int page = 1;
        int size = 10;

        // When
        var commits = repository.findCommitsByRepositoryUrlPaginated(gitRepository.getUrl(), page, size);

        // Then
        assertThat(commits).isNotNull().hasSize(2);

        assertThat(commits.stream().findFirst().get()).satisfies(dbCommit -> {
            assertThat(dbCommit.getId()).isNotNull();
            assertThat(dbCommit.getSha()).isEqualTo(commit2.getSha());
            assertThat(dbCommit.getAuthorName()).isEqualTo(commit2.getAuthorName());
            assertThat(dbCommit.getDate()).isEqualTo(commit2.getDate());
            assertThat(dbCommit.getMessage()).isEqualTo(commit2.getMessage());
        });

        repository.delete(gitRepositoryFromDB);
    }

    @Test
    public void canFindCommitsByGitRepositoryUrlPaginated() {

        // Given
        var gitRepositoryBuilder = CoreUtils.getRandomGitRepositoryBuilder();

        gitRepositoryBuilder
                .commits(new HashSet<>(Arrays.asList(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit())));

        var gitRepository = gitRepositoryBuilder.build();

        var gitRepositoryFromDB = repository.save(gitRepository);

        int page = 1;
        int size = 1;

        // When
        var commits = repository.findCommitsByRepositoryUrlPaginated(gitRepository.getUrl(), page, size);

        // Then
        assertThat(commits).isNotNull().hasSize(1);

        repository.delete(gitRepositoryFromDB);
    }

    @Test
    public void canDelete() {

        // Given
        var gitRepositoryBuilder = CoreUtils.getRandomGitRepositoryBuilder();

        var commit = CoreUtils.getRandomCommit();

        gitRepositoryBuilder.commits(Collections.singleton(commit));

        var gitRepository = gitRepositoryBuilder.build();

        repository.save(gitRepository);

        var gitRepositoryFromDB = repository.findByUrl(gitRepository.getUrl());

        // When
        repository.delete(gitRepositoryFromDB.get());

        // Then
        var gitRepositoryFromDBAfterDElete = repository.findByUrl(gitRepository.getUrl());

        assertThat(gitRepositoryFromDBAfterDElete).isNotNull().isNotPresent();

    }


}
