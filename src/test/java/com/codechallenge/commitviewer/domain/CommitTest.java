package com.codechallenge.commitviewer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class CommitTest {

    @Test
    public void canBuild() {

        // Given
        String sha = RandomString.make(10);
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        // When
        Commit commit = Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

        // Then
        assertThat(commit).isNotNull();

        assertThat(commit.getSha()).isEqualTo(sha);
        assertThat(commit.getMessage()).isEqualTo(message);
        assertThat(commit.getDate()).isEqualTo(date);
        assertThat(commit.getAuthorName()).isEqualTo(authorName);

    }

    @Test(expected = IllegalStateException.class)
    public void exceptionWhenShaIsNull() {

        // Given
        String sha = null;
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = IllegalStateException.class)
    public void exceptionWhenShaIsEmpty() {

        // Given
        String sha = "";
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = IllegalStateException.class)
    public void exceptionWhenShaIsBlank() {

        // Given
        String sha = "     ";
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = IllegalStateException.class)
    public void exceptionWhenMessageIsNull() {

        // Given
        String sha = RandomString.make(10);
        String message = null;
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = IllegalStateException.class)
    public void exceptionWhenMessageIsEmpty() {

        // Given
        String sha = RandomString.make(10);
        String message = "";
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = IllegalStateException.class)
    public void exceptionWhenMessageIsBlank() {

        // Given
        String sha = RandomString.make(10);
        String message = "      ";
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }


    @Test(expected = IllegalStateException.class)
    public void exceptionWhenDateIsNull() {

        // Given
        String sha = RandomString.make(10);
        String message = RandomString.make(10);
        LocalDateTime date = null;
        String authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = IllegalStateException.class)
    public void exceptionWhenAuthorNameIsNull() {

        // Given
        String sha = RandomString.make(10);
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = null;

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = IllegalStateException.class)
    public void exceptionWhenAuthorNameIsEmpty() {

        // Given
        String sha = RandomString.make(10);
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = "";

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = IllegalStateException.class)
    public void exceptionWhenAuthorNameIsBlank() {

        // Given
        String sha = RandomString.make(10);
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = "     ";

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }


}
