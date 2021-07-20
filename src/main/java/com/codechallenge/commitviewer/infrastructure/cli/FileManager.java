package com.codechallenge.commitviewer.infrastructure.cli;

import java.io.File;
import java.io.IOException;

public interface FileManager {

    File createTemporaryFolder(String prefix) throws IOException;

    boolean removeTemporaryFolderAndContents(File folderToRemove);

}
