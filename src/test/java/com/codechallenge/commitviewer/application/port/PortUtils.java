package com.codechallenge.commitviewer.application.port;

import java.util.Random;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

import com.codechallenge.commitviewer.application.port.cli.GitRepositoryCommitCliRequest;
import com.codechallenge.commitviewer.application.port.rest.GitRepositoryCommitRestRequest;

public class PortUtils {

    public static PageRequest getRandomPageRequest() {

        int page = new Random().nextInt(100) + 1;
        int size = new Random().nextInt(100) + 1;

        return PageRequest.from(page, size);

    }

    public static GitRepositoryCommitRestRequest getRandomGitRepositoryCommitRestRequest() {

        var repositoryName = RandomString.make(10);
        var ownerName = RandomString.make(10);
        var pageRequest = PortUtils.getRandomPageRequest();

        return GitRepositoryCommitRestRequest.from(repositoryName, ownerName, pageRequest);

    }

    public static GitRepositoryCommitCliRequest getRandomGitRepositoryCommitCliRequest() {

        var url = RandomString.make(10);
        var pageRequest = PortUtils.getRandomPageRequest();

        return GitRepositoryCommitCliRequest.from(url, pageRequest);

    }

}
