package com.codechallenge.commitviewer.infrastructure.rest.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubCommitResponse {

    private String sha;
    @JsonProperty("node_id")
    private String nodeId;
    private Commit commit;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

}
