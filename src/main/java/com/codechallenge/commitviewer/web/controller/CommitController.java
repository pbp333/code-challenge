package com.codechallenge.commitviewer.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codechallenge.commitviewer.application.api.CommitApplicationService;
import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;
import com.codechallenge.commitviewer.web.json.CommitJson;
import com.codechallenge.commitviewer.web.json.JsonMapper;
import com.codechallenge.commitviewer.web.request.PagingParam;

@RestController
@RequestMapping("/api/commits")
public class CommitController extends ErrorController {

    private final CommitApplicationService service;

    @Autowired
    public CommitController(CommitApplicationService service) {
        this.service = service;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CommitJson> getCommits(@RequestParam("url") String repositoryUrl, PagingParam pagingParam) {

        var request = PaginatedRequest.<String>builder().request(repositoryUrl).page(pagingParam.getPage())
                .size(pagingParam.getSize()).build();

        List<CommitDto> commits = service.getCommits(request);

        return commits.stream().map(JsonMapper::map).collect(Collectors.toList());

    }


}
