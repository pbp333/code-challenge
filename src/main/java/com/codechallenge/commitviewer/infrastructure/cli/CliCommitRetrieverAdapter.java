package com.codechallenge.commitviewer.infrastructure.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codechallenge.commitviewer.application.api.dto.CommitDto;
import com.codechallenge.commitviewer.application.exception.TechnicalException;
import com.codechallenge.commitviewer.application.port.cli.CommitRetrieverCliPort;
import com.codechallenge.commitviewer.application.port.cli.GitRepositoryCommitCliRequest;

@Service
public class CliCommitRetrieverAdapter implements CommitRetrieverCliPort {

    private static final String TMP_FOLDER_PREFIX = "tmp";
    private static final String EXCEPTION_MESSAGE = "Unable to retrieve commit list via CLI";

    private final CommandLineInterface cli;
    private final FileManager fileManager;

    public CliCommitRetrieverAdapter(CommandLineInterface cli, FileManager fileManager) {
        this.cli = cli;
        this.fileManager = fileManager;
    }

    @Override
    public List<CommitDto> getCommits(GitRepositoryCommitCliRequest request) {

        var tmpFolder = createRepositoryFolder();

        createGitRepository(request.getUrl(), tmpFolder);

        var repoFolderName = getRepoFolderName(tmpFolder);

        var repoFolder = new File(tmpFolder.getAbsolutePath().concat("/").concat(repoFolderName));

        var commitList =
                getCommitList(repoFolder, request.getPageRequest().getPage(), request.getPageRequest().getSize());

        fileManager.removeTemporaryFolderAndContents(tmpFolder);

        return commitList.stream().map(CliCommitParser::parse).collect(Collectors.toList());

    }

    private File createRepositoryFolder() {

        try {

            return fileManager.createTemporaryFolder(TMP_FOLDER_PREFIX);

        } catch (IOException e) {
            throw new TechnicalException("Unable to create temporary folder for git repository", e);
        }
    }

    private List<String> getCommitList(File repoFolder, int page, int size) {

        try {

            var numberOfCommitsToSkip = size * (page - 1);

            var sizeArgument = String.format("--max-count=%d", size);
            var numberOfCommitsToSkipArgument = String.format("--skip=%d", numberOfCommitsToSkip);

            String[] gitLogCommands =
                    {"git", "log", "--pretty=%H-%cn-%ct-%s", sizeArgument, numberOfCommitsToSkipArgument};

            return cli.excuteCommand(gitLogCommands, repoFolder);


        } catch (IOException | InterruptedException e) {

            throw new TechnicalException(EXCEPTION_MESSAGE, e);

        }
    }

    private String getRepoFolderName(File tmpFolder) {

        try {

            String[] lsCommand = {"ls"};

            List<String> cliResponse = cli.excuteCommand(lsCommand, tmpFolder);

            if (cliResponse.size() != 1)
                throw new TechnicalException("Cli commit retriever - could not retrieve repository git folder");

            return cliResponse.get(0);

        } catch (IOException | InterruptedException e) {

            throw new TechnicalException(EXCEPTION_MESSAGE, e);

        }
    }

    private void createGitRepository(String repositoryUrl, File tmpFolder) {

        try {

            String[] gitCloneCommand = {"git", "clone", repositoryUrl};

            cli.excuteCommand(gitCloneCommand, tmpFolder);

        } catch (IOException | InterruptedException e) {

            throw new TechnicalException(EXCEPTION_MESSAGE, e);

        }

    }

}
