package com.codechallenge.commitviewer.application.api.request;

public class PaginatedRequest<T> {

    private final T request;
    private final int page;
    private final int size;

    private PaginatedRequest(Builder<T> builder) {
        this.request = builder.request;
        this.page = builder.page;
        this.size = builder.size;
    }

    public T getRequest() {
        return request;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private T request;
        private int page;
        private int size;

        private Builder() {

        }

        public Builder<T> request(T request) {
            this.request = request;
            return this;
        }

        public Builder<T> page(int page) {
            this.page = page;
            return this;
        }

        public Builder<T> size(int size) {
            this.size = size;
            return this;
        }

        public PaginatedRequest<T> build() {
            return new PaginatedRequest<>(this);
        }
    }

}
