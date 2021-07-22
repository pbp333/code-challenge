package com.codechallenge.commitviewer.web.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class ErrorJsonTest {

    @Test
    public void canBuild() {

        // Given
        var message = RandomString.make(10);

        // When
        var errorJson = ErrorJson.from(message);

        // Then
        assertThat(errorJson).isNotNull();

        assertThat(errorJson.getMessage()).isEqualTo(message);

    }
}
