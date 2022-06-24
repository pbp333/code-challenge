package com.codechallenge.commitviewer.application;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.core.Commit;

public class CommitMapper {

    private CommitMapper() {

    }

    public static Commit map(CommitDto dto) {

        var commitBuilder = Commit.builder();

        commitBuilder.sha(dto.getSha().toString());
        commitBuilder.authorName(dto.getAuthorName());
        commitBuilder.date(dto.getDate());
        commitBuilder.message(dto.getMessage());

        return commitBuilder.build();

    }

    public static CommitDto map(Commit commit) {

        var dtoBuilder = CommitDto.builder();

        dtoBuilder.sha(commit.getSha());
        dtoBuilder.authorName(commit.getAuthorName());
        dtoBuilder.date(commit.getDate());
        dtoBuilder.message(commit.getMessage());

        return dtoBuilder.build();

    }
}
