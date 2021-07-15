package com.codechallenge.commitviewer.application.api.dto;

import java.time.LocalDateTime;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

public class CommitDtoUtil {

    public static CommitDto getRandom() {

        String sha = RandomString.make(10);
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        return CommitDto.builder().sha(sha).message(message).date(date).authorName(authorName).build();
    }

    public static CommitDto getStatic() {

        String sha = "sha-123456";
        String message = "message 123";
        LocalDateTime date = LocalDateTime.parse("2021-07-15T15:14:13.373180");
        String authorName = "Author Name";

        return CommitDto.builder().sha(sha).message(message).date(date).authorName(authorName).build();
    }

}
