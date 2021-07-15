package com.codechallenge.commitviewer.infrastructure.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class CommitTest {

    @Test
    public void canBuild() {

        // Given
        Author author = JsonUtil.getRandomAuthor();
        Committer committer = JsonUtil.getRandomCommitter();
        String message = RandomString.make(10);
        Tree tree = JsonUtil.getRandomTree();
        String url = RandomString.make(10);
        int commentCount = new Random().nextInt(100);
        Verification verification = JsonUtil.getRandomVerification();

        // When
        Commit commit = new Commit();

        commit.setAuthor(author);
        commit.setCommitter(committer);
        commit.setMessage(message);
        commit.setTree(tree);
        commit.setUrl(url);
        commit.setCommentCount(commentCount);
        commit.setVerification(verification);

        // Then
        assertThat(commit.getAuthor()).isEqualTo(author);
        assertThat(commit.getCommitter()).isEqualTo(committer);

        assertThat(commit.getMessage()).isEqualTo(message);
        assertThat(commit.getTree()).isEqualTo(tree);

        assertThat(commit.getUrl()).isEqualTo(url);

        assertThat(commit.getCommentCount()).isEqualTo(commentCount);

        assertThat(commit.getVerification()).isEqualTo(verification);
    }


}
