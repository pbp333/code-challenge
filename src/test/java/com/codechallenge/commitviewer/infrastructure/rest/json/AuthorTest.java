package com.codechallenge.commitviewer.infrastructure.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class AuthorTest {

    @Test
    public void canBuild() {

        // Given
        String name = RandomString.make(10);
        String email = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();

        // When
        Author author = new Author();

        author.setName(name);
        author.setEmail(email);
        author.setDate(date);

        // Then
        assertThat(author.getName()).isEqualTo(name);
        assertThat(author.getEmail()).isEqualTo(email);
        assertThat(author.getDate()).isEqualTo(date);
    }

}
