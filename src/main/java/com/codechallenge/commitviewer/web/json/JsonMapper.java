package com.codechallenge.commitviewer.web.json;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;

public class JsonMapper {

    private JsonMapper() {

    }

    public static CommitJson map(CommitDto dto) {

        var builder = CommitJson.builder();

        builder.sha(dto.getSha());
        builder.message(dto.getMessage());
        builder.date(dto.getDate());
        builder.authorName(dto.getAuthorName());

        return builder.build();

    }

}
