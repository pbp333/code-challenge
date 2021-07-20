package com.codechallenge.commitviewer.infrastructure.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CommandLineInterface {

    List<String> excuteCommand(String[] commands, File folder) throws IOException, InterruptedException;

}
