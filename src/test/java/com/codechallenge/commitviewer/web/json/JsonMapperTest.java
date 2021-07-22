package com.codechallenge.commitviewer.web.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.codechallenge.commitviewer.application.api.ApiUtils;

public class JsonMapperTest {

    @Test
    public void canMap() {

        // Given
        var dto = ApiUtils.getRandomCommitDto();

        // When
        var json = JsonMapper.map(dto);

        // Then
        assertThat(json).isNotNull();

        assertThat(json.getSha()).isEqualTo(dto.getSha());
        assertThat(json.getAuthorName()).isEqualTo(dto.getAuthorName());
        assertThat(json.getDate()).isEqualTo(dto.getDate());
        assertThat(json.getMessage()).isEqualTo(dto.getMessage());

    }
}
