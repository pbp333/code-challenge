package com.codechallenge.commitviewer.infrastructure.rest.json;

import java.time.Instant;
import java.util.Random;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

public class JsonUtil {

    public static Author getRandomAuthor() {

        String name = RandomString.make(10);
        String email = RandomString.make(10);
        Instant date = Instant.now();

        Author author = new Author();

        author.setName(name);
        author.setEmail(email);
        author.setDate(date);

        return author;
    }

    public static Committer getRandomCommitter() {

        String name = RandomString.make(10);
        String email = RandomString.make(10);
        Instant date = Instant.now();

        Committer commiter = new Committer();

        commiter.setName(name);
        commiter.setEmail(email);
        commiter.setDate(date);

        return commiter;
    }

    public static Tree getRandomTree() {

        String sha = RandomString.make(10);
        String url = RandomString.make(10);

        Tree tree = new Tree();

        tree.setSha(sha);
        tree.setUrl(url);

        return tree;
    }

    public static Verification getRandomVerification() {

        boolean verified = new Random().nextBoolean();
        String reason = RandomString.make(10);
        String signature = RandomString.make(10);
        String payload = RandomString.make(10);

        Verification verification = new Verification();

        verification.setVerified(verified);
        verification.setReason(reason);
        verification.setSignature(signature);
        verification.setPayload(payload);

        return verification;
    }

    public static Commit getRandomCommit() {

        Author author = JsonUtil.getRandomAuthor();
        Committer committer = JsonUtil.getRandomCommitter();
        String message = RandomString.make(10);
        Tree tree = JsonUtil.getRandomTree();
        String url = RandomString.make(10);
        int commentCount = new Random().nextInt(100);
        Verification verification = JsonUtil.getRandomVerification();

        Commit commit = new Commit();

        commit.setAuthor(author);
        commit.setCommitter(committer);
        commit.setMessage(message);
        commit.setTree(tree);
        commit.setUrl(url);
        commit.setCommentCount(commentCount);
        commit.setVerification(verification);

        return commit;
    }

    public static GitHubCommitResponse getRandomGitHubCommitResponse() {

        String sha = RandomString.make(10);
        String nodeId = RandomString.make(10);
        Commit commit = JsonUtil.getRandomCommit();

        GitHubCommitResponse response = new GitHubCommitResponse();

        response.setSha(sha);
        response.setNodeId(nodeId);
        response.setCommit(commit);


        return response;
    }

}
