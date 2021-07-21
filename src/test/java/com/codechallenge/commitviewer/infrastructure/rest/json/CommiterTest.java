package com.codechallenge.commitviewer.infrastructure.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class CommiterTest {

    @Test
    public void canBuild() {

        // Given
        String name = RandomString.make(10);
        String email = RandomString.make(10);
        Instant date = Instant.now();

        // When
        Committer commiter = new Committer();

        commiter.setName(name);
        commiter.setEmail(email);
        commiter.setDate(date);

        // Then
        assertThat(commiter.getName()).isEqualTo(name);
        assertThat(commiter.getEmail()).isEqualTo(email);
        assertThat(commiter.getDate()).isEqualTo(date);
    }

}
