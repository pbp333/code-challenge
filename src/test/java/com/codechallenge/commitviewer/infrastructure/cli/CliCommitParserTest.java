package com.codechallenge.commitviewer.infrastructure.cli;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class CliCommitParserTest {

    private static final String EXPECTED_COMMIT_FORMATTER = "%s-%s-%s-%s";

    @Test
    public void canParse() {

        // Given
        var sha = RandomString.make(10);
        var authorName = RandomString.make(10);

        var epochSeconds = Instant.now().getEpochSecond();
        var dateAsString = String.valueOf(epochSeconds);
        var expectedDate = Instant.ofEpochSecond(epochSeconds);

        var message = RandomString.make(30);

        var commit = String.format(EXPECTED_COMMIT_FORMATTER, sha, authorName, dateAsString, message);

        // When
        var commitDto = CliCommitParser.parse(commit);

        // Then
        assertThat(commitDto).isNotNull();

        assertThat(commitDto.getSha()).isEqualTo(sha);
        assertThat(commitDto.getAuthorName()).isEqualTo(authorName);
        assertThat(commitDto.getDate()).isEqualTo(expectedDate);
        assertThat(commitDto.getMessage()).isEqualTo(message);

    }

}
