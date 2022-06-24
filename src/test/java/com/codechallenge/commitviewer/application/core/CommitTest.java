package com.codechallenge.commitviewer.application.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

import com.codechallenge.commitviewer.application.core.exception.BusinessException;

public class CommitTest {

    @Test
    public void canBuild() {

        // Given
        var sha = RandomString.make(10);
        var message = RandomString.make(10);
        var date = Instant.now();
        var authorName = RandomString.make(10);

        // When
        var commit = Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

        // Then
        assertThat(commit).isNotNull();

        assertThat(commit.getSha()).isEqualTo(sha);
        assertThat(commit.getMessage()).isEqualTo(message);
        assertThat(commit.getDate()).isEqualTo(date);
        assertThat(commit.getAuthorName()).isEqualTo(authorName);

    }

    @Test
    public void canEquals() {

        // Given
        var sha = RandomString.make(10);
        var message = RandomString.make(10);
        var date = Instant.now();
        var authorName = RandomString.make(10);

        var commit = Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();
        // When
        var equals = commit.equals(commit);

        // Then
        assertThat(equals).isTrue();
    }

    @Test
    public void canEqualsNull() {

        // Given
        var sha = RandomString.make(10);
        var message = RandomString.make(10);
        var date = Instant.now();
        var authorName = RandomString.make(10);

        var commit = Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();
        // When
        var equals = commit.equals(null);

        // Then
        assertThat(equals).isFalse();
    }

    @Test
    public void canEqualsDifferentType() {

        // Given
        var sha = RandomString.make(10);
        var message = RandomString.make(10);
        var date = Instant.now();
        var authorName = RandomString.make(10);

        var commit = Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();
        // When
        var equals = commit.equals("");

        // Then
        assertThat(equals).isFalse();
    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenShaIsNull() {

        // Given
        String sha = null;
        var message = RandomString.make(10);
        var date = Instant.now();
        var authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenShaIsEmpty() {

        // Given
        var sha = "";
        var message = RandomString.make(10);
        var date = Instant.now();
        var authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenShaIsBlank() {

        // Given
        var sha = "     ";
        var message = RandomString.make(10);
        var date = Instant.now();
        var authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenMessageIsNull() {

        // Given
        var sha = RandomString.make(10);
        String message = null;
        var date = Instant.now();
        var authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenMessageIsEmpty() {

        // Given
        var sha = RandomString.make(10);
        var message = "";
        var date = Instant.now();
        var authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenMessageIsBlank() {

        // Given
        var sha = RandomString.make(10);
        var message = "      ";
        var date = Instant.now();
        var authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }


    @Test(expected = BusinessException.class)
    public void exceptionWhenDateIsNull() {

        // Given
        var sha = RandomString.make(10);
        var message = RandomString.make(10);
        Instant date = null;
        var authorName = RandomString.make(10);

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenAuthorNameIsNull() {

        // Given
        var sha = RandomString.make(10);
        var message = RandomString.make(10);
        var date = Instant.now();
        String authorName = null;

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenAuthorNameIsEmpty() {

        // Given
        var sha = RandomString.make(10);
        var message = RandomString.make(10);
        var date = Instant.now();
        var authorName = "";

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }

    @Test(expected = BusinessException.class)
    public void exceptionWhenAuthorNameIsBlank() {

        // Given
        var sha = RandomString.make(10);
        var message = RandomString.make(10);
        var date = Instant.now();
        var authorName = "     ";

        // When
        Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();

    }


}
