package com.codechallenge.commitviewer.application.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

import com.codechallenge.commitviewer.application.core.exception.BusinessException;

public class GitRepositoryTest {

    @Test
    public void canBuild() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        var url = RandomString.make(10);
        var commits = Stream.of(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit()).collect(Collectors.toSet());

        // When
        var repository = GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

        // Then
        assertThat(repository).isNotNull();

        assertThat(repository.getOwnerName()).isEqualTo(ownerName);
        assertThat(repository.getName()).isEqualTo(name);
        assertThat(repository.getUrl()).isEqualTo(url);

        assertThat(repository.getCommits()).containsAll(commits);

    }

    @Test
    public void canAddCommits() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        var url = RandomString.make(10);
        var commits = Stream.of(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit()).collect(Collectors.toSet());

        var repository = GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

        var commit = CoreUtils.getRandomCommit();

        // When
        repository.addCommits(Arrays.asList(commit));

        // Then
        assertThat(repository.getCommits()).contains(commit);

    }

    @Test
    public void canAddCommitsOnlyUniqueAreAdded() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        var url = RandomString.make(10);

        var commit1 = CoreUtils.getRandomCommit();
        var commit2 = CoreUtils.getRandomCommit();

        var commits = Stream.of(commit1, commit2).collect(Collectors.toSet());

        var repository = GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

        int originalCommitsSize = repository.getCommits().size();

        // When
        repository.addCommits(Arrays.asList(commit1));

        // Then
        assertThat(repository.getCommits()).hasSize(originalCommitsSize);

    }

    @Test
    public void canBuildWithCommitsNull() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        var url = RandomString.make(10);

        // When
        var repository = GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(null).build();

        // Then
        assertThat(repository).isNotNull();

        assertThat(repository.getOwnerName()).isEqualTo(ownerName);
        assertThat(repository.getName()).isEqualTo(name);

        assertThat(repository.getCommits()).isNotNull().isEmpty();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenNameIsNull() {

        // Given
        var ownerName = RandomString.make(10);
        String name = null;
        var url = RandomString.make(10);
        var commits = Stream.of(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit()).collect(Collectors.toSet());

        // When
        GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenNameIsEmpty() {

        // Given
        var ownerName = RandomString.make(10);
        var name = "";
        var url = RandomString.make(10);
        var commits = Stream.of(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit()).collect(Collectors.toSet());

        // When
        GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenOwnerNameIsNull() {

        // Given
        String ownerName = null;
        var name = RandomString.make(10);
        var url = RandomString.make(10);
        var commits = Stream.of(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit()).collect(Collectors.toSet());

        // When
        GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenOwnerNameIsEmpty() {

        // Given
        var ownerName = "";
        var name = RandomString.make(10);
        var url = RandomString.make(10);
        var commits = Stream.of(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit()).collect(Collectors.toSet());

        // When
        GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenUrlIsNull() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        String url = null;
        var commits = Stream.of(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit()).collect(Collectors.toSet());

        // When
        GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenUrlIsEmpty() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        var url = "";
        var commits = Stream.of(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit()).collect(Collectors.toSet());

        // When
        GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

    }

    @Test(expected = UnsupportedOperationException.class)
    public void exceptionModifyingCommitsInRepository() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        var url = RandomString.make(10);
        var commits = Stream.of(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit()).collect(Collectors.toSet());

        var repository = GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

        // When
        repository.getCommits().clear();
    }

    @Test
    public void modifyingExternalReferenceDoesNotModifyRepositoryCommits() {

        // Given
        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        var url = RandomString.make(10);
        Set<Commit> commits = new HashSet<>();

        commits.add(CoreUtils.getRandomCommit());
        commits.add(CoreUtils.getRandomCommit());

        var repository = GitRepository.builder().ownerName(ownerName).name(name).url(url).commits(commits).build();

        var expectedCommitsSize = repository.getCommits().size();

        // When
        commits.clear();

        // Then
        assertThat(repository.getCommits()).hasSize(expectedCommitsSize);

    }

}
