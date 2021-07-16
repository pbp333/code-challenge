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

    private static final String[] GIT_LOG_COMMANDS = {"git", "log", "--pretty=%H-%cn-%ct-%s "};
    private static final String[] LS_COMMAND = {"ls"};


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
            var gitLogBuilder = new ProcessBuilder(GIT_LOG_COMMANDS);
            gitLogBuilder = gitLogBuilder.directory(repoFolder);

            var gitLogProcess = gitLogBuilder.start();
            var reader = new BufferedReader(new InputStreamReader(gitLogProcess.getInputStream()));

            List<String> log = new ArrayList<>();

            var line = " ";

            while ((line = reader.readLine()) != null) {
                log.add(line);
            }

            if (gitLogProcess.waitFor() != 0)
                throw new TechnicalException("Cli commit retriever - could not retrieve commit list");

            gitLogProcess.destroy();
            reader.close();

            return log;

        } catch (IOException | InterruptedException e) {
            throw new TechnicalException(EXCEPTION_MESSAGE, e);
        }
    }

    private String getRepoFolderName(Path tmpFolder) {

        try {
            var lsCommandBuilder = new ProcessBuilder(LS_COMMAND);
            lsCommandBuilder = lsCommandBuilder.directory(tmpFolder.toFile());

            var lsCommandProc = lsCommandBuilder.start();
            var reader = new BufferedReader(new InputStreamReader(lsCommandProc.getInputStream()));

            var line = reader.readLine();

            if (lsCommandProc.waitFor() != 0)
                throw new TechnicalException("Cli commit retriever - could not retrieve tmp folder");

            lsCommandProc.destroy();

            return line;

        } catch (IOException | InterruptedException e) {
            throw new TechnicalException(EXCEPTION_MESSAGE, e);

        }
    }

    private void createGitRepository(PaginatedRequest<String> request, Path tmpFolder) {

        try {

            String[] gitCloneCommand = {"git", "clone", request.getRequest()};
            var gitCloneCommandBuilder = new ProcessBuilder(gitCloneCommand);
            gitCloneCommandBuilder = gitCloneCommandBuilder.directory(tmpFolder.toFile());

            var gitCloneProcess = gitCloneCommandBuilder.start();

            if (gitCloneProcess.waitFor() != 0)
                throw new TechnicalException("Cli commit retriever - could not clone repository");

            gitCloneProcess.destroy();

        } catch (IOException | InterruptedException e) {

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
