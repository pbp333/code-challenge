package com.codechallenge.commitviewer.application.api.request;

import java.util.Random;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

public class PaginatedRequestUtil {

    public static PaginatedRequest<String> getRandom() {

        String request = RandomString.make(10);
        int page = new Random().nextInt(100);
        int size = new Random().nextInt(100);

        return PaginatedRequest.<String>builder().request(request).page(page).size(size).build();

    }

}
