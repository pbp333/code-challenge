package com.codechallenge.commitviewer.application.api.dto;

import java.time.Instant;

public class CommitDto {

    private final String sha;
    private final String message;
    private final Instant date;
    private final String authorName;

    private CommitDto(Builder builder) {
        this.sha = builder.sha;
        this.message = builder.message;
        this.date = builder.date;
        this.authorName = builder.authorName;
    }

    public String getSha() {
        return sha;
    }

    public String getMessage() {
        return message;
    }

    public Instant getDate() {
        return date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String sha;
        private String message;
        private Instant date;
        private String authorName;

        public Builder sha(String sha) {
            this.sha = sha;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder date(Instant date) {
            this.date = date;
            return this;
        }

        public Builder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public CommitDto build() {
            return new CommitDto(this);
        }
    }

}
