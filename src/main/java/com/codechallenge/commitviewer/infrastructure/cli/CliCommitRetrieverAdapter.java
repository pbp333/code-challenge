package com.codechallenge.commitviewer.infrastructure.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.api.request.PaginatedRequest;
import com.codechallenge.commitviewer.application.exception.TechnicalException;
import com.codechallenge.commitviewer.application.port.CommitRetrieverPort;
import com.codechallenge.commitviewer.application.port.CommitRetriverStrategy;

@Service
public class CliCommitRetrieverAdapter implements CommitRetrieverPort {

    private static final String TMP_FOLDER_PREFIX = "tmp";
    private static final String EXCEPTION_MESSAGE = "Could not retrieve GitHub repository commits through the CLI";

    @Override
    public CommitRetriverStrategy getStrategy() {
        return CommitRetriverStrategy.CLI;
    }

    @Override
    public List<CommitDto> getCommits(PaginatedRequest<String> request) {

        try {
            var tmpFolder = Files.createTempDirectory(TMP_FOLDER_PREFIX);

            createGitRepository(request, tmpFolder);

            var repoFolderName = getRepoFolderName(tmpFolder);

            var repoFolder = new File(tmpFolder.toAbsolutePath().toString().concat("/").concat(repoFolderName));

            var commitList = getCommitList(repoFolder);

            removeFolder(tmpFolder.toFile());

            return commitList.stream().map(CliCommitParser::parse).collect(Collectors.toList());

        } catch (IOException e) {
            throw new TechnicalException(EXCEPTION_MESSAGE, e);
        }
    }

    private List<String> getCommitList(File repoFolder) {

        try {
            String[] gitLogCommand = {"git", "log", "--pretty=\"%H-%cn-%ct-%s\""};
            var gitLogBuilder = new ProcessBuilder(gitLogCommand);
            gitLogBuilder = gitLogBuilder.directory(repoFolder);

            var gitLogProcess = gitLogBuilder.start();
            var reader = new BufferedReader(new InputStreamReader(gitLogProcess.getInputStream()));

            List<String> log = new ArrayList<>();

            var line = "";

            while ((line = reader.readLine()) != null) {
                log.add(line);
            }

            return log;

        } catch (IOException e) {
            throw new TechnicalException(EXCEPTION_MESSAGE, e);
        }
    }

    private String getRepoFolderName(Path tmpFolder) {

        try {
            String[] lsCommand = {"ls"};
            var lsCommandBuilder = new ProcessBuilder(lsCommand);
            lsCommandBuilder = lsCommandBuilder.directory(tmpFolder.toFile());

            var lsCommandProc = lsCommandBuilder.start();
            var reader = new BufferedReader(new InputStreamReader(lsCommandProc.getInputStream()));

            return reader.readLine();

        } catch (IOException e) {
            throw new TechnicalException(EXCEPTION_MESSAGE, e);

        }
    }

    private void createGitRepository(PaginatedRequest<String> request, Path tmpFolder) {

        try {

            String[] gitCloneCommand = {"git", "clone", "--depth", "1", request.getRequest()};
            var gitCloneCommandBuilder = new ProcessBuilder(gitCloneCommand);
            gitCloneCommandBuilder = gitCloneCommandBuilder.directory(tmpFolder.toFile());

            gitCloneCommandBuilder.start();

        } catch (IOException e) {

            throw new TechnicalException(EXCEPTION_MESSAGE, e);

        }

    }

    private boolean removeFolder(File folder) {

        File[] allContent = folder.listFiles();

        if (allContent != null)
            Arrays.stream(allContent).forEach(this::removeFolder);

        return folder.delete();

    }

}
