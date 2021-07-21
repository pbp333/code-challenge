package com.codechallenge.commitviewer.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

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
    public void canSaveGitRepository() {

        // Given
        var gitRepositoryBuilder = CoreUtils.getRandomGitRepositoryBuilder();

        var commit = CoreUtils.getRandomCommit();

        gitRepositoryBuilder.commits(Arrays.asList(commit));

        var gitRepository = gitRepositoryBuilder.build();

        // When
        repository.save(gitRepository);

        // Then
        var gitRepositoryFromDBOptional =
                repository.findByOwnerNameAndName(gitRepository.getOwnerName(), gitRepository.getName());


        assertThat(gitRepositoryFromDBOptional).isNotNull().isPresent();

        var gitRepositoryFromDB = gitRepositoryFromDBOptional.get();

        assertThat(gitRepositoryFromDB.getOwnerName()).isEqualTo(gitRepository.getOwnerName());
        assertThat(gitRepositoryFromDB.getName()).isEqualTo(gitRepository.getName());

        assertThat(gitRepositoryFromDB.getCommits()).hasSize(gitRepository.getCommits().size()).hasSize(1);

        assertThat(gitRepositoryFromDB.getCommits().get(0)).satisfies(dbCommit -> {
            assertThat(dbCommit.getSha()).isEqualTo(commit.getSha());
            assertThat(dbCommit.getAuthorName()).isEqualTo(commit.getAuthorName());
            assertThat(dbCommit.getDate()).isEqualTo(commit.getDate());
            assertThat(dbCommit.getMessage()).isEqualTo(commit.getMessage());
        });

        repository.delete(gitRepositoryFromDB);
    }

    @Test
    public void canDelete() {

        // Given
        var gitRepositoryBuilder = CoreUtils.getRandomGitRepositoryBuilder();

        var commit = CoreUtils.getRandomCommit();

        gitRepositoryBuilder.commits(Arrays.asList(commit));

        var gitRepository = gitRepositoryBuilder.build();

        repository.save(gitRepository);

        var gitRepositoryFromDB =
                repository.findByOwnerNameAndName(gitRepository.getOwnerName(), gitRepository.getName());

        // When
        repository.delete(gitRepositoryFromDB.get());

        // Then
        var gitRepositoryFromDBAfterDElete =
                repository.findByOwnerNameAndName(gitRepository.getOwnerName(), gitRepository.getName());

        assertThat(gitRepositoryFromDBAfterDElete).isNotNull().isNotPresent();

    }


}
