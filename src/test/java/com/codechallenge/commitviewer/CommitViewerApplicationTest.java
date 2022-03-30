package com.codechallenge.commitviewer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommitViewerApplicationTest {

	// Application loads test
	@Test
	void contextLoads() {
		assertThat(true).isTrue();
	}

}
