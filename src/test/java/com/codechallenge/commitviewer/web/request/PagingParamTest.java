package com.codechallenge.commitviewer.web.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Test;

public class PagingParamTest {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 5;

    @Test
    public void canBuildDefault() {

        // When
        var pagingParam = new PagingParam();

        // Then
        assertThat(pagingParam.getPage()).isEqualTo(DEFAULT_PAGE);
        assertThat(pagingParam.getSize()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    public void canBuild() {

        // Given
        int page = new Random().nextInt(100);
        int size = new Random().nextInt(100);

        // When
        var pagingParam = new PagingParam();

        pagingParam.setPage(page);
        pagingParam.setSize(size);

        // Then
        assertThat(pagingParam.getPage()).isEqualTo(page);
        assertThat(pagingParam.getSize()).isEqualTo(size);
    }

}
