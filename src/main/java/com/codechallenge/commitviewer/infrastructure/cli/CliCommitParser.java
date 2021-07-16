package com.codechallenge.commitviewer.infrastructure.cli;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;

public class CliCommitParser {

    private static final int SHA_INDEX = 0;
    private static final int AUTHOR_NAME_INDEX = 1;
    private static final int DATE_INDEX = 2;
    private static final int MESSAGE_INDEX = 3;

    private static final String SPLITTER = "-";

    private CliCommitParser() {

    }

    public static CommitDto parse(String commit) {

        String[] commitElements = commit.split(SPLITTER);

        var builder = CommitDto.builder();

        builder.sha(commitElements[SHA_INDEX]);
        builder.authorName(commitElements[AUTHOR_NAME_INDEX]);
        builder.date(parseCommitDate(commitElements[DATE_INDEX]));
        builder.message(commitElements[MESSAGE_INDEX]);


        return builder.build();

    }

    private static LocalDateTime parseCommitDate(String commitDate) {

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.valueOf(commitDate)),
                TimeZone.getDefault().toZoneId());
    }

}
