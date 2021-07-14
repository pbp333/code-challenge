package com.codechallenge.commitviewer.application.api.dto;

import java.time.LocalDateTime;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

public class CommitDtoFixture {

    public static CommitDto get() {

        String sha = RandomString.make(10);
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        return CommitDto.builder().sha(sha).message(message).date(date).authorName(authorName).build();
    }

}
