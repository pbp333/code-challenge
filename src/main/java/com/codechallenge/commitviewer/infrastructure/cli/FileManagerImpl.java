package com.codechallenge.commitviewer.infrastructure.cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.springframework.stereotype.Service;

@Service
public class FileManagerImpl implements FileManager {

    @Override
    public File createTemporaryFolder(String prefix) throws IOException {
        return Files.createTempDirectory(prefix).toFile();
    }

    @Override
    public boolean removeTemporaryFolderAndContents(File folderToRemove) {
        File[] allContent = folderToRemove.listFiles();

        if (allContent != null)
            Arrays.stream(allContent).forEach(this::removeTemporaryFolderAndContents);

        return folderToRemove.delete();
    }

}
