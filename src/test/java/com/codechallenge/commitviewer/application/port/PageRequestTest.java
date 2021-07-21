package com.codechallenge.commitviewer.application.port;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Test;

import com.codechallenge.commitviewer.application.exception.ApplicationException;

public class PageRequestTest {

    @Test
    public void canBuild() {

        // Given
        int page = new Random().nextInt(100) + 1;
        int size = new Random().nextInt(100) + 1;

        // When
        var pageRequest = PageRequest.from(page, size);

        // Then
        assertThat(pageRequest).isNotNull();

        assertThat(pageRequest.getPage()).isEqualTo(page);
        assertThat(pageRequest.getSize()).isEqualTo(size);
    }

    @Test(expected = ApplicationException.class)
    public void exceptionWhenPageIsNegative() {

        // Given
        int page = -1;
        int size = new Random().nextInt(100) + 1;

        // When
        PageRequest.from(page, size);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionWhenPageIsZero() {

        // Given
        int page = 0;
        int size = new Random().nextInt(100) + 1;

        // When
        PageRequest.from(page, size);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionWhenSizeIsNegative() {

        // Given
        int page = new Random().nextInt(100) + 1;
        int size = -1;

        // When
        PageRequest.from(page, size);

    }

    @Test(expected = ApplicationException.class)
    public void exceptionWhenSizeIsZero() {

        // Given
        int page = new Random().nextInt(100) + 1;
        int size = 0;

        // When
        PageRequest.from(page, size);

    }

}
