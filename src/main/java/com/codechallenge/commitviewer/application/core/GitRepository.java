package com.codechallenge.commitviewer.application.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.codechallenge.commitviewer.application.exception.BusinessException;

public class GitRepository {

    private final String ownerName;
    private final String name;
    private final List<Commit> commits = new ArrayList<>();

    private GitRepository(Builder builder) {
        this.ownerName = builder.ownerName;
        this.name = builder.name;
        this.commits.addAll(builder.commits);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getName() {
        return name;
    }

    public List<Commit> getCommits() {
        return Collections.unmodifiableList(commits);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String ownerName;
        private String name;
        private List<Commit> commits = new ArrayList<>();

        private Builder() {

        }

        public Builder ownerName(String ownerName) {
            this.ownerName = ownerName;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder commits(List<Commit> commits) {
            this.commits = commits;
            return this;
        }

        public GitRepository build() {

            validateOwnerName();
            validateName();

            if (this.commits == null)
                this.commits = Collections.emptyList();

            return new GitRepository(this);
        }

        private void validateOwnerName() {

            if (this.ownerName == null || this.ownerName.isEmpty())
                throw new BusinessException("Owner Name is invalid");

        }

        private void validateName() {

            if (this.name == null || this.name.isEmpty())
                throw new BusinessException("Name is invalid");

        }
    }

}
