package com.codechallenge.commitviewer.application.core;

import java.time.LocalDateTime;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

public class CoreUtils {

    public static Commit getRandomCommit() {

        String sha = RandomString.make(10);
        String message = RandomString.make(10);
        LocalDateTime date = LocalDateTime.now();
        String authorName = RandomString.make(10);

        return Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();
    }

}
