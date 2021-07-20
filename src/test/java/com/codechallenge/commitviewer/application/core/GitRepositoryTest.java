package com.codechallenge.commitviewer.application.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class GitRepositoryTest {

    @Test
    public void canBuild() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        var commits = Arrays.asList(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit());

        // When
        var repository = GitRepository.builder().ownerName(ownerName).name(name).commits(commits).build();

        // Then
        assertThat(repository).isNotNull();

        assertThat(repository.getOwnerName()).isEqualTo(ownerName);
        assertThat(repository.getName()).isEqualTo(name);

        assertThat(repository.getCommits()).containsAll(commits);

    }

    @Test
    public void canBuildWithCommitsNull() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);

        // When
        var repository = GitRepository.builder().ownerName(ownerName).name(name).commits(null).build();

        // Then
        assertThat(repository).isNotNull();

        assertThat(repository.getOwnerName()).isEqualTo(ownerName);
        assertThat(repository.getName()).isEqualTo(name);

        assertThat(repository.getCommits()).isNotNull().isEmpty();

    }

    @Test(expected = UnsupportedOperationException.class)
    public void exceptionModifyingCommitsInRepository() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        var commits = Arrays.asList(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit());

        var repository = GitRepository.builder().ownerName(ownerName).name(name).commits(commits).build();

        // When
        repository.getCommits().clear();
    }

    @Test
    public void modifyingExternalReferenceDoesNotModifyRepositoryCommits() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        List<Commit> commits = new ArrayList<>();

        commits.add(CoreUtils.getRandomCommit());
        commits.add(CoreUtils.getRandomCommit());

        var repository = GitRepository.builder().ownerName(ownerName).name(name).commits(commits).build();

        var expectedCommitsSize = repository.getCommits().size();

        // When
        commits.clear();

        // Then
        assertThat(repository.getCommits()).hasSize(expectedCommitsSize);

    }

}
