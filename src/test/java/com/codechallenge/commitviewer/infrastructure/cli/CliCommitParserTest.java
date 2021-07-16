package com.codechallenge.commitviewer.infrastructure.cli;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class CliCommitParserTest {

    private static final String EXPECTED_COMMIT_FORMATTER = "%s-%s-%s-%s";

    @Test
    public void canParse() {

        // Given
        String sha = RandomString.make(10);
        String authorName = RandomString.make(10);

        LocalDateTime date = LocalDateTime.now().withNano(0);

        Long epochSeconds = date.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        String dateAsString = String.valueOf(epochSeconds);

        String message = RandomString.make(30);

        String commit = String.format(EXPECTED_COMMIT_FORMATTER, sha, authorName, dateAsString, message);

        // When
        var commitDto = CliCommitParser.parse(commit);

        // Then
        assertThat(commitDto).isNotNull();

        assertThat(commitDto.getSha()).isEqualTo(sha);
        assertThat(commitDto.getAuthorName()).isEqualTo(authorName);
        assertThat(commitDto.getDate()).isEqualTo(date);
        assertThat(commitDto.getMessage()).isEqualTo(message);

    }

}
