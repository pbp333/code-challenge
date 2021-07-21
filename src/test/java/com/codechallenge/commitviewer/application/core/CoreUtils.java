package com.codechallenge.commitviewer.application.core;

import java.time.Instant;
import java.util.Arrays;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

public class CoreUtils {

    public static Commit getRandomCommit() {

        var sha = RandomString.make(10);
        var message = RandomString.make(10);
        var date = Instant.now();
        var authorName = RandomString.make(10);

        return Commit.builder().sha(sha).message(message).date(date).authorName(authorName).build();
    }

    public static GitRepository.Builder getRandomGitRepositoryBuilder() {

        var ownerName = RandomString.make(10);
        var name = RandomString.make(10);
        var commits = Arrays.asList(CoreUtils.getRandomCommit(), CoreUtils.getRandomCommit());

        return GitRepository.builder().ownerName(ownerName).name(name).commits(commits);
    }

}
