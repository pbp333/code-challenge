package com.codechallenge.commitviewer.application.core;

import java.time.LocalDateTime;

import com.codechallenge.commitviewer.application.exception.BusinessException;

public class Commit {

    private final String sha;
    private final String message;
    private final LocalDateTime date;
    private final String authorName;

    private Commit(Builder builder) {
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

    public LocalDateTime getDate() {
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
        private LocalDateTime date;
        private String authorName;

        public Builder sha(String sha) {
            this.sha = sha;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Commit build() {

            validateSha();
            validateMessage();
            validateDate();
            validateAuthorName();

            return new Commit(this);
        }

        private void validateSha() {
            if (this.sha == null || this.sha.isBlank())
                throw new BusinessException("Sha is invalid");
        }

        private void validateMessage() {
            if (this.message == null || this.message.isBlank())
                throw new BusinessException("Message is invalid");
        }

        private void validateDate() {
            if (this.date == null)
                throw new BusinessException("Date is invalid");

        }

        private void validateAuthorName() {
            if (this.authorName == null || this.authorName.isBlank())
                throw new BusinessException("Author Name is invalid");
        }
    }

}
