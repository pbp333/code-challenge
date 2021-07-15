package com.codechallenge.commitviewer.application.api.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class PaginatedRequestTest {

    @Test
    public void canBuild() {

        // Given
        String request = RandomString.make(10);
        int page = new Random().nextInt(100);
        int size = new Random().nextInt(100);

        // When
        var paginatedRequest = PaginatedRequest.<String>builder().request(request).page(page).size(size).build();

        // Then
        assertThat(paginatedRequest).isNotNull();

        assertThat(paginatedRequest.getRequest()).isEqualTo(request);
        assertThat(paginatedRequest.getPage()).isEqualTo(page);
        assertThat(paginatedRequest.getSize()).isEqualTo(size);

    }

}
