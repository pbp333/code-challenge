package com.codechallenge.commitviewer.application.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class CommitDtoTest {

    @Test
    public void canBuild() {

        // Given
        String sha = RandomString.make(10);
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        // When
        CommitDto commit = CommitDto.builder().sha(sha).message(message).date(date).authorName(authorName).build();

        // Then
        assertThat(commit).isNotNull();

        assertThat(commit.getSha()).isEqualTo(sha);
        assertThat(commit.getMessage()).isEqualTo(message);
        assertThat(commit.getDate()).isEqualTo(date);
        assertThat(commit.getAuthorName()).isEqualTo(authorName);

    }

}
