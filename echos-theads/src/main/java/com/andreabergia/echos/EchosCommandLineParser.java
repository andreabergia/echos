package com.andreabergia.echos;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class EchosCommandLineParser {
    private static final Option PORT_OPTION = Option
            .builder("p")
            .longOpt("port")
            .desc("The server socket port")
            .hasArg()
            .argName("port")
            .type(Integer.class)
            .build();
    private static final Option THREADS_OPTION = Option
            .builder("t")
            .longOpt("threads")
            .desc("The maximum number of threads")
            .hasArg()
            .argName("threads")
            .type(Integer.class)
            .build();

    private static final Options OPTIONS = new Options()
            .addOption(PORT_OPTION)
            .addOption(THREADS_OPTION);

    public EchosSocketServerOptions parseCommandLine(String[] args) {
        try {
            CommandLine commandLine = new DefaultParser().parse(OPTIONS, args);
            if (hasLeftOverArguments(commandLine)) {
                throw new InvalidCommandLineException("No arguments are allowed");
            }

            int port = getPort(commandLine);
            int threads = getThreads(commandLine);
            return new EchosSocketServerOptions(port, threads);
        } catch (ParseException | IllegalArgumentException e) {
            throw new InvalidCommandLineException(e);
        }
    }

    private boolean hasLeftOverArguments(CommandLine commandLine) {
        return !commandLine.getArgList().isEmpty();
    }

    private int getPort(CommandLine commandLine) {
        return getIntegerOptionValue(commandLine, PORT_OPTION, EchosSocketServerOptions.DEFAULT_PORT);
    }

    private int getThreads(CommandLine commandLine) {
        return getIntegerOptionValue(commandLine, THREADS_OPTION, EchosSocketServerOptions.NUM_THREADS);
    }

    private int getIntegerOptionValue(CommandLine commandLine, Option option, int defaultValue) {
        if (commandLine.hasOption(option.getOpt())) {
            return Integer.parseInt(commandLine.getOptionValue(option.getOpt()));
        }
        return defaultValue;
    }
}
