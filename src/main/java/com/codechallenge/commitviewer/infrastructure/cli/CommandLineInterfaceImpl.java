package com.codechallenge.commitviewer.infrastructure.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class CommandLineInterfaceImpl implements CommandLineInterface {

    private static final int COMMAND_PERMITED_TIMEOUT_IN_SECONDS = 10;

    @Override
    public List<String> excuteCommand(String[] commands, File folder) throws IOException, InterruptedException {

        var builder = new ProcessBuilder(commands);
        builder = builder.directory(folder);

        var process = builder.start();
        var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        List<String> log = new ArrayList<>();

        var line = "";

        while ((line = reader.readLine()) != null) {
            log.add(line);
        }

        if (process.waitFor(COMMAND_PERMITED_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS))
            throw new IOException(
                    "Command execution took longer than permited: " + COMMAND_PERMITED_TIMEOUT_IN_SECONDS + " secs");

        process.destroy();
        reader.close();

        return log;
    }

}
