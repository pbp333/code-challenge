package com.codechallenge.commitviewer.application.core;


import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.codechallenge.commitviewer.application.core.exception.BusinessException;

@Entity
@Table(name = "COMMIT")
public class Commit {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "SHA")
    private String sha;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE", columnDefinition = "TIMESTAMP(6)")
    private Instant date;

    @Column(name = "AUTHORNAME")
    private String authorName;

    // Needed for JPA
    private Commit() {

    }

    private Commit(Builder builder) {
        this.sha = builder.sha;
        this.message = builder.message;
        this.date = builder.date;
        this.authorName = builder.authorName;
    }

    public Long getId() {
        return id;
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

        private Builder() {

        }

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

    @Override
    public int hashCode() {
        final var prime = 31;
        var result = 1;
        result = prime * result + ((sha == null) ? 0 : sha.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Commit other = (Commit) obj;
        if (sha == null) {
            if (other.sha != null)
                return false;
        } else if (!sha.equals(other.sha))
            return false;
        return true;
    }

}
