package com.codechallenge.commitviewer.application.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.codechallenge.commitviewer.application.exception.BusinessException;

@Entity
@Table(name = "GIT_REPOSITORY")
public class GitRepository {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "OWNER_NAME")
    private String ownerName;

    @Column(name = "NAME")
    private String name;

    @JoinColumn(name = "GIT_REPOSITORY_ID")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Commit> commits = new ArrayList<>();

    // Needed for JPA
    private GitRepository() {

    }

    private GitRepository(Builder builder) {
        this.ownerName = builder.ownerName;
        this.name = builder.name;
        this.commits.addAll(builder.commits);
    }

    public Long getId() {
        return id;
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
