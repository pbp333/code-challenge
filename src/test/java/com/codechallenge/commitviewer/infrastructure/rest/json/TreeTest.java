package com.codechallenge.commitviewer.infrastructure.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class TreeTest {

    @Test
    public void canBuild() {

        // Given
        String sha = RandomString.make(10);
        String url = RandomString.make(10);

        // When
        Tree tree = new Tree();

        tree.setSha(sha);
        tree.setUrl(url);

        // Then
        assertThat(tree.getSha()).isEqualTo(sha);
        assertThat(tree.getUrl()).isEqualTo(url);
    }


}
