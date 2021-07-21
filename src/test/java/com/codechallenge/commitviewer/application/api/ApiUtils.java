package com.codechallenge.commitviewer.application.api;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;

public class ApiUtils {

    public static CommitDto getRandomCommitDto() {

        String sha = RandomString.make(10);
        String message = RandomString.make(10);
        Instant date = Instant.now();
        String authorName = RandomString.make(10);

        return CommitDto.builder().sha(sha).message(message).date(date).authorName(authorName).build();
    }

    public static CommitDto getStaticCommitDto() {

        String sha = "sha-123456";
        String message = "message 123";
        Instant date = LocalDateTime.parse("2021-07-15T15:14:13.373180").toInstant(ZoneOffset.UTC);
        String authorName = "Author Name";

        return CommitDto.builder().sha(sha).message(message).date(date).authorName(authorName).build();
    }

    public static PaginatedRequest<String> getRandomPaginatedRequest() {

        String request = RandomString.make(10);
        int page = new Random().nextInt(100);
        int size = new Random().nextInt(100);

        return PaginatedRequest.<String>builder().request(request).page(page).size(size).build();

    }

}
